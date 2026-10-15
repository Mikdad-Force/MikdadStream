package com.mikdadstream.app.data.remote

import com.google.gson.annotations.SerializedName

// ---------- Envelope umum API ----------

data class Envelope<T>(
    val code: Int? = null,
    val status: String? = null,
    val message: String? = null,
    val data: T? = null,
)

data class ModelsEnvelope(
    val code: Int? = null,
    val status: String? = null,
    val data: List<SourceModel>? = null,
    @SerializedName("searchall_movie") val searchAllMovie: Boolean? = null,
    @SerializedName("searchall_short_drama") val searchAllShortDrama: Boolean? = null,
)

// ---------- Model sumber ----------

data class SourceModel(
    val id: String? = null,
    val name: String? = null,
    @SerializedName("icon_url") val iconUrl: String? = null,
    val description: String? = null,
    @SerializedName("content_type") val contentType: String? = null,
    val status: String? = null,
) {
    val safeId: String get() = id.orEmpty()
    val safeName: String get() = name ?: safeId
    val isActive: Boolean get() = status.equals("active", true)

    val typeLabel: String
        get() = when (contentType) {
            "movie_tv" -> "Film & Serial"
            "short_drama" -> "Drama Pendek"
            "live_tv" -> "TV Langsung"
            else -> contentType.orEmpty().ifBlank { "Konten" }
        }
}

data class Category(
    val id: String? = null,
    val name: String? = null,
    @SerializedName("tab_key") val tabKey: String? = null,
    @SerializedName("position_index") val positionIndex: String? = null,
) {
    val safeId: String get() = id.orEmpty()
    val safeName: String get() = name ?: safeId
}

data class CategoriesData(
    val data: List<Category>? = null,
    @SerializedName("model_id") val modelId: String? = null,
)

// ---------- Daftar video ----------

data class MediaItem(
    val key: String? = null,
    val id: String? = null,
    val cover: String? = null,
    val title: String? = null,
    val desc: String? = null,
    val tag: List<String>? = null,
    @SerializedName("episode_count") val episodeCount: Int? = null,
    @SerializedName("hot_score") val hotScore: String? = null,
    val source: String? = null,
    val type: String? = null,
    @SerializedName("cover_is_landscape") val coverIsLandscape: Boolean? = null,
    @SerializedName("detailPath") val detailPath: String? = null,
) {
    val safeId: String get() = id ?: key.orEmpty()
    val safeTitle: String get() = title?.ifBlank { null } ?: "Tanpa Judul"
    val isNews: Boolean get() = type == "news"
}

data class VideosData(
    val type: String? = null,
    @SerializedName("module_type") val moduleType: String? = null,
    @SerializedName("module_name") val moduleName: String? = null,
    @SerializedName("module_key") val moduleKey: String? = null,
    val items: List<MediaItem>? = null,
    val page: Int? = null,
    @SerializedName("has_more") val hasMore: Boolean? = null,
)

data class SearchData(
    val items: List<MediaItem>? = null,
    val query: String? = null,
)

data class SearchBody(
    val query: String? = null,
    val q: String? = null,
    @SerializedName("model_id") val modelId: String? = null,
    val page: Int = 1,
    @SerializedName("content_type") val contentType: String? = null,
    @SerializedName("excluded_models") val excludedModels: List<String>? = null,
)

// ---------- Detail ----------

data class EpisodeData(
    @SerializedName("episode_id") val episodeId: String? = null,
    @SerializedName("episode_number") val episodeNumber: Int? = null,
    @SerializedName("badge_label") val badgeLabel: String? = null,
    @SerializedName("is_free_preview") val isFreePreview: Boolean? = null,
    @SerializedName("is_premium") val isPremium: Boolean? = null,
    @SerializedName("locked_reason") val lockedReason: String? = null,
    @SerializedName("free_episode_count") val freeEpisodeCount: Int? = null,
    @SerializedName("streaming_required") val streamingRequired: Boolean? = null,
    @SerializedName("download_required") val downloadRequired: Boolean? = null,
    @SerializedName("duration_seconds") val durationSeconds: Int? = null,
    @SerializedName("is_vip") val isVip: Boolean? = null,
    @SerializedName("thumbnail_url") val thumbnailUrl: String? = null,
    @SerializedName("video_url") val videoUrl: String? = null,
) {
    val safeId: String get() = episodeId.orEmpty()
    val safeNumber: Int get() = episodeNumber ?: 0
    val isLocked: Boolean get() = isPremium == true && isFreePreview != true
}

