package com.mikdadstream.app.data.remote

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mikdadstream.app.BuildConfig
import com.mikdadstream.app.data.local.PrefsStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/** Error API dengan pesan ramah pengguna. */
class ApiError(val errorMessage: String, val code: Int = 0, val authRequired: Boolean = false) : Exception(errorMessage)

object ApiConfig {
    val baseUrl: String = BuildConfig.API_BASE_URL.trimEnd('/') + "/"
    val gson: Gson = Gson()
}

/**
 * Gerbang autentikasi: nonce → login, refresh token, dan pemulihan sesi.
 * Semua operasi memakai OkHttp blocking — dipanggil dari thread IO saja.
 */
class AuthGateway(
    private val prefs: PrefsStore,
    private val client: OkHttpClient,
    private val gson: Gson = ApiConfig.gson,
) {

    private val lock = Any()
    private val jsonMedia = "application/json; charset=utf-8".toMediaType()

    private fun post(path: String, payload: JsonObject, token: String? = null): JsonObject? {
        val builder = Request.Builder()
            .url(ApiConfig.baseUrl + path)
            .post(gson.toJson(payload).toRequestBody(jsonMedia))
            .header("Content-Type", "application/json")
        if (!token.isNullOrBlank()) builder.header("Authorization", "Bearer $token")
        return client.newCall(builder.build()).execute().use { resp ->
            val body = resp.body?.string() ?: return null
            runCatching { gson.fromJson(body, JsonObject::class.java) }.getOrNull()
        }
    }

    private fun status(root: JsonObject?): String? =
        root?.get("status")?.takeIf { it.isJsonPrimitive }?.asString

    private fun data(root: JsonObject?): JsonObject? =
        root?.get("data")?.takeIf { it.isJsonObject }?.asJsonObject

    /**
     * Refresh token secara blocking. true bila sesi pulih.
     * @param force abaikan masa berlaku token dan paksa refresh (dipanggil saat 401).
     */
    fun refreshBlocking(force: Boolean = false): Boolean = synchronized(lock) {
        val refresh = prefs.refreshToken ?: return false
        if (!force && !prefs.tokenExpired() && !prefs.accessTokenValue().isNullOrBlank()) return true
        val root = post("api/app/auth/refresh-token", JsonObject().apply {
            addProperty("refresh_token", refresh)
        })
        val data = data(root)
        val access = data?.get("access_token")?.takeIf { it.isJsonPrimitive }?.asString
        if (status(root) == "success" && !access.isNullOrBlank()) {
            val newRefresh = data.get("refresh_token")?.takeIf { it.isJsonPrimitive }?.asString
            val expires = data.get("expires_in")?.takeIf { it.isJsonPrimitive }?.asInt
            prefs.saveTokens(access, newRefresh ?: refresh, expires)
            true
        } else {
            false
        }
    }

    /** Login baru dengan identitas perangkat (email tersimpan / dibuat). */
    fun loginBlocking(displayName: String? = null): Boolean = synchronized(lock) {
        val instanceId = prefs.appInstanceId
        val nonceRoot = post("api/app/auth/nonce", JsonObject().apply {
            addProperty("app_instance_id", instanceId)
            addProperty("device_type", "android_mobile")
        })
        val nonce = data(nonceRoot)?.get("nonce")?.takeIf { it.isJsonPrimitive }?.asString
            ?: return false

        val email = prefs.email ?: "stream_${instanceId.replace("-", "").take(12)}@mikdad.stream"
        val name = displayName ?: prefs.profile.value?.displayName ?: "Pengguna Mikdad"
        val loginRoot = post("api/app/auth/login/google-account", JsonObject().apply {
            addProperty("email", email)
            addProperty("display_name", name)
            addProperty("app_instance_id", instanceId)
            addProperty("device_type", "android_mobile")
            addProperty("nonce", nonce)
        })
        val data = data(loginRoot)
        val access = data?.get("access_token")?.takeIf { it.isJsonPrimitive }?.asString ?: return false
        val refresh = data.get("refresh_token")?.takeIf { it.isJsonPrimitive }?.asString
        val expires = data.get("expires_in")?.takeIf { it.isJsonPrimitive }?.asInt
        prefs.saveEmail(email)
        prefs.saveTokens(access, refresh, expires)
        prefs.saveProfile(name, null, null, null)
        true
    }

    /** Pastikan ada sesi valid (token aktif / refresh / login baru). */
    suspend fun ensureSession(): Boolean = withContext(Dispatchers.IO) {
        val access = prefs.accessTokenValue()
        if (!access.isNullOrBlank() && !prefs.tokenExpired()) return@withContext true
        if (prefs.refreshToken != null && refreshBlocking()) return@withContext true
        loginBlocking()
    }

    /** Paksa login baru (dipakai bila sesi rusak). */
    suspend fun forceLogin(displayName: String? = null): Boolean =
        withContext(Dispatchers.IO) { loginBlocking(displayName) }
}

/** Menyelipkan Bearer token + pemulihan otomatis saat 401. */
private class AuthInterceptor(private val auth: AuthGateway, private val prefs: PrefsStore) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val original = chain.request()

        if (prefs.tokenExpired() && prefs.refreshToken != null) {
            runCatching { auth.refreshBlocking() }
        }

        var response = chain.proceed(withToken(original))
        if (response.code == 401) {
            response.close()
            val recovered = runCatching { auth.refreshBlocking(force = true) }.getOrDefault(false) ||
                runCatching { auth.loginBlocking() }.getOrDefault(false)
            if (recovered) {
                response = chain.proceed(withToken(original))
            }
        }
        return response
    }

    private fun withToken(request: Request): Request {
        val token = prefs.accessTokenValue() ?: return request
        return request.newBuilder().header("Authorization", "Bearer $token").build()
    }
}

class ApiClient(prefs: PrefsStore) {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    /** Client utama (dengan auth) — dipakai semua endpoint konten. */
    val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(45, TimeUnit.SECONDS)
        .writeTimeout(45, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    private val gateway = AuthGateway(prefs, client)

    val auth: AuthGateway get() = gateway

    private val authenticatedClient: OkHttpClient = client.newBuilder()
        .addInterceptor(AuthInterceptor(gateway, prefs))
        .build()

    val api: MikdadApi = Retrofit.Builder()
        .baseUrl(ApiConfig.baseUrl)
        .client(authenticatedClient)
        .addConverterFactory(GsonConverterFactory.create(ApiConfig.gson))
        .build()
        .create(MikdadApi::class.java)

    init {
        // pastikan logging hanya di debug agar ringan
        @Suppress("UNUSED_EXPRESSION")
        logging
    }
}
