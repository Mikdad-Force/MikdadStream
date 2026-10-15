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
    private val _serverStatus = MutableStateFlow<String?>(null)
    private val _hiddenSources = MutableStateFlow(sp.getStringSet(KEY_HIDDEN, null)?.toSet() ?: emptySet())

    val accessToken: StateFlow<String?> = _accessToken
    val profile: StateFlow<Profile?> = _profile
    val activeSource: StateFlow<String> = _activeSource
    val themeMode: StateFlow<Int> = _themeMode
    val serverStatus: StateFlow<String?> = _serverStatus
    val hiddenSources: StateFlow<Set<String>> = _hiddenSources

    val refreshToken: String? get() = sp.getString(KEY_REFRESH, null)
    val appInstanceId: String
        get() = sp.getString(KEY_INSTANCE, null) ?: UUID.randomUUID().toString().also {
            sp.edit { putString(KEY_INSTANCE, it) }
        }
    val email: String? get() = sp.getString(KEY_EMAIL, null)

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

    fun setServerStatus(status: String?) {
        _serverStatus.value = status
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

        private const val KEY_ACCESS = "access_token"
        private const val KEY_REFRESH = "refresh_token"
        private const val KEY_ACCESS_EXP = "access_exp"
        private const val KEY_INSTANCE = "app_instance_id"
        private const val KEY_EMAIL = "email"
        private const val KEY_NAME = "display_name"
        private const val KEY_AVATAR = "avatar_url"
        private const val KEY_NUMBER = "user_number"
        private const val KEY_PREMIUM = "premium"
        private const val KEY_SOURCE = "active_source"
        private const val KEY_THEME = "theme_mode"
        private const val KEY_HIDDEN = "hidden_sources"
    }
}
