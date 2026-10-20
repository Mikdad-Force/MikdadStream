package com.mikdadstream.app.ui.player

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.util.Log
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.PlaylistPlay
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PictureInPicture
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.media3.common.C
import androidx.media3.common.MediaItem as ExoMediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.TrackGroup
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.LoadEventInfo
import androidx.media3.exoplayer.source.MediaLoadData
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.mikdadstream.app.AppGraph
import com.mikdadstream.app.BuildConfig
import com.mikdadstream.app.data.remote.ApiError
import com.mikdadstream.app.data.remote.EpisodeData
import com.mikdadstream.app.data.remote.MediaStream
import com.mikdadstream.app.data.remote.SubtitleTrack
import com.mikdadstream.app.ui.detail.PlayRequest
import com.mikdadstream.app.ui.tv.TvRequestFocusOnShow
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private data class SubOption(val group: TrackGroup?, val index: Int, val key: String, val label: String)

private data class HudInfo(val icon: ImageVector?, val text: String)

private enum class GestureZone { NONE, BRIGHTNESS, VOLUME, SEEK }

private class GestureState {
    var zone = GestureZone.NONE
    var startY = 0f
    var startPos = 0L
    var startBrightness = 0.5f
    var startVolume = 0
    var accX = 0f
    var accY = 0f
    var lastLiveSeekAt = 0L
}

/**
 * Jembatan antara tombol remote (diproses di PlayerActivity) dengan status pemutar
 * Compose. Diisi oleh PlayerScreen selama ia aktif.
 */
internal object PlayerRemoteController {
    var ready: Boolean = false
    var controlsVisible: () -> Boolean = { false }
    var togglePlay: () -> Unit = {}
    var play: () -> Unit = {}
    var pause: () -> Unit = {}
    var seekBack: () -> Unit = {}
    var seekForward: () -> Unit = {}
    var showControls: () -> Unit = {}

    fun reset() {
        ready = false
        controlsVisible = { false }
        togglePlay = {}
        play = {}
        pause = {}
        seekBack = {}
        seekForward = {}
        showControls = {}
    }
}

private fun resolveStreamUrl(url: String?): String? {
    val u = url?.takeIf { it.isNotBlank() } ?: return null
    if (u.startsWith("http://") || u.startsWith("https://")) return u
    if (u.startsWith("/")) return BuildConfig.API_BASE_URL.trimEnd('/') + u
    return u
}

/**
 * Normalisasi kode bahasa ke satu format agar perbandingan konsisten.
 * Dipakai ulang di seluruh rantai subtitle (pencocokan, label, dedup, diagnostik).
 *
 * Aturan:
 *  - "id", "in", "ind", "indonesia", "in_id", "id_id", "id-id", "in-id" -> "id"
 *    (Java/Android Locale versi lama memakai "in" untuk Indonesia).
 *  - String yang mengandung "indonesi"/"bahasa" (mis. "bahasa indonesia", "id_id") -> "id".
 *  - Kode lain dinormalisasi ke bentuk dasar huruf kecil tanpa pemisah "_" / "-" / ".".
 *  - String kosong -> "".
 */
fun normalizeLangCode(code: String?): String {
    val raw = (code ?: "").trim().lowercase()
    if (raw.isEmpty()) return ""
    if (raw.contains("indonesi") || raw.contains("bahasa")) return "id"
    val base = raw.replace('.', '_').replace('-', '_')
        .substringBefore('_').substringBefore('-')
    return when (base) {
        "id", "in", "ind" -> "id"
        else -> base
    }
}

private fun subtitleMime(url: String): String? {
    val path = url.substringBefore('?').lowercase()
    return when {
        path.endsWith(".vtt") -> "text/vtt"
        path.endsWith(".srt") -> "application/x-subrip"
        path.endsWith(".ttml") || path.endsWith(".dfxp") -> "application/ttml+xml"
        path.endsWith(".ass") || path.endsWith(".ssa") -> "text/x-ssa"
        // Ekstensi tak dikenal (mis. URL tanpa ekstensi): subtitle sudah
        // dinormalisasi ke WebVTT saat disiapkan, jadi anggap VTT agar track
        // Indonesia tidak pernah dibuang diam-diam.
        else -> "text/vtt"
    }
}

/**
 * Baca beberapa byte awal file subtitle lokal ("file://...") untuk deteksi isi.
 * Mengembalikan null untuk URL remote atau file yang tidak bisa dibaca.
 */
private fun localSubtitleHead(url: String): String? {
    if (!url.startsWith("file://")) return null
    return try {
        val f = java.io.File(java.net.URI(url))
        if (!f.exists() || f.length() == 0L) {
            null
        } else {
            java.io.FileInputStream(f).use { ins ->
                val buf = ByteArray(48)
                val n = ins.read(buf)
                if (n <= 0) null else String(buf, 0, n, Charsets.UTF_8).removePrefix("\uFEFF")
            }
        }
    } catch (e: Exception) {
        null
    }
}

/**
 * Pilih MIME subtitle berdasarkan ISI file (bukan hanya ekstensi):
 * file lokal yang berawalan "WEBVTT" -> text/vtt; berisi timestamp
 * SRT "00:00:01,000" -> application/x-subrip. Bila isi tak terdeteksi,
 * jatuh ke tebakan per-ekstensi (subtitleMime). Mencegah kasus VTT yang
 * dibaca sebagai SRT (atau sebaliknya) sehingga parser gagal diam-diam.
 */
private fun detectSubtitleMime(url: String): String {
    val head = localSubtitleHead(url)
    return when {
        head != null && head.trimStart().startsWith("WEBVTT", true) -> "text/vtt"
        head != null && Regex("\\d{1,2}:\\d{2}:\\d{2}[,.]\\d{3}").containsMatchIn(head) ->
            "application/x-subrip"
        else -> subtitleMime(url) ?: "text/vtt"
    }
}

private fun formatOffset(ms: Long): String {
    val seconds = ms / 1000.0
    val text = String.format(java.util.Locale.US, "%.1f", seconds)
    return if (ms > 0) "+${text}s" else "${text}s"
}

private fun md5Key(text: String): String =
    java.security.MessageDigest.getInstance("MD5")
        .digest(text.toByteArray())
        .joinToString("") { "%02x".format(it) }
        .take(24)

/** Konversi teks SRT mentah menjadi WebVTT. */
private fun srtTextToVtt(srt: String): String {
    val clean = srt.removePrefix("\uFEFF").replace("\r\n", "\n").replace('\r', '\n')
    val sb = StringBuilder("WEBVTT\n\n")
    val ts = Regex("(\\d{1,2}):(\\d{2}):(\\d{2}),(\\d{3})")
    sb.append(ts.replace(clean) { m ->
        "${m.groupValues[1]}:${m.groupValues[2]}:${m.groupValues[3]}.${m.groupValues[4]}"
    })
    return sb.toString()
}

/** Pastikan teks berupa WebVTT; null jika isi bukan SRT/VTT. */
private fun normalizeSubtitleText(text: String): String? {
    val clean = text.removePrefix("\uFEFF").replace("\r\n", "\n").replace('\r', '\n')
    val trimmed = clean.trimStart()
    if (trimmed.startsWith("WEBVTT", true)) return clean
    if (Regex("\\d{1,2}:\\d{2}:\\d{2},\\d{3}").containsMatchIn(clean)) return srtTextToVtt(clean)
    return null
}

private suspend fun fetchSubtitleText(url: String): String? = withContext(Dispatchers.IO) {
    try {
        if (url.startsWith("file://")) {
            val f = java.io.File(java.net.URI(url))
            if (f.exists() && f.length() > 0) f.readText() else null
        } else {
            val conn = java.net.URL(url).openConnection() as java.net.HttpURLConnection
            conn.connectTimeout = 20_000
            conn.readTimeout = 45_000
            conn.setRequestProperty("User-Agent", "ExoPlayerLib/2.19.1")
            try {
                if (conn.responseCode !in 200..299) null
                else conn.inputStream.bufferedReader().use { it.readText() }
            } finally {
                conn.disconnect()
            }
        }
    } catch (e: Exception) {
        null
    }
}

/**
 * Ambil isi subtitle sebagai WebVTT (unduh bila remote, konversi bila SRT).
 * Hasil remote di-cache di ".../subs/sb4_<hash>.vtt".
 */
