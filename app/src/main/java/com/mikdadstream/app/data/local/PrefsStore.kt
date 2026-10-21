package com.mikdadstream.app.data.local

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONArray
import java.util.UUID

data class Profile(
    val email: String? = null,
    val displayName: String? = null,
    val avatarUrl: String? = null,
    val userNumber: Int? = null,
    val premium: Boolean? = null,
) {
    val initials: String
        get() = displayName?.trim()?.split(Regex("\\s+"))?.take(2)
            ?.joinToString("") { it.take(1).uppercase() }
            ?.ifBlank { null }
            ?: email?.take(1)?.uppercase() ?: "S"
}

/** Penyimpanan sesi (token, profil) + preferensi aplikasi. */
class PrefsStore(context: Context) {

    private val sp = context.getSharedPreferences("mikdad_stream_prefs", Context.MODE_PRIVATE)

    private val _accessToken = MutableStateFlow(sp.getString(KEY_ACCESS, null))
    private val _tokenExpiresAt = MutableStateFlow(sp.getLong(KEY_ACCESS_EXP, 0L))
    private val _profile = MutableStateFlow(
        Profile(
            email = sp.getString(KEY_EMAIL, null),
            displayName = sp.getString(KEY_NAME, null),
            avatarUrl = sp.getString(KEY_AVATAR, null),
            userNumber = sp.getInt(KEY_NUMBER, 0).takeIf { it > 0 },
            premium = sp.getBoolean(KEY_PREMIUM, false),
        )
    )
    private val _activeSource = MutableStateFlow(sp.getString(KEY_SOURCE, DEFAULT_SOURCE) ?: DEFAULT_SOURCE)
    private val _themeMode = MutableStateFlow(sp.getInt(KEY_THEME, THEME_SYSTEM))
    private val _accent = MutableStateFlow(sp.getInt(KEY_ACCENT, ACCENT_PINK))
    private val _language = MutableStateFlow(sp.getString(KEY_LANGUAGE, LANG_ID) ?: LANG_ID)
    private val _region = MutableStateFlow(
        CountryLocations.byCode(sp.getString(KEY_REGION, DEFAULT_REGION)).code,
    )
    private val _premiumUnlocked = MutableStateFlow(sp.getBoolean(KEY_PREMIUM_UNLOCK, false))
    private val _serverStatus = MutableStateFlow<String?>(null)
    private val _hiddenSources = MutableStateFlow(sp.getStringSet(KEY_HIDDEN, null)?.toSet() ?: emptySet())
    private val _loggedIn = MutableStateFlow(sp.getBoolean(KEY_LOGGED_IN, false))

    init {
        CountrySelection.code = _region.value
    }

    val accessToken: StateFlow<String?> = _accessToken
    val profile: StateFlow<Profile?> = _profile
    val activeSource: StateFlow<String> = _activeSource
    val themeMode: StateFlow<Int> = _themeMode
    val accent: StateFlow<Int> = _accent
    val language: StateFlow<String> = _language
    val region: StateFlow<String> = _region
    val premiumUnlocked: StateFlow<Boolean> = _premiumUnlocked
    val serverStatus: StateFlow<String?> = _serverStatus
    val hiddenSources: StateFlow<Set<String>> = _hiddenSources
    val loggedIn: StateFlow<Boolean> = _loggedIn

    val refreshToken: String? get() = sp.getString(KEY_REFRESH, null)

    val appInstanceId: String
        get() = sp.getString(KEY_INSTANCE, null) ?: UUID.randomUUID().toString().also {
            sp.edit { putString(KEY_INSTANCE, it) }
        }
    val email: String? get() = sp.getString(KEY_EMAIL, null)
    val googleSub: String? get() = sp.getString(KEY_GOOGLE_SUB, null)

    fun accessTokenValue(): String? = _accessToken.value
    fun tokenExpired(skewMs: Long = 45_000L): Boolean {
        val exp = _tokenExpiresAt.value
        return exp <= 0L || System.currentTimeMillis() >= exp - skewMs
    }

    fun saveTokens(access: String, refresh: String?, expiresInSeconds: Int?) {
        val exp = if (expiresInSeconds != null && expiresInSeconds > 0) {
            System.currentTimeMillis() + expiresInSeconds * 1000L
        } else System.currentTimeMillis() + 1_800_000L
        sp.edit {
            putString(KEY_ACCESS, access)
            putLong(KEY_ACCESS_EXP, exp)
            if (refresh != null) putString(KEY_REFRESH, refresh)
        }
        _accessToken.value = access
        _tokenExpiresAt.value = exp
    }

    fun saveEmail(email: String) {
        sp.edit { putString(KEY_EMAIL, email) }
        _profile.value = _profile.value?.copy(email = email) ?: Profile(email = email)
    }

    fun saveGoogleSub(sub: String?) {
        if (!sub.isNullOrBlank()) sp.edit { putString(KEY_GOOGLE_SUB, sub) }
    }

