package com.mikdadstream.app.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

enum class AccountType(val key: String, val label: String) {
    NORMAL("normal", "Normal"),
    PREMIUM("premium", "Premium"),
    DEVELOPERS("developers", "Developers"),
}

data class AccountState(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val avatarUrl: String?,
    val accountType: AccountType,
    val premium: Boolean,
)

/**
 * Akun berbasis Firebase (Authentication + Firestore).
 * Data disimpan pada koleksi `users/{uid}`.
 * Bila Firebase tidak terkonfigurasi, seluruh operasi gagal dengan aman (null) tanpa crash.
 */
class UserRepository {

    private val auth: FirebaseAuth? = runCatching { FirebaseAuth.getInstance() }.getOrNull()
    private val firestore: FirebaseFirestore? = runCatching { FirebaseFirestore.getInstance() }.getOrNull()

    private val _account = MutableStateFlow<AccountState?>(null)
    val account: StateFlow<AccountState?> = _account

    private var listener: ListenerRegistration? = null

    val currentUid: String? get() = auth?.currentUser?.uid

    /** Pasang pemantau status login Firebase agar profil selalu tersinkron. */
    fun start() {
        val firebaseAuth = auth ?: return
        firebaseAuth.addAuthStateListener { fa ->
            val user = fa.currentUser
            if (user == null) {
                detachListener()
                _account.value = null
            } else {
                attachListener(user)
            }
        }
    }

    /** Login Google memakai ID token dari Google Sign-In. */
    suspend fun signInWithGoogle(idToken: String): AccountState? {
        val firebaseAuth = auth ?: return null
        return runCatching {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            result.user?.let { upsert(it) }
        }.getOrNull()
    }

    suspend fun signOut() {
        runCatching { auth?.signOut() }
        detachListener()
        _account.value = null
    }

    /** Sinkronkan data profil dasar ke Firestore (tanpa menimpa status premium). */
    suspend fun upsert(user: FirebaseUser? = auth?.currentUser): AccountState? {
        val u = user ?: return null
        val db = firestore ?: return null
        val ref = db.collection(COLLECTION).document(u.uid)
        val profile = runCatching { ref.get().await() }.getOrNull()
        val existing = profile?.exists() == true
        val payload = mutableMapOf<String, Any?>(
            "uid" to u.uid,
            "email" to u.email,
            "displayName" to u.displayName,
            "avatarUrl" to u.photoUrl?.toString(),
            "updatedAt" to FieldValue.serverTimestamp(),
        )
        if (!existing) {
            val initial = resolveInitialType(u.email)
            payload["accountType"] = initial.key
            payload["premium"] = initial == AccountType.PREMIUM
            payload["createdAt"] = FieldValue.serverTimestamp()
        }
        runCatching { ref.set(payload, SetOptions.merge()).await() }

        val storedType = profile?.getString("accountType")
        val premium = profile?.getBoolean("premium") ?: false
        val state = stateFrom(u, storedType, premium)
        _account.value = state
        return state
    }

    /** Aktifkan premium (pembelian Rp 10.000). */
    suspend fun activatePremium() {
        val uid = currentUid
        if (uid == null) {
            _account.value = _account.value?.copy(accountType = AccountType.PREMIUM, premium = true)
            return
        }
        runCatching {
            firestore?.collection(COLLECTION)?.document(uid)?.set(
                mapOf(
                    "premium" to true,
                    "accountType" to AccountType.PREMIUM.key,
                    "premiumSince" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp(),
                ),
                SetOptions.merge(),
            )?.await()
        }
        _account.value = _account.value?.copy(accountType = AccountType.PREMIUM, premium = true)
    }

    suspend fun refresh() {
        upsert()
    }

    private fun attachListener(user: FirebaseUser) {
        val db = firestore
        if (db == null) {
            _account.value = stateFrom(user, null, false)
            return
        }
        detachListener()
        listener = db.collection(COLLECTION).document(user.uid)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot == null) return@addSnapshotListener
                if (!snapshot.exists()) {
                    _account.value = stateFrom(user, null, false)
                    return@addSnapshotListener
                }
                _account.value = stateFrom(
                    user,
                    snapshot.getString("accountType"),
                    snapshot.getBoolean("premium") ?: false,
                    email = snapshot.getString("email"),
                    displayName = snapshot.getString("displayName"),
                    avatarUrl = snapshot.getString("avatarUrl"),
                )
            }
    }

    private fun detachListener() {
        listener?.remove()
        listener = null
    }

    private fun stateFrom(
        user: FirebaseUser,
        storedType: String?,
        premium: Boolean,
        email: String? = user.email,
        displayName: String? = user.displayName,
        avatarUrl: String? = user.photoUrl?.toString(),
    ): AccountState {
        val type = when {
            storedType == AccountType.DEVELOPERS.key || isDeveloper(email) -> AccountType.DEVELOPERS
            premium || storedType == AccountType.PREMIUM.key -> AccountType.PREMIUM
            else -> AccountType.NORMAL
        }
        return AccountState(
            uid = user.uid,
            email = email,
            displayName = displayName,
            avatarUrl = avatarUrl,
            accountType = type,
            premium = type == AccountType.PREMIUM || type == AccountType.DEVELOPERS,
        )
    }

    private fun resolveInitialType(email: String?): AccountType =
        if (isDeveloper(email)) AccountType.DEVELOPERS else AccountType.NORMAL

    private fun isDeveloper(email: String?): Boolean =
        email != null && developerEmails.contains(email.lowercase())

    companion object {
        const val COLLECTION = "users"

        /** Akun yang otomatis berlabel Developers. Tambahkan email lain di sini. */
        val developerEmails = setOf<String>()
    }
}