private suspend fun localVttContent(url: String): String? = withContext(Dispatchers.IO) {
    if (url.startsWith("file://")) {
        val f = java.io.File(java.net.URI(url))
        if (!f.exists() || f.length() == 0L) return@withContext null
        return@withContext normalizeSubtitleText(f.readText())
    }
    val ctx = try { AppGraph.appContext } catch (e: Throwable) { null }
        ?: return@withContext null
    val dir = java.io.File(ctx.cacheDir, "subs").apply { mkdirs() }
    val f = java.io.File(dir, "sb4_${md5Key(url)}.vtt")
    if (f.exists() && f.length() > 0) {
        val cached = f.readText()
        if (cached.trimStart().removePrefix("\uFEFF").startsWith("WEBVTT", true)) {
            return@withContext cached
        }
        f.delete()
    }
    val body = fetchSubtitleText(url) ?: return@withContext null
    val vtt = normalizeSubtitleText(body) ?: return@withContext null
    f.writeText(vtt)
    vtt
}

/** Geser semua timestamp WebVTT sebesar offset (ms). Timestamp negatif -> 0. */
private fun shiftVttTimestamps(content: String, offsetMs: Long): String {
    val ts = Regex("(\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3})")
    return ts.replace(content) { m ->
        val total = ((m.groupValues[1].toLong() * 3600 +
            m.groupValues[2].toLong() * 60 +
            m.groupValues[3].toLong()) * 1000 +
            m.groupValues[4].toLong() + offsetMs).coerceAtLeast(0L)
        val hh = total / 3_600_000
        val mm = (total % 3_600_000) / 60_000
        val ss = (total % 60_000) / 1000
        val millis = total % 1000
        "%02d:%02d:%02d.%03d".format(hh, mm, ss, millis)
    }
}

/**
 * URL subtitle yang sudah digeser sesuai offset (disimpan sebagai file lokal
 * "sbshift_<hash>.vtt"). Offset 0 -> URL asli.
 */
private suspend fun shiftedSubtitleUrl(url: String, offsetMs: Long): String =
    withContext(Dispatchers.IO) {
        if (offsetMs == 0L) return@withContext url
        val ctx = try { AppGraph.appContext } catch (e: Throwable) { null }
            ?: return@withContext url
        val dir = java.io.File(ctx.cacheDir, "subs").apply { mkdirs() }
        val f = java.io.File(dir, "sbshift_${md5Key("$url|$offsetMs")}.vtt")
        if (f.exists() && f.length() > 0) {
            val cached = f.readText().take(64)
            if (cached.trimStart().removePrefix("\uFEFF").startsWith("WEBVTT", true)) {
                return@withContext android.net.Uri.fromFile(f).toString()
            }
            f.delete()
        }
        val base = localVttContent(url) ?: return@withContext url
        f.writeText(shiftVttTimestamps(base, offsetMs))
        android.net.Uri.fromFile(f).toString()
    }

private fun subtitleLangName(code: String?): String? {
    val raw = (code ?: "").trim().lowercase()
    if (raw.isEmpty()) return null
    if (raw.contains("\u4e2d")) return if (raw.contains("\u7e41")) "\u7e41\u9ad4\u4e2d\u6587" else "\u7b80\u9ad4\u4e2d\u6587"
    return when (normalizeLangCode(code)) {
        "id" -> "Indonesia"
        "en", "eng", "english" -> "English"
        "ms", "may", "ms_my", "malay", "melayu" -> "Melayu"
        "jv", "jav", "jawa", "javanese" -> "Jawa"
        "su", "sun", "sunda", "sundanese" -> "Sunda"
        "zh", "chi", "zho", "chs", "cht", "chinese" -> "\u4e2d\u6587"
        "ja", "jpn", "japanese" -> "\u65e5\u672c\u8a9e"
        "ko", "kor", "korean" -> "\ud55c\uad6d\uc5b4"
        "es", "spa", "spanish" -> "Espa\u00f1ol"
        "ar", "ara", "arabic" -> "\u0627\u0644\u0639\u0631\u0628\u064a\u0629"
        "pt", "por", "portuguese", "po" -> "Portugu\u00eas"
        "fr", "fra", "fre", "french" -> "Fran\u00e7ais"
        "de", "deu", "ger", "german" -> "Deutsch"
        "th", "tha", "thai" -> "\u0e44\u0e17\u0e22"
        "vi", "vie", "vietnamese" -> "Ti\u1ebfng Vi\u1ec7t"
        "ru", "rus", "russian" -> "\u0420\u0443\u0441\u0441\u043a\u0438\u0439"
        "tl", "fil", "filipino", "tagalog" -> "Filipino"
        else -> null
    }
}

private fun formatTime(ms: Long): String {
    if (ms <= 0L) return "0:00"
    val s = ms / 1000
    val h = s / 3600
    val m = (s % 3600) / 60
    val sec = s % 60
    return if (h > 0) "%d:%02d:%02d".format(h, m, sec) else "%d:%02d".format(m, sec)
}