    fun saveProfile(displayName: String?, avatarUrl: String?, userNumber: Int?, premium: Boolean?) {
        sp.edit {
            displayName?.let { putString(KEY_NAME, it) }
            avatarUrl?.let { putString(KEY_AVATAR, it) }
            userNumber?.let { putInt(KEY_NUMBER, it) }
            premium?.let { putBoolean(KEY_PREMIUM, it) }
        }
        _profile.value = Profile(
            email = sp.getString(KEY_EMAIL, null),
            displayName = displayName ?: sp.getString(KEY_NAME, null),
            avatarUrl = avatarUrl ?: sp.getString(KEY_AVATAR, null),
            userNumber = userNumber ?: sp.getInt(KEY_NUMBER, 0).takeIf { it > 0 },
            premium = premium ?: sp.getBoolean(KEY_PREMIUM, false),
        )
    }

    fun clearTokens() {
        sp.edit {
            remove(KEY_ACCESS)
            remove(KEY_REFRESH)
            remove(KEY_ACCESS_EXP)
            remove(KEY_GOOGLE_SUB)
        }
        _accessToken.value = null
        _tokenExpiresAt.value = 0L
    }

    fun setActiveSource(modelId: String) {
        sp.edit { putString(KEY_SOURCE, modelId) }
        _activeSource.value = modelId
    }

    fun setThemeMode(mode: Int) {
        sp.edit { putInt(KEY_THEME, mode) }
        _themeMode.value = mode
    }

    fun setAccent(value: Int) {
        sp.edit { putInt(KEY_ACCENT, value) }
        _accent.value = value
    }

    fun setLanguage(code: String) {
        sp.edit { putString(KEY_LANGUAGE, code) }
        _language.value = code
    }

    fun setRegion(code: String) {
        val normalized = CountryLocations.byCode(code).code
        sp.edit { putString(KEY_REGION, normalized) }
        _region.value = normalized
        CountrySelection.code = normalized
    }

    fun setPremiumUnlocked(value: Boolean) {
        sp.edit { putBoolean(KEY_PREMIUM_UNLOCK, value) }
        _premiumUnlocked.value = value
    }

    fun setServerStatus(status: String?) {
        _serverStatus.value = status
    }

    fun setLoggedIn(value: Boolean) {
        sp.edit { putBoolean(KEY_LOGGED_IN, value) }
        _loggedIn.value = value
    }

    fun setHiddenSources(ids: Set<String>) {
        val normalized = ids.map { it.lowercase().trim() }.filter { it.isNotBlank() }.toSet()
        sp.edit { putStringSet(KEY_HIDDEN, normalized) }
        _hiddenSources.value = normalized
    }

    fun hideSource(id: String) {
        val key = id.lowercase().trim()
        if (key.isBlank()) return
        val next = _hiddenSources.value + key
        sp.edit { putStringSet(KEY_HIDDEN, next) }
        _hiddenSources.value = next
    }

    fun unhideSource(id: String) {
        val key = id.lowercase().trim()
        val next = _hiddenSources.value - key
        sp.edit { putStringSet(KEY_HIDDEN, next) }
        _hiddenSources.value = next
    }

    fun isSourceHidden(id: String): Boolean = _hiddenSources.value.contains(id.lowercase().trim())

    companion object {
        const val DEFAULT_SOURCE = "moviebox"
        const val THEME_SYSTEM = 0
        const val THEME_DARK = 1
        const val THEME_LIGHT = 2

        const val ACCENT_PINK = 0
        const val ACCENT_AMBER = 1
        const val ACCENT_TEAL = 2
        const val ACCENT_VIOLET = 3
        const val ACCENT_GREEN = 4

        const val LANG_ID = "id"
        const val LANG_EN = "en"
        const val LANG_SU = "su"

        const val DEFAULT_REGION = "ID"

        private const val KEY_ACCESS = "access_token"
        private const val KEY_REFRESH = "refresh_token"
        private const val KEY_ACCESS_EXP = "access_exp"
        private const val KEY_INSTANCE = "app_instance_id"
        private const val KEY_EMAIL = "email"
        private const val KEY_GOOGLE_SUB = "google_sub"
        private const val KEY_NAME = "display_name"
        private const val KEY_AVATAR = "avatar_url"
        private const val KEY_NUMBER = "user_number"
        private const val KEY_PREMIUM = "premium"
        private const val KEY_SOURCE = "active_source"
        private const val KEY_THEME = "theme_mode"
        private const val KEY_ACCENT = "accent_choice"
        private const val KEY_LANGUAGE = "app_language"
        private const val KEY_REGION = "app_region"
        private const val KEY_PREMIUM_UNLOCK = "premium_unlocked"
        private const val KEY_HIDDEN = "hidden_sources"
        private const val KEY_LOGGED_IN = "logged_in"
    }
}
