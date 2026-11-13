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
    /** Masa aktif berakhir (epoch ms); null = tanpa batas. Diisi Admin Panel. */
    val activeUntil: Long? = null,
) {
    /** True bila masa aktif sudah lewat — role turun ke Normal. */
    val expired: Boolean
        get() = activeUntil != null && System.currentTimeMillis() > activeUntil

    /** Role efektif setelah memperhitungkan masa aktif. */
    val effectiveType: AccountType
        get() = if (expired) AccountType.NORMAL else accountType

    /** Premium hanya berlaku bila belum kedaluwarsa. */
    val effectivePremium: Boolean
        get() = !expired && (premium || accountType != AccountType.NORMAL)
}

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
        var createdActiveUntil: Long? = null
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
            // Akun baru mendapat masa aktif 1 bulan sejak login pertama.
            // Developers tidak dibatasi agar tidak terkunci dari Admin Panel.
            if (initial != AccountType.DEVELOPERS) {
                createdActiveUntil = oneMonthFromNow()
                payload["activeUntil"] = createdActiveUntil
            }
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
        val state = stateFrom(u, storedType, premium, activeUntil = readActiveUntil(profile) ?: createdActiveUntil)
        _account.value = state
        return state
    }

    /** Baca masa aktif dari Firestore (angka epoch ms; bisa juga Timestamp). */
    private fun readActiveUntil(doc: com.google.firebase.firestore.DocumentSnapshot?): Long? {
        if (doc == null || !doc.exists()) return null
        val raw = doc.get("activeUntil")
        return when (raw) {
            is Number -> raw.toLong().takeIf { it > 0 }
            is com.google.firebase.Timestamp -> raw.toDate().time
            else -> null
        }
    }

    /** Aktifkan premium (pembelian Rp 10.000). Developer tetap berlabel Developers. */
    suspend fun activatePremium() {
        val uid = currentUid
        val keepDevelopers = _account.value?.accountType == AccountType.DEVELOPERS
        if (uid == null) {
            _account.value = _account.value?.copy(
                accountType = if (keepDevelopers) AccountType.DEVELOPERS else AccountType.PREMIUM,
                premium = true,
            )
            return
        }
        val payload = mutableMapOf<String, Any?>(
            "premium" to true,
            "premiumSince" to FieldValue.serverTimestamp(),
            "updatedAt" to FieldValue.serverTimestamp(),
        )
        if (!keepDevelopers) payload["accountType"] = AccountType.PREMIUM.key

        val writeError = runCatching {
            firestore?.collection(COLLECTION)?.document(uid)?.set(payload, SetOptions.merge())?.await()
        }.exceptionOrNull()
        if (writeError != null) fail("Gagal menyimpan premium: ${writeError.message}")
        else _lastError.value = null
        _account.value = _account.value?.copy(
            accountType = if (keepDevelopers) AccountType.DEVELOPERS else AccountType.PREMIUM,
            premium = true,
        )
    }

    /** Ubah tipe akun dan simpan ke Firestore (`users/{uid}.accountType`). */
    suspend fun setAccountType(type: AccountType) {
        val premium = type != AccountType.NORMAL
        val uid = currentUid
        if (uid == null) {
            _account.value = _account.value?.copy(accountType = type, premium = premium)
            return
        }
        val writeError = runCatching {
            firestore?.collection(COLLECTION)?.document(uid)?.set(
                mapOf(
                    "accountType" to type.key,
                    "premium" to premium,
                    "updatedAt" to FieldValue.serverTimestamp(),
                ),
                SetOptions.merge(),
            )?.await()
        }.exceptionOrNull()
        if (writeError != null) fail("Gagal menyimpan tipe akun: ${writeError.message}")
        else {
            Log.i(TAG, "Tipe akun users/$uid diubah ke ${type.key}")
            _lastError.value = null
        }
        _account.value = _account.value?.copy(accountType = type, premium = premium)
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
                    activeUntil = readActiveUntil(snapshot),
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
        activeUntil: Long? = null,
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
            activeUntil = activeUntil,
        )
    }

    private fun resolveInitialType(email: String?): AccountType =
        if (isDeveloper(email)) AccountType.DEVELOPERS else AccountType.NORMAL

    private fun isDeveloper(email: String?): Boolean =
        email != null && developerEmails.contains(email.lowercase())

    /** Epoch ms satu bulan kalender dari sekarang. */
    private fun oneMonthFromNow(): Long =
        java.util.Calendar.getInstance().apply { add(java.util.Calendar.MONTH, 1) }.timeInMillis

    private fun fail(message: String) {
        Log.e(TAG, message)
        _lastError.value = message
    }

    // ---------------- Admin Panel ----------------

    /** Ringkasan satu pengguna untuk daftar di Admin Panel. */
    data class AdminUser(
        val uid: String,
        val email: String?,
        val displayName: String?,
        val accountType: String?,
        val activeUntil: Long?,
    )

    /**
     * Apakah akun saat ini boleh membuka Admin Panel. Dibatasi ke role
     * Developers agar pengguna biasa tidak bisa mengubah masa aktif siapa pun.
     */
    fun isAdmin(): Boolean = _account.value?.accountType == AccountType.DEVELOPERS

    /**
     * Ambil daftar pengguna (maks [limit]) untuk dikelola.
     *
     * Membaca SELURUH koleksi `users`, sehingga Rules pada koleksi itu harus
     * mengizinkan baca (lihat firestore.rules: `allow read: if isSignedIn()`).
     * Bila masih ditolak, [lastError] diisi pesan yang menjelaskan penyebabnya
     * agar Admin Panel bisa menampilkan panduan, bukan gagal diam-diam.
     */
    suspend fun listUsers(limit: Long = 50): List<AdminUser> {
        val db = firestore ?: run {
            fail("Firestore tidak tersedia")
            return emptyList()
        }
        val snap = runCatching {
            db.collection(COLLECTION).limit(limit).get().await()
        }.getOrElse { e ->
            val msg = e.message.orEmpty()
            fail(
                if (msg.contains("PERMISSION_DENIED", true) || msg.contains("permission", true))
                    "Daftar pengguna ditolak Firestore. Pastikan Rules koleksi `users` " +
                        "berisi: allow read: if isSignedIn(); lalu Publish."
                else "Gagal memuat pengguna: $msg",
            )
            return emptyList()
        }
        if (snap.isEmpty) {
            Log.i(TAG, "listUsers kosong")
            return emptyList()
        }
        _lastError.value = null
        return snap.documents.map { d ->
            AdminUser(
                uid = d.id,
                email = d.getString("email"),
                displayName = d.getString("displayName"),
                accountType = d.getString("accountType"),
                activeUntil = readActiveUntil(d),
            )
        }
    }

    /**
     * Cari satu pengguna berdasarkan email persis.
     *
     * Ini jalur utama Admin Panel karena hanya membutuhkan query pada field
     * `email` (bukan membaca semua dokumen). Bila Rules belum mengizinkan,
     * hasilnya null dan alasan penolakan masuk ke [lastError].
     */
    suspend fun findUserByEmail(email: String): AdminUser? {
        val db = firestore ?: run {
            fail("Firestore tidak tersedia")
            return null
        }
        val target = email.trim().lowercase()
        if (target.isBlank()) {
            fail("Email tidak boleh kosong")
            return null
        }
        // `email` disimpan apa adanya dari akun Google; coba bentuk asli lalu
        // huruf kecil agar cocok dengan data lama.
        val candidates = listOf(email.trim(), target).distinct()
        for (candidate in candidates) {
            val snap = runCatching {
                db.collection(COLLECTION)
                    .whereEqualTo("email", candidate)
                    .limit(1)
                    .get()
                    .await()
            }.getOrElse {
                fail("Gagal mencari pengguna: ${it.message}")
                return null
            }
            val doc = snap.documents.firstOrNull() ?: continue
            return AdminUser(
                uid = doc.id,
                email = doc.getString("email"),
                displayName = doc.getString("displayName"),
                accountType = doc.getString("accountType"),
                activeUntil = readActiveUntil(doc),
            )
        }
        fail("Pengguna dengan email $target tidak ditemukan")
        return null
    }

    /**
     * Ubah role + masa aktif pengguna lain. [activeUntil] null = tanpa batas.
     *
     * Memakai `update()` (bukan `set(merge)`) agar sesuai aturan Firestore:
     * perubahan dokumen pengguna lain diizinkan lewat cabang UPDATE untuk
     * developer, sementara `set` pada dokumen baru akan dinilai sebagai CREATE
     * dan ditolak. Karena itu bila dokumen belum ada, pesan kesalahan dibuat
     * jelas alih-alih gagal tanpa penjelasan.
     */
    suspend fun updateUserAccess(
        uid: String,
        type: AccountType,
        activeUntil: Long?,
    ): Boolean {
        val db = firestore ?: run {
            fail("Firestore tidak tersedia")
            return false
        }
        // update() menerima Map<String, Any> (bukan Any?): nilai null diwakili
        // FieldValue.delete()/nilai konkret, jadi activeUntil dipisah.
        val payload = mutableMapOf<String, Any>(
            "accountType" to type.key,
            "premium" to (type != AccountType.NORMAL),
            "updatedAt" to FieldValue.serverTimestamp(),
        )
        if (activeUntil != null) {
            payload["activeUntil"] = activeUntil
        } else {
            // "Tanpa batas" = hapus field agar tidak ada masa aktif.
            payload["activeUntil"] = FieldValue.delete()
        }
        val error = runCatching {
            db.collection(COLLECTION).document(uid).update(payload).await()
        }.exceptionOrNull()
        if (error != null) {
            val msg = error.message.orEmpty()
            fail(
                if (msg.contains("NOT_FOUND", true) || msg.contains("No document", true))
                    "Dokumen pengguna belum ada. Pengguna harus login sekali dulu."
                else "Gagal menyimpan akses: $msg",
            )
            return false
        }
        Log.i(TAG, "Admin mengubah users/$uid -> ${type.key} until=$activeUntil")
        _lastError.value = null
        return true
    }

    companion object {
        private const val TAG = "MikdadFirebase"
        const val COLLECTION = "users"

        /** Akun yang otomatis berlabel Developers. Tambahkan email lain di sini. */
        val developerEmails = setOf<String>()
    }
}