private fun formatSpeed(v: Float): String =
    if (v == v.toInt().toFloat()) "${v.toInt()}\u00d7" else "$v\u00d7"

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    request: PlayRequest,
    onBack: () -> Unit,
    onProgress: (
        modelId: String,
        contentId: String,
        title: String,
        cover: String?,
        episodeId: String,
        episodeNumber: Int,
        positionMs: Long,
        durationMs: Long,
    ) -> Unit,
) {
    val context = LocalContext.current
    val audioManager = remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    val scope = rememberCoroutineScope()

    var episodeId by remember { mutableStateOf(request.episodeId) }
    var episodeNumber by remember { mutableIntStateOf(request.episodeNumber) }
    var episodes by remember { mutableStateOf(request.episodes) }

    var streams by remember { mutableStateOf<List<MediaStream>>(emptyList()) }
    var apiSubtitles by remember { mutableStateOf<List<SubtitleTrack>>(emptyList()) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    var loadingStreams by remember { mutableStateOf(true) }
    var streamError by remember { mutableStateOf<String?>(null) }
    // Apakah kita berhasil MEMASANG track Indonesia (dibangun di SubtitleConfiguration).
    var installedHasIndo by remember { mutableStateOf(false) }

    var showSources by remember { mutableStateOf(false) }
    var showEpisodes by remember { mutableStateOf(false) }
    var playerError by remember { mutableStateOf<String?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var buffering by remember { mutableStateOf(false) }

    // ---- Kontrol lanjutan ----
    var showControls by remember { mutableStateOf(true) }
    var uiPosition by remember { mutableLongStateOf(0L) }
    var uiDuration by remember { mutableLongStateOf(0L) }
    var userSeek by remember { mutableStateOf<Long?>(null) }
    var speed by remember { mutableFloatStateOf(1f) }
    var showSpeedMenu by remember { mutableStateOf(false) }
    var showSubMenu by remember { mutableStateOf(false) }
    var showSleepMenu by remember { mutableStateOf(false) }
    var resizeModeIx by remember { mutableIntStateOf(0) }
    var subOptions by remember { mutableStateOf<List<SubOption>>(emptyList()) }
    var selectedSubKey by remember { mutableStateOf<String?>(null) }
    var subtitleUserChosen by remember { mutableStateOf(false) }
    // Sinkronisasi subtitle manual: offset waktu subtitle terhadap video.
    var subOffsetMs by remember { mutableLongStateOf(0L) }
    var subOffsetTrigger by remember { mutableIntStateOf(0) }
    var showSyncMenu by remember { mutableStateOf(false) }
    var showQualityMenu by remember { mutableStateOf(false) }
    var videoQualities by remember { mutableStateOf<List<Int>>(emptyList()) }
    var selectedVideoHeight by remember { mutableStateOf<Int?>(null) }
    var hud by remember { mutableStateOf<HudInfo?>(null) }
    var hudJob by remember { mutableStateOf<Job?>(null) }
    var sleepEndsAt by remember { mutableLongStateOf(0L) }

    val activityRef = remember { mutableStateOf<PlayerActivity?>(null) }
    LaunchedEffect(LocalContext.current) {
        activityRef.value = context.findActivity() as? PlayerActivity
    }
    val inPip = activityRef.value?.inPipState?.value == true
    val gesture = remember { GestureState() }
    val controlFocus = remember { FocusRequester() }
    val backFocus = remember { FocusRequester() }
    val rewindFocus = remember { FocusRequester() }
    val forwardFocus = remember { FocusRequester() }
    val bottomFirstFocus = remember { FocusRequester() }

    val resizeModes = intArrayOf(
        AspectRatioFrameLayout.RESIZE_MODE_FIT,
        AspectRatioFrameLayout.RESIZE_MODE_FILL,
        AspectRatioFrameLayout.RESIZE_MODE_ZOOM,
    )
    val resizeLabels = arrayOf("Muat", "Penuh", "Zoom")

    val streamHeaders = remember {
        java.util.concurrent.atomic.AtomicReference<Map<String, String>>(emptyMap())
    }
    val dataSourceFactory = remember {
        val httpFactory = object : androidx.media3.datasource.DataSource.Factory {
            override fun createDataSource(): androidx.media3.datasource.DataSource {
                val dataSource = DefaultHttpDataSource.Factory()
                    .setConnectTimeoutMs(20_000)
                    .setReadTimeoutMs(45_000)
                    .createDataSource() as DefaultHttpDataSource
                val headers = streamHeaders.get()
                headers.forEach { (key, value) ->
                    dataSource.setRequestProperty(key, value)
                }
                if (!headers.keys.any { it.equals("User-Agent", ignoreCase = true) }) {
                    dataSource.setRequestProperty("User-Agent", "MikdadStream/1.0.0")
                }
                return dataSource
            }
        }
        DefaultDataSource.Factory(context, httpFactory)
    }
    val player = remember {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(dataSourceFactory))
            .setSeekBackIncrementMs(10_000)
            .setSeekForwardIncrementMs(10_000)
            .setHandleAudioBecomingNoisy(true)
            .build()
    }
    val pendingSeek = remember { mutableLongStateOf(request.startPositionMs) }
    var reload by remember { mutableIntStateOf(0) }
    val lastPosition = remember { mutableLongStateOf(0L) }
    val lastDuration = remember { mutableLongStateOf(0L) }

    val selectedStream = streams.getOrNull(selectedIndex)
    val mediaReady = streamError == null && streams.isNotEmpty() && !loadingStreams

    fun saveProgressNow() {
        onProgress(
            request.modelId,
            request.contentId,
            request.title,
            request.cover,
            episodeId,
            episodeNumber,
            lastPosition.longValue,
            lastDuration.longValue,
        )
    }

    fun playEpisode(target: EpisodeData) {
        saveProgressNow()
        pendingSeek.longValue = 0L
        userSeek = null
        episodeId = target.safeId
        episodeNumber = target.safeNumber
        streams = emptyList()
        playerError = null
        showEpisodes = false
        loadingStreams = true
    }

    fun showHud(icon: ImageVector?, text: String) {
        hudJob?.cancel()
        hud = HudInfo(icon, text)
        hudJob = scope.launch {
            delay(900)
            hud = null
        }
    }

    /**
     * Pilih track subtitle Indonesia langsung dari currentTracks.
     * Tidak bergantung pada event onTracksChanged / pilihan otomatis ExoPlayer,
     * sehingga tidak pernah jatuh ke Melayu/English.
     */
    fun applyIndonesianSubtitle(force: Boolean = false): Boolean {
        if (subtitleUserChosen && !force) return false
        player.currentTracks.groups.forEach { g ->
            if (g.type != C.TRACK_TYPE_TEXT) return@forEach
            for (i in 0 until g.length) {
                val f = g.getTrackFormat(i)
                if (normalizeLangCode(f.language) == "id" || subtitleLangName(f.label) == "Indonesia") {
                    val bp = player.trackSelectionParameters.buildUpon()
                        .clearOverridesOfType(C.TRACK_TYPE_TEXT)
                        .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, false)
                        .setOverrideForType(TrackSelectionOverride(g.mediaTrackGroup, i))
                    player.trackSelectionParameters = bp.build()
                    selectedSubKey = "${g.mediaTrackGroup.id}#$i"
                    Log.i(
                        "PlayerScreen",
                        "Pilih subtitle Indonesia: lang=${f.language} label=${f.label} idx=$i",
                    )
                    return true
                }
            }
        }
        return false
    }

    fun refreshSubOptions() {
        val opts = mutableListOf<SubOption>()
        var selectedKey: String? = null
        val seenCodes = mutableSetOf<String>()
        player.currentTracks.groups.forEach { g ->
            if (g.type != C.TRACK_TYPE_TEXT) return@forEach
            val tg = g.mediaTrackGroup
            for (i in 0 until g.length) {
                val f = g.getTrackFormat(i)
                // Dedup per kode hasil normalisasi (bukan label): track Indonesia
                // tidak pernah dibuang hanya karena label/name beda.
                val code = normalizeLangCode(f.language)
                if (!seenCodes.add(code)) continue
                val langName = subtitleLangName(f.language) ?: subtitleLangName(f.label)
                val label = langName
                    ?: f.label?.takeIf { it.isNotBlank() }
                    ?: f.language?.takeIf { it.isNotBlank() }
                    ?: "Subtitle ${opts.size + 1}"
                val key = "${tg.id}#$i"
                opts.add(SubOption(tg, i, key, label))
                if (g.isTrackSelected(i)) selectedKey = key
            }
        }
        // Fallback: tampilkan juga bahasa yang ADA di API tetapi belum terdaftar
        // di pemutar (mis. track gagal diparse). Menu jadi selalu sinkron dengan
        // daftar bahasa video; memilihnya memicu muat ulang track.
        apiSubtitles.forEach { s ->
            val code = normalizeLangCode(s.lang ?: s.label)
            if (code.isBlank() || code in seenCodes) return@forEach
            seenCodes.add(code)
            val label = subtitleLangName(code) ?: s.label?.takeIf { it.isNotBlank() } ?: code
            opts.add(SubOption(null, -1, "api:$code", label))
        }
        subOptions = opts.sortedBy { if (it.label == "Indonesia") 0 else 1 }
        selectedSubKey = selectedKey
        // Pilih Indonesia eksplisit bila belum ada yang terpilih.
        if (selectedKey == null) applyIndonesianSubtitle()
    }

    fun selectSubtitle(opt: SubOption?) {
        // Pilihan manual pengguna: jangan ditimpa auto-select Indonesia.
        subtitleUserChosen = true
        val group = opt?.group
        if (opt != null && group == null) {
            // Bahasa ada di API tapi belum terdaftar di pemutar. Coba cari group
            // yang cocok berdasarkan kode bahasa, kalau tidak ada muat ulang.
            val code = opt.key.removePrefix("api:")
            var applied = false
            player.currentTracks.groups.forEach { g ->
                if (applied || g.type != C.TRACK_TYPE_TEXT) return@forEach
                for (i in 0 until g.length) {
                    if (normalizeLangCode(g.getTrackFormat(i).language) == code) {
                        val bp = player.trackSelectionParameters.buildUpon()
                            .clearOverridesOfType(C.TRACK_TYPE_TEXT)
                            .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, false)
                            .setOverrideForType(TrackSelectionOverride(g.mediaTrackGroup, i))
                        player.trackSelectionParameters = bp.build()
                        selectedSubKey = "${g.mediaTrackGroup.id}#$i"
                        applied = true
                        return@forEach
                    }
                }
            }
            if (!applied) {
                pendingSeek.longValue = player.currentPosition
                subOffsetTrigger++
                showHud(Icons.Filled.Subtitles, "Memuat subtitle ${opt.label}…")
            } else {
                showHud(Icons.Filled.Subtitles, "Subtitle ${opt.label}")
            }
            return
        }
        val bp = player.trackSelectionParameters.buildUpon()
        bp.clearOverridesOfType(C.TRACK_TYPE_TEXT)
        if (opt != null && group != null) {
            bp.setTrackTypeDisabled(C.TRACK_TYPE_TEXT, false)
                .setOverrideForType(TrackSelectionOverride(group, opt.index))
        } else {
            bp.setTrackTypeDisabled(C.TRACK_TYPE_TEXT, true)
        }
        player.trackSelectionParameters = bp.build()
        selectedSubKey = opt?.key
    }

    fun refreshVideoQualities() {
        val heights = LinkedHashSet<Int>()
        player.currentTracks.groups.forEach { g ->
            if (g.type != C.TRACK_TYPE_VIDEO) return@forEach
            for (i in 0 until g.length) {
                val h = g.getTrackFormat(i).height
                if (h > 0) heights.add(h)
            }
        }
        videoQualities = heights.sortedDescending()
    }

    fun selectVideoHeight(height: Int?) {
        val bp = player.trackSelectionParameters.buildUpon()
        bp.clearOverridesOfType(C.TRACK_TYPE_VIDEO)
        if (height != null) {
            var applied = false
            player.currentTracks.groups.forEach { g ->
                if (applied || g.type != C.TRACK_TYPE_VIDEO) return@forEach
                for (i in 0 until g.length) {
                    if (g.getTrackFormat(i).height == height) {
                        bp.setOverrideForType(TrackSelectionOverride(g.mediaTrackGroup, i))
                        applied = true
                        break
                    }
                }
            }
        }
        player.trackSelectionParameters = bp.build()
        selectedVideoHeight = height
    }

    fun seekTo(target: Long, show: Boolean = true) {
        val dur = player.duration.takeIf { it > 0 } ?: uiDuration
        val t = if (dur > 0) target.coerceIn(0L, dur) else target.coerceAtLeast(0L)
        player.seekTo(t)
        uiPosition = t
        lastPosition.longValue = t
        if (show) showHud(null, formatTime(t))
    }

    fun seekBy(deltaMs: Long) {
        seekTo(player.currentPosition + deltaMs)
    }

    fun toggleControls() {
        showControls = !showControls
    }

    fun toggleOrientation() {
        val act = activityRef.value ?: return
        act.requestedOrientation =
            if (act.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            }
    }

    fun setBrightness(value: Float) {
        val act = activityRef.value ?: return
        val w = act.window ?: return
        val lp = w.attributes
        lp.screenBrightness = value.coerceIn(0.01f, 1f)
        w.attributes = lp
    }

    fun currentBrightness(): Float {
        val act = activityRef.value ?: return 0.5f
        val b = act.window?.attributes?.screenBrightness ?: -1f
        return if (b < 0f) 0.5f else b
    }

    fun protocolRank(fmt: String?): Int = when (fmt?.lowercase()) {
        "dash" -> 3
        "hls" -> 2
        "mp4", "flv" -> 1
        else -> 0
    }

    fun pickStreamIndex(list: List<MediaStream>): Int = when {
        list.isEmpty() -> 0
        list.size == 1 -> 0
        else -> list.indices.maxWithOrNull(
            compareBy<Int> { if (list[it].isEmbed) 0 else 1 }
                .thenBy { protocolRank(list[it].format) }
                .thenBy { list[it].qualityCode ?: 0 },
        ) ?: 0
    }

    // ---- Muat sumber video dari API ----
    LaunchedEffect(episodeId, reload) {
        loadingStreams = true
        streamError = null
        val local = request.localUrl
        if (!local.isNullOrBlank()) {
            streams = listOf(
                MediaStream(
                    server = "Unduhan",
                    format = "mp4",
                    quality = "Lokal",
                    url = local,
                )
            )
            apiSubtitles = emptyList()
            selectedIndex = 0
            loadingStreams = false
            return@LaunchedEffect
        }
        // Sumber sudah di-preload dari layar detail -> pemutaran langsung mulus.
        // Hanya pakai jalur cepat bila subtitle ikut ter-prefetch DAN benar-benar
        // memuat Indonesia. Bila prefetch ada tapi tanpa Indonesia (mis. metadata
        // beda/versi dub), jangan pakai jalur cepat -> lanjut fetch langsung.
        val prefetchedHasIndo = request.subtitles.any { s ->
            normalizeLangCode(s.lang) == "id" || subtitleLangName(s.label) == "Indonesia"
        }
        Log.i(
            "PlayerScreen",
            "masuk player: episode=$episodeId prefetchStreams=${request.streams.size} " +
                "prefetchSubs=${request.subtitles.size} prefetchIndo=$prefetchedHasIndo",
        )
        if (reload == 0 && episodeId == request.episodeId && request.streams.isNotEmpty()) {
            val list = request.streams
            streams = list
            // Subtitle JANGAN dipakai dari prefetch: prefetch bisa berasal dari
            // metadata/versi tanpa caption Indonesia, sehingga subtitle tidak
            // termuat. Selalu ambil ulang daftar segar dari API untuk subjek yang
            // benar-benar diputar; prefetch hanya dipakai sebagai cadangan.
            val parts = episodeId.split("_")
            val se = parts.getOrNull(0)?.toIntOrNull() ?: 1
            val ep = parts.getOrNull(1)?.toIntOrNull() ?: 1
            val fresh = runCatching {
                com.mikdadstream.app.data.moviebox.LocalMovieBoxClient
                    .fetchSubtitles(request.contentId, se, ep)
            }.getOrDefault(emptyList())
            fun indoOf(s: SubtitleTrack) =
                normalizeLangCode(s.lang ?: s.label) == "id" || subtitleLangName(s.label) == "Indonesia"
            apiSubtitles = when {
                fresh.any(::indoOf) -> fresh
                request.subtitles.any(::indoOf) -> request.subtitles
                fresh.isNotEmpty() -> fresh
                else -> request.subtitles
            }
            Log.i(
                "PlayerScreen",
                "subtitle segar: ${apiSubtitles.map { (it.lang ?: "") + "/" + (it.label ?: "") }}",
            )
            selectedIndex = pickStreamIndex(list)
            loadingStreams = false
            return@LaunchedEffect
        }
        try {
            val data = AppGraph.repository.source(request.modelId, episodeId, request.contentId)
            val list = data.playableStreams
            streams = list
            apiSubtitles = data.subtitles.orEmpty()
            Log.i("PlayerScreen", "subtitle source: ${apiSubtitles.map { (it.lang ?: "") + "/" + (it.label ?: "") }}")
            // Bila daftar tidak memuat Indonesia, coba sekali lagi langsung ke API.
            fun isIndo(s: SubtitleTrack): Boolean =
                normalizeLangCode(s.lang ?: s.label) == "id" || subtitleLangName(s.label) == "Indonesia"
            if (apiSubtitles.none(::isIndo)) {
                val parts = episodeId.split("_")
                val se = parts.getOrNull(0)?.toIntOrNull() ?: 1
                val ep = parts.getOrNull(1)?.toIntOrNull() ?: 1
                val retry = runCatching {
                    com.mikdadstream.app.data.moviebox.LocalMovieBoxClient
                        .fetchSubtitles(request.contentId, se, ep)
                }.getOrDefault(emptyList())
                Log.i("PlayerScreen", "subtitle retry: ${retry.size} track, indo=${retry.any(::isIndo)}")
                if (retry.any(::isIndo)) apiSubtitles = retry
            }
            selectedIndex = pickStreamIndex(list)
            loadingStreams = false
            if (list.isEmpty()) {
                streamError = "Tidak ada sumber pemutaran untuk episode ini."
            }
        } catch (e: ApiError) {
            loadingStreams = false
            streamError = e.message
        } catch (e: Exception) {
            loadingStreams = false
            streamError = e.message ?: "Gagal memuat sumber video."
        }
    }

    // ---- Terapkan stream terpilih ke ExoPlayer (pemutar native) ----
    LaunchedEffect(selectedIndex, episodeId, apiSubtitles, subOffsetTrigger) {
        val stream = streams.getOrNull(selectedIndex) ?: return@LaunchedEffect
        val url = resolveStreamUrl(stream.safeUrl) ?: return@LaunchedEffect
        playerError = null
        // Cegah playback ganda: hentikan media sebelumnya sebelum memuat yang baru.
        player.stop()
        player.clearMediaItems()
        streamHeaders.set(stream.headers.orEmpty())
        val builder = ExoMediaItem.Builder().setUri(url)

        // ---- DIAGNOSTIK: cetak daftar kode API, track diterima, normalisasi, cocokan "id" ----
        val apiCodes = apiSubtitles.map { normalizeLangCode(it.lang ?: it.label ?: "") }
        Log.d("PlayerScreen", "subtitle API codes: $apiCodes")
        Log.d("PlayerScreen", "subtitle received: ${apiSubtitles.map { (it.lang ?: "") + "|" + (it.label ?: "") }}")

        // Terapkan offset sinkronisasi: bila pengguna menggeser waktu subtitle,
        // setiap file subtitle digeser lalu dipakai versi lokalnya.
        val sourceSubs = if (subOffsetMs == 0L) {
            apiSubtitles
        } else {
            val out = ArrayList<SubtitleTrack>(apiSubtitles.size)
            for (s in apiSubtitles) {
                val u = s.url
                out.add(
                    if (u.isNullOrBlank()) s
                    else s.copy(url = shiftedSubtitleUrl(u, subOffsetMs)),
                )
            }
            out
        }
        Log.i("PlayerScreen", "subtitle offset=${subOffsetMs}ms tracks=${sourceSubs.size}")

        val seenLang = LinkedHashSet<String>()
        val played = sourceSubs.mapNotNull { s ->
            val u = s.url?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
            // MIME dipilih dari ISI file lokal (sniff WEBVTT/SRT) bila tersedia,
            // bukan hanya dari ekstensi URL.
            val mime = detectSubtitleMime(u)
            val lang = normalizeLangCode(s.lang ?: s.label)
            // Dedup: satu track per kode hasil normalisasi (setId jadi unik per track).
            if (!seenLang.add(lang)) return@mapNotNull null
            val indo = lang == "id"
            val headPreview = localSubtitleHead(u)?.trimStart()?.take(24)
            Log.d(
                "PlayerScreen",
                "subtitle sniff lang=$lang mime=$mime" +
                    (headPreview?.let { " head=\"$it\"" } ?: "") +
                    " uri=$u",
            )
            lang to ExoMediaItem.SubtitleConfiguration.Builder(Uri.parse(u))
                // setId unik per subtitle agar seluruh track andal terlihat di
                // currentTracks dan bisa di-override dengan pasti.
                .setId("sub_$lang")
                .setMimeType(mime)
                .setLanguage(lang)
                .setLabel(subtitleLangName(lang) ?: s.label ?: lang)
                // Track Indonesia ditandai DEFAULT: ExoPlayer memilihnya sendiri
                // tanpa perlu preferred-language dan tanpa fallback ke Melayu.
                .setSelectionFlags(if (indo) C.SELECTION_FLAG_DEFAULT else 0)
                .build()
        }
        // Indonesia dan Melayu TETAP TERPISAH (label berbeda, keduanya tersedia).
        // Indonesia diletakkan paling depan agar menjadi kandidat default pertama.
        val subs = played.sortedByDescending { it.first == "id" }.map { it.second }
        installedHasIndo = played.any { it.first == "id" }
        Log.d("PlayerScreen", "subtitle configs: ${subs.joinToString { (it.language ?: "") + "/" + (it.label ?: "") }}")
        Log.d("PlayerScreen", "id match: api=${apiCodes.contains("id")} installed=$installedHasIndo visible=${subs.any { it.label == "Indonesia" }}")
        // DIAGNOSTIK 2: cetak SEMUA SubtitleConfiguration sebelum prepare():
        // id (unik "sub_<lang>"), language (hasil normalizeLangCode), label,
        // mimeType, dan URI (lokal file:// -> VTT hasil konversi, atau remote).
        subs.forEach { c ->
            Log.i(
                "PlayerScreen",
                "SubtitleConfig id=${c.id} language=${c.language} label=${c.label} " +
                    "mime=${c.mimeType} uri=${c.uri}",
            )
        }

        if (subs.isNotEmpty()) builder.setSubtitleConfigurations(subs)
        selectedVideoHeight = null
        subtitleUserChosen = false
        selectedSubKey = null
        // Bahasa Indonesia ditandai DEFAULT pada SubtitleConfiguration DAN
        // dijadikan preferensi bahasa teks. Bila track Indonesia ada, ExoPlayer
        // memilihnya; bila tidak ada, tidak ada track lain yang otomatis dipilih
        // (sehingga tidak pernah jatuh ke Melayu).
        val newParams = player.trackSelectionParameters.buildUpon()
            .clearOverridesOfType(C.TRACK_TYPE_TEXT)
            .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
            .setPreferredTextLanguages("id", "in", "ind", "in_id")
            .setSelectUndeterminedTextLanguage(false)
            .build()
        player.trackSelectionParameters = newParams
        // DIAGNOSTIK 4: audit TrackSelectionParameters — pastikan tipe TEXT tidak
        // dinonaktifkan dan tidak ada flag yang menyembunyikan track "id".
        val textDisabled = newParams.disabledTrackTypes.contains(C.TRACK_TYPE_TEXT)
        val textOverrides = newParams.overrides.values.filter { it.getType() == C.TRACK_TYPE_TEXT }
        Log.i(
            "PlayerScreen",
            "track params: preferredText=${newParams.preferredTextLanguages} " +
                "ignoredTextFlags=0x${Integer.toHexString(newParams.ignoredTextSelectionFlags)} " +
                "undetermined=${newParams.selectUndeterminedTextLanguage} " +
                "textDisabled=$textDisabled textOverrides=$textOverrides",
        )
        if (textDisabled) {
            Log.w("PlayerScreen", "tipe TEXT dinonaktifkan -> diaktifkan kembali sebelum prepare")
            player.trackSelectionParameters = newParams.buildUpon()
                .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, false)
                .build()
        }
        player.setMediaItem(builder.build())
        val seek = pendingSeek.longValue
        if (seek > 0L) player.seekTo(seek)
        player.prepare()
        player.playWhenReady = true
        showControls = true
        // Track subtitle baru tersedia beberapa saat setelah prepare; jangan
        // hanya bergantung pada onTracksChanged (bisa terlewat).
        repeat(24) {
            if (applyIndonesianSubtitle()) return@LaunchedEffect
            delay(250)
        }
        Log.i("PlayerScreen", "Tidak ada track subtitle Indonesia untuk stream ini (${apiSubtitles.size} track)")
        if (apiSubtitles.isNotEmpty()) showHud(null, "Subtitle Indonesia tidak tersedia")
    }

    // ---- Listener pemutar ----
    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
                if (!playing) saveProgressNow()
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                buffering = playbackState == Player.STATE_BUFFERING
                lastPosition.longValue = player.currentPosition
                lastDuration.longValue = player.duration.takeIf { it > 0 } ?: 0L
                if (playbackState == Player.STATE_ENDED) {
                    val index = episodes.indexOfFirst { it.safeId == episodeId }
                    val next = episodes.getOrNull(index + 1)
                    if (next != null) playEpisode(next) else saveProgressNow()
                }
            }

        override fun onPlayerError(error: PlaybackException) {
            // DIAGNOSTIK 1a: error pemutar lengkap (termasuk jejak stack yang
            // memuat lokasi kegagalan load/parse subtitle eksternal bila ada).
            Log.e(
                "PlayerScreen",
                "playerError: code=${error.errorCodeName} msg=${error.message}\n" +
                    error.stackTraceToString(),
            )
            playerError = "Pemutaran gagal (${error.errorCodeName}). Coba ganti sumber."
            saveProgressNow()
        }

        override fun onTracksChanged(tracks: androidx.media3.common.Tracks) {
            // DIAGNOSTIK 3: cetak SEMUA track teks pada currentTracks —
            // group index, language, label, sampleMimeType, isSupported, isSelected.
            var textCount = 0
            tracks.groups.forEachIndexed { gi, g ->
                if (g.type != C.TRACK_TYPE_TEXT) return@forEachIndexed
                for (i in 0 until g.length) {
                    val f = g.getTrackFormat(i)
                    textCount++
                    Log.i(
                        "PlayerScreen",
                        "textTrack group=$gi idx=$i lang=${f.language} label=${f.label} " +
                            "mime=${f.sampleMimeType} supported=${g.isTrackSupported(i)} " +
                            "selected=${g.isTrackSelected(i)}",
                    )
                }
            }
            Log.i("PlayerScreen", "onTracksChanged: totalTextTracks=$textCount")
            refreshSubOptions()
            refreshVideoQualities()
        }

        override fun onTrackSelectionParametersChanged(
            params: androidx.media3.common.TrackSelectionParameters,
        ) {
            Log.i(
                "PlayerScreen",
                "params changed: preferredText=${params.preferredTextLanguages} " +
                    "ignoredTextFlags=0x${Integer.toHexString(params.ignoredTextSelectionFlags)} " +
                    "textDisabled=${params.disabledTrackTypes.contains(C.TRACK_TYPE_TEXT)}",
            )
            refreshSubOptions()
        }
    }
    player.addListener(listener)
    // DIAGNOSTIK 1b: listener analytics untuk event PER-LOAD. Untuk track teks
    // (subtitle) cetak URL, MIME, durasi/byte, dan kode HTTP saat gagal. Mendeteksi
    // subtitle yang gagal diunduh/parse sehingga tidak tampil di currentTracks.
    val textLoadListener = object : AnalyticsListener {
        private fun isTextLoad(mld: MediaLoadData): Boolean =
            mld.trackType == C.TRACK_TYPE_TEXT ||
                mld.trackFormat?.sampleMimeType?.startsWith("text/") == true

        override fun onLoadStarted(
            eventTime: AnalyticsListener.EventTime,
            loadEventInfo: LoadEventInfo,
            mediaLoadData: MediaLoadData,
        ) {
            if (!isTextLoad(mediaLoadData)) return
            Log.i("PlayerScreen", "subtitle load start uri=${loadEventInfo.uri}")
        }

        override fun onLoadCompleted(
            eventTime: AnalyticsListener.EventTime,
            loadEventInfo: LoadEventInfo,
            mediaLoadData: MediaLoadData,
        ) {
            if (!isTextLoad(mediaLoadData)) return
            Log.i(
                "PlayerScreen",
                "subtitle load OK uri=${loadEventInfo.uri} bytes=${loadEventInfo.bytesLoaded} " +
                    "durMs=${loadEventInfo.loadDurationMs} mime=${mediaLoadData.trackFormat?.sampleMimeType}",
            )
        }

        override fun onLoadCanceled(
            eventTime: AnalyticsListener.EventTime,
            loadEventInfo: LoadEventInfo,
            mediaLoadData: MediaLoadData,
        ) {
            if (!isTextLoad(mediaLoadData)) return
            Log.w("PlayerScreen", "subtitle load CANCEL uri=${loadEventInfo.uri}")
        }

        override fun onLoadError(
            eventTime: AnalyticsListener.EventTime,
            loadEventInfo: LoadEventInfo,
            mediaLoadData: MediaLoadData,
            error: IOException,
            wasCanceled: Boolean,
        ) {
            if (!isTextLoad(mediaLoadData)) return
            val code = (error as? HttpDataSource.InvalidResponseCodeException)?.responseCode
            Log.e(
                "PlayerScreen",
                "subtitle load ERROR uri=${loadEventInfo.uri} http=$code " +
                    "mime=${mediaLoadData.trackFormat?.sampleMimeType} " +
                    "lang=${mediaLoadData.trackFormat?.language} " +
                    "err=${error?.message ?: "-"} canceled=$wasCanceled\n" +
                    (error?.stackTraceToString() ?: ""),
            )
        }
    }
    player.addAnalyticsListener(textLoadListener)
    onDispose {
        saveProgressNow()
        player.removeListener(listener)
        player.removeAnalyticsListener(textLoadListener)
        player.release()
    }
    }

    // ---- Dukungan tombol remote / D-pad (diproses di PlayerActivity) ----
    DisposableEffect(player) {
        PlayerRemoteController.ready = true
        PlayerRemoteController.controlsVisible = { showControls }
        PlayerRemoteController.togglePlay = {
            if (player.isPlaying) player.pause() else player.play()
            showControls = true
        }
        PlayerRemoteController.play = { player.play() }
        PlayerRemoteController.pause = { player.pause() }
        PlayerRemoteController.seekBack = { seekBy(-10_000L); showControls = true }
        PlayerRemoteController.seekForward = { seekBy(10_000L); showControls = true }
        PlayerRemoteController.showControls = { showControls = true }
        onDispose { PlayerRemoteController.reset() }
    }

    // Beri fokus ke tombol putar/jeda saat kontrol tampil agar D-pad bisa menjelajah.
    LaunchedEffect(showControls, mediaReady, inPip, showSpeedMenu, showSubMenu, showSyncMenu, showSleepMenu, showQualityMenu) {
        if (showControls && mediaReady && !inPip &&
            !showSpeedMenu && !showSubMenu && !showSyncMenu && !showSleepMenu && !showQualityMenu
        ) {
            delay(60)
            runCatching { controlFocus.requestFocus() }
        }
    }

    // ---- Simpan progres berkala ----
    LaunchedEffect(player, episodeId) {
        while (true) {
            delay(4_000)
            if (player.isPlaying) {
                lastPosition.longValue = player.currentPosition
                lastDuration.longValue = player.duration.takeIf { it > 0 } ?: 0L
                saveProgressNow()
            }
        }
    }

    // ---- Ticker UI (posisi/durasi saat kontrol tampil) ----
    LaunchedEffect(player) {
        while (true) {
            delay(500)
            if (showControls) {
                if (userSeek == null) {
                    uiPosition = player.currentPosition
                }
                val d = player.duration
                if (d > 0) uiDuration = d
            }
        }
    }

    // ---- Sembunyikan kontrol otomatis ----
    LaunchedEffect(showControls, isPlaying, showSpeedMenu, showSubMenu, showSyncMenu, showSleepMenu, showQualityMenu, showSources, showEpisodes, inPip) {
        if (showControls && isPlaying && !inPip &&
            !showSpeedMenu && !showSubMenu && !showSyncMenu && !showSleepMenu && !showQualityMenu && !showSources && !showEpisodes
        ) {
            delay(3_500)
            showControls = false
            // Sana di sini adalah fokus yang hilang karena semi-otomatis, sehingga
            // pemain tidak kehilangan kontrol D-pad di TV.
            runCatching { controlFocus.requestFocus() }
        }
    }

    // ---- Timer tidur ----
    LaunchedEffect(sleepEndsAt) {
        val end = sleepEndsAt
        if (end > 0) {
            while (System.currentTimeMillis() < end) {
                delay(1_000)
            }
            saveProgressNow()
            player.pause()
            sleepEndsAt = 0
        }
    }

    BackHandler {
        when {
            showSpeedMenu -> showSpeedMenu = false
            showSubMenu -> showSubMenu = false
            showSyncMenu -> showSyncMenu = false
            showSleepMenu -> showSleepMenu = false
            showQualityMenu -> showQualityMenu = false
            showSources -> showSources = false
            showEpisodes -> showEpisodes = false
            showControls && mediaReady -> showControls = false
            else -> {
                saveProgressNow()
                onBack()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PlayerView(ctx).apply {
                    this.player = player
                    useController = false
                    setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER)
                    resizeMode = resizeModes[resizeModeIx]
                    // Subtitle tanpa kotak hitam: latar transparan + bayangan tipis.
                    subtitleView?.setApplyEmbeddedStyles(false)
                    subtitleView?.setApplyEmbeddedFontSizes(false)
                    subtitleView?.setStyle(
                        androidx.media3.ui.CaptionStyleCompat(
                            android.graphics.Color.WHITE,
                            android.graphics.Color.TRANSPARENT,
                            android.graphics.Color.TRANSPARENT,
                            androidx.media3.ui.CaptionStyleCompat.EDGE_TYPE_DROP_SHADOW,
                            android.graphics.Color.BLACK,
                            null,
                        )
                    )
                }
            },
            update = { pv ->
                pv.resizeMode = resizeModes[resizeModeIx]
                // Pastikan style subtitle tetap transparan tiap update Compose.
                pv.subtitleView?.setApplyEmbeddedStyles(false)
                pv.subtitleView?.setApplyEmbeddedFontSizes(false)
                pv.subtitleView?.setStyle(
                    androidx.media3.ui.CaptionStyleCompat(
                        android.graphics.Color.WHITE,
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                        androidx.media3.ui.CaptionStyleCompat.EDGE_TYPE_DROP_SHADOW,
                        android.graphics.Color.BLACK,
                        null,
                    )
                )
            },
        )

        // ---- Lapisan gestur (tap, tap ganda, seret) ----
        if (mediaReady && !inPip) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(player) {
                        coroutineScope {
                            launch {
                                detectTapGestures(
                                    onTap = { toggleControls() },
                                    onDoubleTap = { offset ->
                                        if (offset.x < size.width / 2f) seekBy(-10_000L)
                                        else seekBy(10_000L)
                                    },
                                )
                            }
                            launch {
                                detectDragGestures(
                                    onDragStart = { offset ->
                                        gesture.zone = when {
                                            offset.x < size.width * 0.33f -> GestureZone.BRIGHTNESS
                                            offset.x > size.width * 0.67f -> GestureZone.VOLUME
                                            else -> GestureZone.SEEK
                                        }
                                        gesture.startY = offset.y
                                        gesture.startPos = player.currentPosition
                                        gesture.startBrightness = currentBrightness()
                                        gesture.startVolume =
                                            audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                                        gesture.accX = 0f
                                        gesture.accY = 0f
                                        gesture.lastLiveSeekAt = 0L
                                    },
                                    onDrag = { _, drag ->
                                        when (gesture.zone) {
                                            GestureZone.BRIGHTNESS -> {
                                                gesture.accY += drag.y
                                                val range = size.height * 1.3f
                                                val v = (gesture.startBrightness - gesture.accY / range)
                                                    .coerceIn(0.01f, 1f)
                                                setBrightness(v)
                                                showHud(
                                                    Icons.Filled.BrightnessMedium,
                                                    "${(v * 100).toInt()}%",
                                                )
                                            }
                                            GestureZone.VOLUME -> {
                                                gesture.accY += drag.y
                                                val max = audioManager
                                                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                                                val stepPx = size.height / (max * 1.5f)
                                                val v = (gesture.startVolume - gesture.accY / stepPx)
                                                    .toInt().coerceIn(0, max)
                                                audioManager.setStreamVolume(
                                                    AudioManager.STREAM_MUSIC, v, 0,
                                                )
                                                showHud(Icons.Filled.VolumeUp, "$v/$max")
                                            }
                                            GestureZone.SEEK -> {
                                                gesture.accX += drag.x
                                                val dur = player.duration.takeIf { it > 0 }
                                                    ?: uiDuration
                                                if (dur > 0) {
                                                    val target = gesture.startPos +
                                                        ((gesture.accX / size.width) * dur).toLong()
                                                    val t = target.coerceIn(0L, dur)
                                                    showHud(null, formatTime(t))
                                                    val now = System.currentTimeMillis()
                                                    if (now - gesture.lastLiveSeekAt > 350) {
                                                        gesture.lastLiveSeekAt = now
                                                        player.seekTo(t)
                                                        uiPosition = t
                                                    }
                                                }
                                            }
                                            GestureZone.NONE -> Unit
                                        }
                                    },
                                    onDragEnd = {
                                        if (gesture.zone == GestureZone.SEEK) {
                                            val dur = player.duration.takeIf { it > 0 } ?: uiDuration
                                            if (dur > 0) {
                                                val target = gesture.startPos +
                                                    ((gesture.accX / size.width) * dur).toLong()
                                                seekTo(target)
                                            }
                                        }
                                        gesture.zone = GestureZone.NONE
                                    },
                                    onDragCancel = { gesture.zone = GestureZone.NONE },
                                )
                            }
                        }
                    },
            )
        }

        // ---- Bar atas ----
        if (showControls && mediaReady && !inPip) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(listOf(Color(0xCC000000), Color.Transparent))
                    )
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color(0x66000000))
                        .focusRequester(backFocus)
                        .focusProperties { down = controlFocus }
                        .clickable {
                            saveProgressNow()
                            onBack()
                        }
                        .padding(10.dp)
                        .size(20.dp),
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = request.title,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = listOfNotNull(
                            if (episodeNumber > 0) "Episode $episodeNumber" else "Putar",
                            selectedStream?.label?.takeIf { it.isNotBlank() },
                        ).joinToString(" \u2022 "),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.75f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                if (sleepEndsAt > 0) {
                    val remainMin =
                        ((sleepEndsAt - System.currentTimeMillis()) / 60_000L + 1L).coerceAtLeast(0L)
                    Text(
                        text = "\u23f0 ${remainMin}m",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color(0x66FFFFFF))
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                    )
                }
            }
        }

        // ---- Transport tengah ----
        if (showControls && mediaReady && !inPip) {
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                Icon(
                    Icons.Filled.Replay10,
                    contentDescription = "Mundur 10 detik",
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color(0x33000000))
                        .focusRequester(rewindFocus)
                        .focusProperties {
                            right = controlFocus
                            up = backFocus
                            down = bottomFirstFocus
                        }
                        .clickable { seekBy(-10_000L) }
                        .padding(14.dp)
                        .size(30.dp),
                )
                Icon(
                    if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isPlaying) "Jeda" else "Putar",
                    tint = Color.Black,
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color.White)
                        .focusRequester(controlFocus)
                        .focusProperties {
                            left = rewindFocus
                            right = forwardFocus
                            up = backFocus
                            down = bottomFirstFocus
                        }
                        .clickable {
                            if (player.isPlaying) player.pause() else player.play()
                        }
                        .padding(16.dp)
                        .size(34.dp),
                )
                Icon(
                    Icons.Filled.Forward10,
                    contentDescription = "Maju 10 detik",
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color(0x33000000))
                        .focusRequester(forwardFocus)
                        .focusProperties {
                            left = controlFocus
                            up = backFocus
                            down = bottomFirstFocus
                        }
                        .clickable { seekBy(10_000L) }
                        .padding(14.dp)
                        .size(30.dp),
                )
            }
        }

        // ---- Panel bawah ----
        if (showControls && mediaReady && !inPip) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC000000)))
                    )
                    .padding(horizontal = 16.dp, vertical = 10.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = formatTime(userSeek ?: uiPosition),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                    )
                    Slider(
                        value = (userSeek ?: uiPosition)
                            .toFloat()
                            .coerceIn(0f, (uiDuration.coerceAtLeast(1L)).toFloat()),
                        onValueChange = { userSeek = it.toLong() },
                        onValueChangeFinished = {
                            userSeek?.let { t -> seekTo(t, show = false) }
                            userSeek = null
                        },
                        valueRange = 0f..(uiDuration.coerceAtLeast(1L)).toFloat(),
                        modifier = Modifier.weight(1f).padding(horizontal = 10.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color.White,
                            inactiveTrackColor = Color.White.copy(alpha = 0.25f),
                        ),
                    )
                    Text(
                        text = "-" + formatTime((uiDuration - (userSeek ?: uiPosition)).coerceAtLeast(0L)),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.75f),
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        CtlButton(
                            icon = Icons.Filled.Layers,
                            label = "Sumber",
                            modifier = Modifier
                                .focusRequester(bottomFirstFocus)
                                .focusProperties { up = controlFocus },
                        ) {
                            showSources = true
                        }
                        if (episodes.size > 1) {
                            CtlButton(
                                icon = Icons.AutoMirrored.Filled.PlaylistPlay,
                                label = "Episode",
                            ) { showEpisodes = true }
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        CtlButton(
                            text = formatSpeed(speed),
                            label = "Kecepatan",
                            modifier = Modifier.focusProperties { up = controlFocus },
                        ) {
                            showSpeedMenu = true
                        }
                        CtlButton(
                            icon = Icons.Filled.AspectRatio,
                            label = resizeLabels[resizeModeIx],
                        ) {
                            resizeModeIx = (resizeModeIx + 1) % resizeModes.size
                        }
                        CtlButton(
                            icon = Icons.Filled.Subtitles,
                            label = "Subtitle",
                            tint = if (selectedSubKey != null) MaterialTheme.colorScheme.primary
                            else Color.White,
                        ) {
                            refreshSubOptions()
                            showSubMenu = true
                        }
                        CtlButton(
                            icon = Icons.Filled.Speed,
                            label = if (subOffsetMs == 0L) "Sync" else formatOffset(subOffsetMs),
                            tint = if (subOffsetMs != 0L) MaterialTheme.colorScheme.primary
                            else Color.White,
                        ) {
                            showSyncMenu = true
                        }
                        if (videoQualities.size > 1) {
                            CtlButton(
                                icon = Icons.Filled.HighQuality,
                                label = selectedVideoHeight?.let { "${it}p" } ?: "Auto",
                                tint = if (selectedVideoHeight != null) MaterialTheme.colorScheme.primary
                                else Color.White,
                            ) {
                                refreshVideoQualities()
                                showQualityMenu = true
                            }
                        }
                        val act = activityRef.value
                        if (act != null) {
                            CtlButton(icon = Icons.Filled.ScreenRotation, label = "Rotasi") {
                                toggleOrientation()
                            }
                            CtlButton(icon = Icons.Filled.PictureInPicture, label = "PiP") {
                                act.enterPip()
                            }
                        }
                        CtlButton(
                            icon = Icons.Filled.Timer,
                            label = if (sleepEndsAt > 0) {
                                "${((sleepEndsAt - System.currentTimeMillis()) / 60_000L + 1L).coerceAtLeast(0L)}m"
                            } else {
                                "Timer"
                            },
                            tint = if (sleepEndsAt > 0) MaterialTheme.colorScheme.primary
                            else Color.White,
                        ) { showSleepMenu = true }
                    }
                }
            }
        }

        // ---- HUD gestur ----
        hud?.let { h ->
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xCC000000))
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                h.icon?.let {
                    Icon(it, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                }
                Text(h.text, style = MaterialTheme.typography.titleMedium, color = Color.White)
            }
        }

        // ---- Status memuat / galat ----
        if (loadingStreams && streams.isEmpty()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                CircularProgressIndicator(color = Color.White)
                Text(
                    text = "Mencari sumber terbaik\u2026",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f),
                )
            }
        }

        streamError?.let { message ->
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(
                    Icons.Filled.ErrorOutline,
                    contentDescription = null,
                    tint = Color(0xFFFF6B6B),
                    modifier = Modifier.size(44.dp),
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color.White)
                        .clickable {
                            streamError = null
                            loadingStreams = true
                            streams = emptyList()
                            reload++
                        }
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                    Text("Coba Lagi", style = MaterialTheme.typography.labelLarge, color = Color.Black)
                }
                if (episodes.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color(0x33FFFFFF))
                            .clickable { showEpisodes = true }
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                    ) {
                        Text("Pilih Episode Lain", style = MaterialTheme.typography.labelLarge, color = Color.White)
                    }
                }
            }
        }

        playerError?.let { message ->
            if (streamError == null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 92.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xE6301018))
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                ) {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                    )
                }
            }
        }

        if (buffering && isPlaying.not() && streamError == null && streams.isNotEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
            )
        }
    }

    // ---- Menu kecepatan ----
    if (showSpeedMenu) {
        OptionMenu(
            title = "Kecepatan Pemutaran",
            items = listOf(0.5f, 0.75f, 1f, 1.25f, 1.5f, 1.75f, 2f).map {
                it to formatSpeed(it)
            },
            selected = { it == speed },
            onSelect = { v ->
                speed = v
                player.playbackParameters = PlaybackParameters(v)
                showSpeedMenu = false
            },
            onDismiss = { showSpeedMenu = false },
        )
    }

    // ---- Menu subtitle ----
    if (showSubMenu) {
        val items = listOf<Pair<SubOption?, String>>(null to "Nonaktif") +
            subOptions.map { it to it.label }
        val apiCodes = apiSubtitles.map { normalizeLangCode(it.lang ?: it.label ?: "") }
        val apiHasIndo = apiCodes.contains("id")
        // "Terpasang" = track Indonesia benar-benar muncul di menu pemutar.
        val visibleHasIndo = subOptions.any { normalizeLangCode(it.label) == "id" || it.label == "Indonesia" }
        val diagNote = buildString {
            append("v").append(com.mikdadstream.app.BuildConfig.VERSION_NAME)
            append(" \u2022 API: ").append(if (apiCodes.isEmpty()) "-" else apiCodes.joinToString(","))
            if (!apiHasIndo) {
                // API/konten ini memang tidak punya track Indonesia sama sekali.
                append(" \u2022 Subtitle Indonesia tidak tersedia untuk film ini")
            } else if (!installedHasIndo) {
                // API punya "id" tapi tidak berhasil dibangun jadi track (bug internal).
                append(" \u2022 INDONESIA TAK TERPASANG")
            } else if (!visibleHasIndo) {
                // Track dibangun dan ditandai DEFAULT tapi ExoPlayer tak menampilkannya.
                append(" \u2022 INDONESIA TAK TERPASANG (terpasang, tak terdaftar di pemutar)")
            }
        }
        OptionMenu(
            title = "Subtitle",
            note = if (subOptions.isEmpty()) "Tidak ada subtitle tersedia untuk video ini."
            else diagNote,
            items = items,
            selected = { it?.key == selectedSubKey },
            onSelect = { opt ->
                selectSubtitle(opt)
                showSubMenu = false
            },
            onDismiss = { showSubMenu = false },
        )
    }


    // ---- Menu sinkronisasi subtitle ----
    if (showSyncMenu) {
        val offsets = listOf(
            -10000L to "-10.0 detik",
            -5000L to "-5.0 detik",
            -2000L to "-2.0 detik",
            -1000L to "-1.0 detik",
            -500L to "-0.5 detik",
            0L to "Normal (0.0)",
            500L to "+0.5 detik",
            1000L to "+1.0 detik",
            2000L to "+2.0 detik",
            5000L to "+5.0 detik",
            10000L to "+10.0 detik",
        )
        OptionMenu(
            title = "Sinkronisasi Subtitle",
            note = "Geser waktu subtitle terhadap video. Sekarang: ${formatOffset(subOffsetMs)}",
            items = offsets,
            selected = { it == subOffsetMs },
            onSelect = { value ->
                subOffsetMs = value
                pendingSeek.longValue = player.currentPosition
                subOffsetTrigger++
                showSyncMenu = false
                showHud(Icons.Filled.Speed, "Sync ${formatOffset(value)}")
            },
            onDismiss = { showSyncMenu = false },
        )
    }

    // ---- Menu kualitas video ----
    if (showQualityMenu) {
        val items = listOf<Pair<Int?, String>>(null to "Auto") +
            videoQualities.map { it to "${it}p" }
        OptionMenu(
            title = "Kualitas Video",
            note = if (videoQualities.size <= 1) "Sumber ini hanya menyediakan satu kualitas." else null,
            items = items,
            selected = { it == selectedVideoHeight },
            onSelect = { h ->
                selectVideoHeight(h)
                showQualityMenu = false
            },
            onDismiss = { showQualityMenu = false },
        )
    }

    // ---- Menu timer tidur ----
    if (showSleepMenu) {
        OptionMenu(
            title = "Timer Tidur",
            items = listOf(
                15L to "15 menit", 30L to "30 menit", 45L to "45 menit", 60L to "60 menit",
                0L to if (sleepEndsAt > 0) "Matikan timer" else "Nonaktif",
            ),
            selected = { false },
            onSelect = { minutes ->
                sleepEndsAt = if (minutes > 0) {
                    System.currentTimeMillis() + minutes * 60_000L
                } else {
                    0L
                }
                showSleepMenu = false
            },
            onDismiss = { showSleepMenu = false },
        )
    }

    // ---- Sheet sumber ----
    if (showSources) {
        val sheetFocus = remember { FocusRequester() }
        TvRequestFocusOnShow(sheetFocus)
        ModalBottomSheet(
            onDismissRequest = { showSources = false },
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier
                    .focusRequester(sheetFocus)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "Sumber Pemutaran",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                if (streams.isEmpty()) {
                    Text(
                        text = "Belum ada sumber tersedia.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 12.dp),
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxWidth().height(320.dp)) {
                        items(streams.indices.toList()) { index ->
                            val stream = streams[index]
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(
                                        if (index == selectedIndex) MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                    )
                                    .clickable {
                                        saveProgressNow()
                                        pendingSeek.longValue = player.currentPosition
                                        selectedIndex = index
                                        showSources = false
                                    }
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Icon(
                                    Icons.Filled.Layers,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp),
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = stream.label,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                    Text(
                                        text = stream.kindLabel + if (stream.isDrm == true) " \u2022 DRM" else "",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                                if (index == selectedIndex) {
                                    Icon(
                                        Icons.Filled.CheckCircle,
                                        contentDescription = "Terpilih",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(18.dp),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // ---- Sheet episode ----
    if (showEpisodes) {
        val sheetFocus = remember { FocusRequester() }
        TvRequestFocusOnShow(sheetFocus)
        ModalBottomSheet(
            onDismissRequest = { showEpisodes = false },
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier
                    .focusRequester(sheetFocus)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "Daftar Episode",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                LazyColumn(modifier = Modifier.fillMaxWidth().height(320.dp)) {
                    items(episodes, key = { it.safeId }) { episode ->
                        val active = episode.safeId == episodeId
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    if (active) MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                )
                                .clickable { playEpisode(episode) }
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Text(
                                text = if (episode.safeNumber > 0) "EP ${episode.safeNumber}" else "\u2014",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Text(
                                text = episode.badgeLabel ?: "Putar",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.weight(1f),
                            )
                            if (active) {
                                Icon(
                                    Icons.Filled.CheckCircle,
                                    contentDescription = "Sedang diputar",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CtlButton(
    icon: ImageVector? = null,
    text: String? = null,
    label: String,
    tint: Color = Color.White,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 9.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(19.dp))
        }
        Text(
            text = text ?: label,
            style = MaterialTheme.typography.labelSmall,
            color = tint,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
        )
    }
}

@Composable
private fun <T> OptionMenu(
    title: String,
    items: List<Pair<T, String>>,
    selected: (T) -> Boolean,
    onSelect: (T) -> Unit,
    onDismiss: () -> Unit,
    note: String? = null,
) {
    BackHandler { onDismiss() }
    val firstFocus = remember { FocusRequester() }
    TvRequestFocusOnShow(firstFocus)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(20f)
            .background(Color(0x99000000))
            .pointerInput(Unit) { detectTapGestures { onDismiss() } },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 320.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xF01C1C22))
                .pointerInput(Unit) { detectTapGestures { } }
                .focusRequester(firstFocus)
                .focusGroup()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            note?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }
            items.forEach { (value, label) ->
                val isSelected = selected(value)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                            else Color.Transparent
                        )
                        .clickable { onSelect(value) }
                        .padding(horizontal = 8.dp, vertical = 11.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.White,
                        modifier = Modifier.weight(1f),
                    )
                    if (isSelected) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = "Terpilih",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                }
            }
        }
    }
}
