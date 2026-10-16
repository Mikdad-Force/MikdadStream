package com.mikdadstream.app.data.firebase

import android.util.Log
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
 * Semua kegagalan dicatat ke `lastError` agar bisa ditampilkan di UI.
 */
class UserRepository {

    private val auth: FirebaseAuth? = runCatching { FirebaseAuth.getInstance() }
        .onFailure { Log.e(TAG, "FirebaseAuth tidak bisa diinisialisasi", it) }
        .getOrNull()
    private val firestore: FirebaseFirestore? = runCatching { FirebaseFirestore.getInstance() }
        .onFailure { Log.e(TAG, "FirebaseFirestore tidak bisa diinisialisasi", it) }
        .getOrNull()

    private val _account = MutableStateFlow<AccountState?>(null)
    val account: StateFlow<AccountState?> = _account

    private val _lastError = MutableStateFlow<String?>(null)
    val lastError: StateFlow<String?> = _lastError

    private var listener: ListenerRegistration? = null

    val available: Boolean get() = auth != null && firestore != null
    val currentUid: String? get() = auth?.currentUser?.uid

    fun start() {
        val firebaseAuth = auth
        if (firebaseAuth == null) {
            fail("Firebase Auth tidak tersedia di perangkat ini")
            return
        }
        firebaseAuth.addAuthStateListener { fa ->
            val user = fa.currentUser
            Log.d(TAG, "AuthState berubah: uid=${user?.uid ?: "null"}")
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
        val firebaseAuth = auth
        if (firebaseAuth == null) {
            fail("Firebase Auth tidak tersedia di perangkat ini")
            return null
        }
        if (idToken.isBlank()) {
            fail("ID token Google kosong — Google Sign-In gagal")
            return null
        }
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val user = result.user
            if (user == null) {
                fail("Firebase tidak mengembalikan user setelah login")
                null
            } else {
                Log.i(TAG, "Login Firebase sukses uid=${user.uid}")
                upsert(user)
            }
        } catch (t: Throwable) {
            fail("Login Firebase gagal: ${t.message}")
            null
        }
    }

    suspend fun signOut() {
        runCatching { auth?.signOut() }
        detachListener()
        _account.value = null
        _lastError.value = null
    }

    /** Paksa sinkronisasi profil ke Firestore (dipanggil saat app dibuka / setelah login). */
    suspend fun syncNow(): AccountState? {
        val user = auth?.currentUser ?: return null
        return upsert(user)
    }

    /** Sinkronkan data profil dasar ke Firestore (tanpa menimpa status premium). */
    suspend fun upsert(user: FirebaseUser? = auth?.currentUser): AccountState? {
        val u = user ?: return null
        val db = firestore
        if (db == null) {
            fail("Firestore tidak tersedia di perangkat ini")
            return null
        }
        val ref = db.collection(COLLECTION).document(u.uid)

        val profile = runCatching { ref.get().await() }.getOrNull()
        val existing = profile?.exists() == true
        val payload = mutableMapOf<String, Any?>(
            "uid" to u.uid,
            "email" to u.email,
            "displayName" to u.displayName,
            "avatarUrl" to u.photoUrl?.toString(),
            "lastLoginAt" to FieldValue.serverTimestamp(),
            "updatedAt" to FieldValue.serverTimestamp(),
        )
        if (!existing) {
            val initial = resolveInitialType(u.email)
            payload["accountType"] = initial.key
            payload["premium"] = initial == AccountType.PREMIUM
            payload["createdAt"] = FieldValue.serverTimestamp()
        }

        val writeError = runCatching { ref.set(payload, SetOptions.merge()).await() }.exceptionOrNull()
        if (writeError != null) {
            fail("Gagal menulis ke Firestore: ${writeError.message}")
        } else {
            Log.i(TAG, "Firestore users/${u.uid} tersimpan")
            _lastError.value = null
        }

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
        val writeError = runCatching {
            firestore?.collection(COLLECTION)?.document(uid)?.set(
                mapOf(
                    "premium" to true,
                    "accountType" to AccountType.PREMIUM.key,
                    "premiumSince" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp(),
                ),
                SetOptions.merge(),
            )?.await()
        }.exceptionOrNull()
        if (writeError != null) fail("Gagal menyimpan premium: ${writeError.message}")
        else _lastError.value = null
        _account.value = _account.value?.copy(accountType = AccountType.PREMIUM, premium = true)
    }

    private fun attachListener(user: FirebaseUser) {
        val db = firestore
        if (db == null) {
            _account.value = stateFrom(user, null, false)
            return
        }
        detachListener()
        listener = db.collection(COLLECTION).document(user.uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    fail("Gagal membaca Firestore: ${error.message}")
                    return@addSnapshotListener
                }
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

    private fun fail(message: String) {
        Log.e(TAG, message)
        _lastError.value = message
    }

    companion object {
        private const val TAG = "MikdadFirebase"
        const val COLLECTION = "users"

        /** Akun yang otomatis berlabel Developers. Tambahkan email lain di sini. */
        val developerEmails = setOf<String>()
    }
}