data class DetailData(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    @SerializedName("duration_seconds") val durationSeconds: Int? = null,
    val category: String? = null,
    @SerializedName("model_id") val modelId: String? = null,
    val cover: String? = null,
    @SerializedName("poster_landscape") val posterLandscape: String? = null,
    @SerializedName("last_episode_id") val lastEpisodeId: String? = null,
    val episodes: List<EpisodeData>? = null,
) {
    val safeId: String get() = id.orEmpty()
    val safeModelId: String get() = modelId.orEmpty()
    val safeTitle: String get() = title ?: "Tanpa Judul"
    val episodeList: List<EpisodeData> get() = episodes.orEmpty().filter { !it.safeId.isBlank() }
}

// ---------- Sumber pemutaran ----------

data class MediaStream(
    val server: String? = null,
    val format: String? = null,
    val quality: String? = null,
    @SerializedName("quality_code") val qualityCode: Int? = null,
    val url: String? = null,
    @SerializedName("master_url") val masterUrl: String? = null,
    @SerializedName("is_drm") val isDrm: Boolean? = null,
    @SerializedName("drm_type") val drmType: String? = null,
    val headers: Map<String, String>? = null,
) {
    val safeUrl: String? get() = url?.ifBlank { null } ?: masterUrl?.ifBlank { null }
    val isEmbed: Boolean
        get() {
            val f = (format ?: "").lowercase()
            if (f in setOf("embed", "iframe", "hoster", "html", "widget")) return true
            if (f in setOf("hls", "dash", "mp4", "flv")) return false
            val u = safeUrl?.lowercase() ?: return false
            return listOf(
                "blogger.com/video.g", "mega.nz/embed", "gofile.io/d/",
                "pixeldrain.com/", "mediafire.com/file",
            ).any { it in u }
        }
    val label: String
        get() = listOfNotNull(server, quality?.takeIf { it.isNotBlank() })
            .joinToString(" • ")
            .ifBlank { "Sumber" }
    val kindLabel: String
        get() = (format ?: "").uppercase().ifBlank { "STREAM" }
}

data class SubtitleTrack(
    @SerializedName("name") val label: String? = null,
    val lang: String? = null,
    val url: String? = null,
)

data class SourceData(
    @SerializedName("content_access") val contentAccess: String? = null,
    @SerializedName("episode_id") val episodeId: String? = null,
    @SerializedName("playback_profile") val playbackProfile: String? = null,
    val streams: List<MediaStream>? = null,
    @SerializedName("download_links") val downloadLinks: List<MediaStream>? = null,
    val subtitles: List<SubtitleTrack>? = null,
) {
    val playableStreams: List<MediaStream> get() = streams.orEmpty().filter { !it.safeUrl.isNullOrBlank() }
    val files: List<MediaStream> get() = downloadLinks.orEmpty().filter { !it.safeUrl.isNullOrBlank() }
    val isPremium: Boolean get() = contentAccess?.contains("premium", true) == true
}

// ---------- Akun & autentikasi ----------

data class User(
    val id: String? = null,
    @SerializedName("user_number") val userNumber: Int? = null,
    @SerializedName("google_sub") val googleSub: String? = null,
    val email: String? = null,
    @SerializedName("display_name") val displayName: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    val premium: Boolean? = null,
)

data class AccountData(
    @SerializedName("is_authenticated") val isAuthenticated: Boolean? = null,
    @SerializedName("server_time") val serverTime: String? = null,
    @SerializedName("cache_ttl_seconds") val cacheTtlSeconds: Int? = null,
    @SerializedName("cache_policy") val cachePolicy: String? = null,
    val user: User? = null,
    val premium: Boolean? = null,
)

data class NonceBody(
    @SerializedName("app_instance_id") val appInstanceId: String,
    @SerializedName("device_type") val deviceType: String = "android_mobile",
    @SerializedName("ui_mode") val uiMode: String? = null,
)

data class NonceData(
    val nonce: String? = null,
    @SerializedName("expires_in") val expiresIn: Int? = null,
)

data class LoginBody(
    val email: String,
    @SerializedName("display_name") val displayName: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("google_sub") val googleSub: String? = null,
    @SerializedName("app_instance_id") val appInstanceId: String? = null,
    @SerializedName("device_type") val deviceType: String = "android_mobile",
    val nonce: String? = null,
)

data class RefreshBody(
    @SerializedName("refresh_token") val refreshToken: String,
)

data class TokenData(
    @SerializedName("access_token") val accessToken: String? = null,
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("token_type") val tokenType: String? = null,
    @SerializedName("expires_in") val expiresIn: Int? = null,
    @SerializedName("access_token_expires_at") val accessTokenExpiresAt: String? = null,
    @SerializedName("refresh_token_expires_at") val refreshTokenExpiresAt: String? = null,
    @SerializedName("user_id") val userId: String? = null,
    val provider: String? = null,
)
