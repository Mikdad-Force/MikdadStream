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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.PlaylistPlay
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.media3.common.C
import androidx.media3.common.MediaItem as ExoMediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.ParserException
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
import com.mikdadstream.app.ui.tv.isTelevision
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.math.roundToInt

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
    // true saat kolom teks (cari episode) aktif -> tombol remote tidak boleh
    // dialihkan ke pemutar.
    var textInputActive: Boolean = false

    fun reset() {
        ready = false
        controlsVisible = { false }
        togglePlay = {}
        play = {}
        pause = {}
        seekBack = {}
        seekForward = {}
        showControls = {}
        textInputActive = false
    }
}

private fun resolveStreamUrl(url: String?): String? {
    val u = url?.takeIf { it.isNotBlank() } ?: return null
    if (u.startsWith("http://") || u.startsWith("https://")) return u
    if (u.startsWith("/")) return BuildConfig.API_BASE_URL.trimEnd('/') + u
    return u
}

/**
 * Jenis konten stream: "hls", "dash", atau null (biarkan ExoPlayer menebak).
 *
 * ExoPlayer 1.4.1 hanya memilih parser HLS bila URI berakhiran `.m3u8` ATAU
 * MediaItem membawa MIME `application/x-mpegURL`. Banyak CDN memberi URL tanpa
 * ekstensi (mis. `/playlist?token=...`) sehingga pemutar jatuh ke parser
 * progressive dan gagal membaca playlist. Field `format` dari API dipakai
 * sebagai penentu cadangan agar sumber HLS selalu dikenali.
 */
private fun inferStreamKind(url: String, format: String?): String? {
    val u = url.lowercase()
    // Ekstensi dicek pada PATH saja (bukan query) agar parameter ber-".mpd"/
    // ".m3u8" di URL tidak salah menentukan parser.
    val path = u.substringBefore('?')
    if (path.contains(".m3u8")) return "hls"
    if (path.contains(".mpd")) return "dash"
    return when (format?.lowercase()?.trim()) {
        "hls", "m3u8" -> "hls"
        "dash" -> "dash"
        else -> null
    }
}

/** MIME untuk jenis stream. null = biarkan ExoPlayer menebak dari URI. */
private fun streamMimeType(kind: String?): String? = when (kind) {
    "hls" -> MimeTypes.APPLICATION_M3U8
    "dash" -> MimeTypes.APPLICATION_MPD
    // Bukan manifest: memaksa ExoPlayer memakai parser progressive
    // tanpa peduli ekstensi URI.
    "progressive" -> MimeTypes.VIDEO_MP4
    else -> null
}

/** Jenis berikutnya saat parser gagal membaca isi stream (percobaan ulang sekali). */
private fun alternateStreamKind(current: String?): String =
    if (current == "hls") "progressive" else "hls"

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
/** Deteksi apakah teks/kode merujuk ke bahasa Melayu/Malaysia. */
fun isMalay(text: String?): Boolean {
    if (text.isNullOrBlank()) return false
    val s = text.trim().lowercase(java.util.Locale.ROOT)
    // "Malayalam" (bahasa Dravida, kode "ml") BUKAN Melayu meski berawalan "malay".
    if (s.contains("malayalam") || s.contains("malayali")) return false
    val clean = s.replace('-', '_').replace('.', '_')
    val base = clean.substringBefore('_')
    if (base in setOf("ms", "may", "msa", "zlm")) return true
    return s.contains("melayu") || s.contains("malay") || s.contains("malaysia")
}

fun normalizeLangCode(code: String?): String {
    val raw = (code ?: "").trim().lowercase(java.util.Locale.ROOT)
    if (raw.isEmpty()) return ""
    // Melayu BUKAN Indonesia, jangan pernah disamakan walau mengandung kata "bahasa".
    if (isMalay(raw)) return "ms"
    if (raw.contains("indonesi") || (raw.contains("bahasa") && !raw.contains("melayu") && !raw.contains("malay"))) return "id"
    val base = raw.replace('.', '_').replace('-', '_')
        .substringBefore('_').substringBefore('-')
    return when (base) {
        "id", "in", "ind" -> "id"
        "ms", "may", "msa", "zlm" -> "ms"
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
    // Tampilkan 2 desimal bila offset bukan kelipatan 0,5 detik agar nilai
    // seperti 0,25 / 0,75 detik tidak tampil membulat menyesatkan.
    val text = if (ms % 500L == 0L) {
        String.format(java.util.Locale.US, "%.1f", seconds)
    } else {
        String.format(java.util.Locale.US, "%.2f", seconds)
    }
    return if (ms > 0) "+${text}s" else "${text}s"
}

private fun md5Key(text: String): String =
    java.security.MessageDigest.getInstance("MD5")
        .digest(text.toByteArray())
        .joinToString("") { "%02x".format(it) }
        .take(24)

/**
 * Konversi teks SRT mentah menjadi WebVTT.
 *
 * Timestamp SRT (`HH:MM:SS,mmm`) diubah ke WebVTT (`HH:MM:SS.mmm`) dengan jam
 * selalu 2 digit. Format tanpa jam (`MM:SS,mmm`) juga diterima. Milidetik 1-3
 * digit dinormalisasi ke 3 digit agar parser Media3 tidak menolak cue.
 */
private fun srtTextToVtt(srt: String): String =
    "WEBVTT\n\n" + convertSrtTimings(srt)

/**
 * Ubah timestamp gaya SRT ("HH:MM:SS,mmm" / "MM:SS,mmm") menjadi gaya WebVTT
 * ("HH:MM:SS.mmm") TANPA menambah header.
 *
 * Hanya baris timing cue (mengandung "-->") yang disentuh. Baris dialog tidak
 * boleh diubah: angka seperti "12:30,500" di dalam teks dulu ikut dikonversi
 * dan merusak isi subtitle.
 */
private fun convertSrtTimings(srt: String): String {
    val clean = srt.removePrefix("\uFEFF").replace("\r\n", "\n").replace('\r', '\n')
    val ts = Regex("(?:(\\d{1,4}):)?(\\d{1,2}):(\\d{2})[,.](\\d{1,3})")
    return clean.lineSequence().joinToString("\n") { line ->
        if (!line.contains("-->")) {
            line
        } else {
            ts.replace(line) { m ->
                val h = m.groupValues[1].toIntOrNull() ?: 0
                val min = m.groupValues[2].toIntOrNull() ?: 0
                val sec = m.groupValues[3].toIntOrNull() ?: 0
                val millis = m.groupValues[4].padEnd(3, '0').take(3)
                // Locale.US WAJIB: pada perangkat berbahasa Arab/Persia,
                // "%02d" menghasilkan angka non-ASCII dan cue jadi tak terbaca.
                String.format(java.util.Locale.US, "%02d:%02d:%02d.%s", h, min, sec, millis)
            }
        }
    }
}

/**
 * Pastikan teks berupa WebVTT yang valid; null jika isi bukan SRT/VTT.
 *
 * Berkas ber-header "WEBVTT" tetapi bertimestamp gaya SRT (koma) tetap
 * diperbaiki: parser WebVTT Media3 menolak cue berkoma, dan itulah salah satu
 * penyebab subtitle hilang tanpa pesan.
 */
private fun normalizeSubtitleText(text: String): String? {
    val clean = text.removePrefix("\uFEFF").replace("\r\n", "\n").replace('\r', '\n')
    val trimmed = clean.trimStart()
    // Koma pada baris timing = gaya SRT (WebVTT hanya menerima titik).
    val srtStyleTiming = Regex("(?:\\d{1,4}:)?\\d{1,2}:\\d{2},\\d{1,3}\\s*-->").containsMatchIn(clean)
    if (trimmed.startsWith("WEBVTT", true)) {
        // Header sudah ada: perbaiki hanya baris timing, header tetap utuh.
        return if (srtStyleTiming) convertSrtTimings(clean) else clean
    }
    if (clean.contains("-->")) {
        // Ada koma pada timestamp = gaya SRT (WebVTT hanya menerima titik),
        // termasuk format tanpa jam "MM:SS,mmm" -> konversi penuh.
        return if (srtStyleTiming) srtTextToVtt(clean) else "WEBVTT\n\n" + trimmed
    }
    if (Regex("\\d{1,2}:\\d{2}:\\d{2}[,.]\\d{1,3}").containsMatchIn(clean)) return srtTextToVtt(clean)
    return null
}

private fun parseTimestampToMs(raw: String): Long {
    val clean = raw.trim().replace(',', '.')
    val parts = clean.split(':')
    return when (parts.size) {
        3 -> {
            val h = parts[0].toLongOrNull() ?: 0L
            val m = parts[1].toLongOrNull() ?: 0L
            val secParts = parts[2].split('.')
            val s = secParts.getOrNull(0)?.toLongOrNull() ?: 0L
            val ms = (secParts.getOrNull(1)?.padEnd(3, '0')?.take(3)?.toLongOrNull() ?: 0L)
            (h * 3600 + m * 60 + s) * 1000 + ms
        }
        2 -> {
            val m = parts[0].toLongOrNull() ?: 0L
            val secParts = parts[1].split('.')
            val s = secParts.getOrNull(0)?.toLongOrNull() ?: 0L
            val ms = (secParts.getOrNull(1)?.padEnd(3, '0')?.take(3)?.toLongOrNull() ?: 0L)
            (m * 60 + s) * 1000 + ms
        }
        else -> 0L
    }
}

private fun formatMsToVttTimestamp(ms: Long): String {
    val total = ms.coerceAtLeast(0L)
    val hh = total / 3_600_000
    val mm = (total % 3_600_000) / 60_000
    val ss = (total % 60_000) / 1000
    val millis = total % 1000
    // Locale.US: cegah digit non-ASCII pada perangkat ber-locale Arab/Persia.
    return String.format(java.util.Locale.US, "%02d:%02d:%02d.%03d", hh, mm, ss, millis)
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

/**
 * Geser waktu subtitle WebVTT sebesar offset (ms). Timestamp negatif -> 0.
 *
 * Hanya baris timing cue ("start --> end") yang digeser — teks dialog TIDAK
 * disentuh. Pola global sebelumnya ikut mengubah angka di dalam dialog
 * (mis. "12:30,500" pada subtitle telepon) sehingga isi teks ikut rusak.
 * Bila berkas punya header `X-TIMESTAMP-MAP` (subtitle HLS), MPEGTS yang
 * digeser karena itulah acuan waktu yang dipakai pemutar. Blok yang format
 * timing-nya tidak dikenali dibiarkan apa adanya agar tidak pernah rusak.
 */
private fun shiftVttTimestamps(content: String, offsetMs: Long): String {
    if (offsetMs == 0L) return content
    val normalized = content.replace("\r\n", "\n").replace('\r', '\n')
    // Subtitle HLS membawa header "X-TIMESTAMP-MAP=LOCAL:..,MPEGTS:<90kHz>".
    // Untuk berkas seperti ini, menggeser MPEGTS (90 kHz) adalah cara yang
    // benar: waktu cue tetap utuh dan tidak ada cue yang perlu dijepit ke 0.
    val tsMap = Regex("(X-TIMESTAMP-MAP=[^\\n]*?MPEGTS:)(-?\\d+)", RegexOption.IGNORE_CASE)
    if (tsMap.containsMatchIn(normalized)) {
        return tsMap.replace(normalized) { m ->
            val mpegts = m.groupValues[2].toLongOrNull() ?: 0L
            "${m.groupValues[1]}${(mpegts + offsetMs * 90L).coerceAtLeast(0L)}"
        }
    }
    val timing = Regex(
        "^[ \\t]*((?:\\d{1,4}:)?\\d{1,2}:\\d{2}[,.]\\d{1,3})[ \\t]*-->[ \\t]*" +
            "((?:\\d{1,4}:)?\\d{1,2}:\\d{2}[,.]\\d{1,3})(.*)$",
    )
    // Proses per BLOK cue (dipisah baris kosong) supaya header "WEBVTT",
    // "X-TIMESTAMP-MAP", blok STYLE/NOTE, dan teks dialog tidak tersentuh.
    val out = ArrayList<String>()
    for (block in normalized.split(Regex("\n[ \\t]*\n"))) {
        val lines = block.split('\n').toMutableList()
        val timingIdx = lines.indexOfFirst { it.contains("-->") }
        if (timingIdx < 0) {
            out.add(block)
            continue
        }
        val m = timing.find(lines[timingIdx])
        if (m == null) {
            // Format timing tidak dikenali -> biarkan apa adanya, jangan rusak.
            out.add(block)
            continue
        }
        val startMs = parseTimestampToMs(m.groupValues[1])
        val endMs = parseTimestampToMs(m.groupValues[2])
        val dur = (endMs - startMs).coerceAtLeast(400L)
        val newStartMs = startMs + offsetMs
        val newEndMs = newStartMs + dur
        // Cue yang seluruh waktunya jatuh sebelum 0 dibuang. Kalau dipaksa ke
        // 00:00, semua cue awal menumpuk dan berkedip beruntun di detik pertama.
        if (newEndMs <= 0L) continue
        val clampedStart = newStartMs.coerceAtLeast(0L)
        val clampedEnd = newEndMs.coerceAtLeast(clampedStart + 100L)
        lines[timingIdx] =
            "${formatMsToVttTimestamp(clampedStart)} --> " +
                "${formatMsToVttTimestamp(clampedEnd)}${m.groupValues[3]}"
        out.add(lines.joinToString("\n"))
    }
    return out.joinToString("\n\n")
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
    val raw = (code ?: "").trim().lowercase(java.util.Locale.ROOT)
    if (raw.isEmpty()) return null
    if (isMalay(raw)) return "Melayu"
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
    val focusManager = LocalFocusManager.current
    val audioManager = remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    val scope = rememberCoroutineScope()

    // Episode aktif & posisi disimpan lewat rememberSaveable: kalau activity
    // dibuat ulang (mis. sistem kills proses atau konfigurasi berubah) pemutar
    // lanjut di episode yang sama, bukan balik ke episode awal.
    var episodeId by rememberSaveable { mutableStateOf(request.episodeId) }
    var episodeNumber by rememberSaveable { mutableIntStateOf(request.episodeNumber) }
    var episodes by remember { mutableStateOf(request.episodes) }
    // Kolom cari episode ("ketik episode").
    var episodeQuery by rememberSaveable { mutableStateOf("") }
    var searchFocused by remember { mutableStateOf(false) }
    // Posisi terakhir yang diketahui — dipakai sebagai titik lanjut saat
    // activity dibuat ulang.
    var resumePositionMs by rememberSaveable { mutableLongStateOf(0L) }

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
    // ---- Scrub dengan bubble pratinjau ----
    // scrubbing: pengguna sedang menyeret thumb; sisanya posisi (px) milik
    // layout untuk menempatkan bubble tepat di atas thumb dan di atas panel.
    var scrubbing by remember { mutableStateOf(false) }
    var sliderRootX by remember { mutableFloatStateOf(0f) }
    var sliderRootY by remember { mutableFloatStateOf(0f) }
    var sliderW by remember { mutableIntStateOf(0) }
    var rootHeight by remember { mutableIntStateOf(0) }
    var rootTopY by remember { mutableFloatStateOf(0f) }
    var rootLeftX by remember { mutableFloatStateOf(0f) }
    var bubbleW by remember { mutableIntStateOf(0) }
    var bubbleH by remember { mutableIntStateOf(0) }
    var speed by remember { mutableFloatStateOf(1f) }
    var showSpeedMenu by remember { mutableStateOf(false) }
    var showSubMenu by remember { mutableStateOf(false) }
    var showSleepMenu by remember { mutableStateOf(false) }
    var resizeModeIx by remember { mutableIntStateOf(0) }
    var subOptions by remember { mutableStateOf<List<SubOption>>(emptyList()) }
    var selectedSubKey by remember { mutableStateOf<String?>(null) }
    var subtitleUserChosen by remember { mutableStateOf(false) }
    // Bahasa subtitle yang DIKUNCI pengguna. null = semua track dipasang,
    // "" = nonaktif, kode lain = HANYA track itu yang dipasang (tidak mungkin
    // jatuh ke bahasa lain seperti Melayu saat memilih Indonesia).
    // Subtitle otomatis mengikuti Lokasi Negara di Pengaturan: Indonesia -> "id"
    // sehingga subtitle Indonesia langsung tampil tanpa memilih manual. Ini
    // berbeda dari "auto-terkunci": pengguna tetap bebas mengganti/mematikan
    // lewat menu Subtitle (subtitleUserChosen).
    val autoSubLang = AppGraph.prefs.autoSubtitleLang
        .collectAsState().value
    var forcedSubLang by remember(autoSubLang) {
        mutableStateOf<String?>(autoSubLang.takeIf { it.isNotBlank() })
    }
    // Ganti Lokasi Negara = preferensi subtitle baru: lupakan pilihan manual
    // sebelumnya agar subtitle bahasa negara baru bisa dipasang otomatis.
    LaunchedEffect(autoSubLang) {
        subtitleUserChosen = false
    }
    // Sinkronisasi subtitle manual: offset waktu subtitle terhadap video.
    var hasStartedOnce by remember { mutableStateOf(false) }
    var subOffsetMs by rememberSaveable { mutableLongStateOf(0L) }
    var subOffsetTrigger by remember { mutableIntStateOf(0) }
    var showSyncMenu by remember { mutableStateOf(false) }
    var showQualityMenu by remember { mutableStateOf(false) }
    var videoQualities by remember { mutableStateOf<List<Int>>(emptyList()) }
    var selectedVideoHeight by remember { mutableStateOf<Int?>(null) }
    // ---- Jenis konten stream (HLS/DASH) + percobaan ulang otomatis ----
    // URL & format stream yang sedang diputar (dipakai listener onPlayerError).
    var activeStreamUrl by remember { mutableStateOf<String?>(null) }
    var activeStreamFormat by remember { mutableStateOf<String?>(null) }
    // Pemaksaan jenis konten per URL akibat kegagalan parser ("hls"/"dash"/
    // "progressive"). Kosong = masih memakai tebakan otomatis.
    var contentKindRetry by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    // Penanda agar LaunchedEffect pemasangan stream berjalan ulang setelah retry.
    var contentRetryTrigger by remember { mutableIntStateOf(0) }
    // ---- Muat sumber / terapkan stream ----
    // Dipaksa naik tiap kali sumber harus dimuat ulang, sehingga efek pemuatan
    // tetap jalan walau episodeId tidak berubah (sebelumnya klik episode yang
    // sama membuat spinner "Mencari sumber" menggantung tanpa batas).
    var streamLoadTrigger by remember { mutableIntStateOf(0) }
    // Episode yang terakhir benar-benar dipasang ke ExoPlayer: dipakai untuk
    // mempertahankan posisi saat apply ulang (subtitle/sumber menyusul).
    var appliedEpisodeId by remember { mutableStateOf<String?>(null) }
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
                    // Playlist & segmen HLS sering diarahkan lintas protokol
                    // (http -> https) ke CDN berbeda; tanpa ini beban gagal 3xx.
                    .setAllowCrossProtocolRedirects(true)
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
    // Lanjut dari posisi terakhir bila activity dibuat ulang.
    LaunchedEffect(Unit) {
        val resume = resumePositionMs
        if (resume > pendingSeek.longValue) pendingSeek.longValue = resume
    }
    var reload by remember { mutableIntStateOf(0) }
    val lastPosition = remember { mutableLongStateOf(0L) }
    val lastDuration = remember { mutableLongStateOf(0L) }

    val selectedStream = streams.getOrNull(selectedIndex)
    val mediaReady = streamError == null && streams.isNotEmpty() && !loadingStreams

    fun saveProgressNow() {
        resumePositionMs = lastPosition.longValue
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
        // Klik episode yang sedang diputar: jangan bersihkan sumber (efek muat
        // tidak jalan karena episodeId tidak berubah -> spinner menggantung).
        // Cukup tutup panel.
        if (target.safeId == episodeId) {
            showEpisodes = false
            searchFocused = false
            PlayerRemoteController.textInputActive = false
            focusManager.clearFocus()
            return
        }
        saveProgressNow()
        pendingSeek.longValue = 0L
        resumePositionMs = 0L
        userSeek = null
        scrubbing = false
        subOffsetMs = 0L
        episodeId = target.safeId
        episodeNumber = target.safeNumber
        streams = emptyList()
        playerError = null
        showEpisodes = false
        searchFocused = false
        PlayerRemoteController.textInputActive = false
        focusManager.clearFocus()
        loadingStreams = true
        streamLoadTrigger++
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
    /** Pilih track teks dengan kode bahasa tertentu dari currentTracks. */
    fun applyLanguage(code: String): Boolean {
        player.currentTracks.groups.forEach { g ->
            if (g.type != C.TRACK_TYPE_TEXT) return@forEach
            for (i in 0 until g.length) {
                val f = g.getTrackFormat(i)
                val trackCode = normalizeLangCode(f.language)
                // Buang Melayu dari KODE bahasa saja (label tidak akurat).
                if (trackCode == "ms") continue
                // Cocokkan lewat kode ternormalisasi ATAU nama bahasa. Sumber kode
                // tidak seragam ("id"/"in"/"ind"/"Indonesian"), jadi perbandingan
                // harus melalui normalisasi dua sisi agar tidak gagal palsu.
                val labelCode = subtitleLangName(f.label)
                    ?.let { normalizeLangCode(it) }.orEmpty()
                // labelCode kosong tidak boleh cocok dengan code kosong.
                val match = trackCode == code ||
                    (labelCode.isNotEmpty() && labelCode == code) ||
                    (code == "id" && subtitleLangName(f.label) == "Indonesia")
                if (match) {
                    val bp = player.trackSelectionParameters.buildUpon()
                        .clearOverridesOfType(C.TRACK_TYPE_TEXT)
                        .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, false)
                        .setOverrideForType(TrackSelectionOverride(g.mediaTrackGroup, i))
                    player.trackSelectionParameters = bp.build()
                    selectedSubKey = "${g.mediaTrackGroup.id}#$i"
                    Log.i(
                        "PlayerScreen",
                        "Pilih subtitle $code: lang=${f.language} label=${f.label} idx=$i",
                    )
                    com.mikdadstream.app.data.local.SubtitleLog.add(
                        "BERHASIL pilih '$code' -> track lang=${f.language} label=${f.label}",
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
                // Buang Melayu dari KODE bahasa saja; label API tidak akurat
                // (pernah melabeli teks Indonesia sebagai "Malay").
                val code = normalizeLangCode(f.language)
                if (code == "ms" || isMalay(code)) continue
                if (!seenCodes.add(code)) continue
                val langName = subtitleLangName(f.language) ?: subtitleLangName(f.label)
                val label = if (code == "id") "Indonesia"
                else langName
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
            val code = normalizeLangCode(s.lang)
            if (code.isBlank() || code == "ms" || isMalay(code) || code in seenCodes) return@forEach
            seenCodes.add(code)
            val label = if (code == "id") "Indonesia"
            else subtitleLangName(code) ?: s.label?.takeIf { it.isNotBlank() } ?: code
            opts.add(SubOption(null, -1, "api:$code", label))
        }
        subOptions = opts.sortedBy { if (it.label == "Indonesia") 0 else 1 }
        selectedSubKey = selectedKey
        // ---- Auto-subtitle dari Lokasi Negara ----
        // Bila pengguna belum memilih manual, pasang subtitle sesuai lokasi
        // negara (Indonesia -> Indonesia). Dipanggil juga saat track baru
        // terdaftar, karena subtitle sering muncul belakangan. Jika belum ada
        // track yang cocok, jangan matikan tipe TEXT: biarkan track terpasang
        // begitu muncul lalu diterapkan oleh LaunchedEffect pemasangan.
        val wanted = forcedSubLang
        if (!subtitleUserChosen) {
            if (!wanted.isNullOrBlank()) {
                applyLanguage(wanted)
            } else if (selectedSubKey == null) {
                player.trackSelectionParameters = player.trackSelectionParameters.buildUpon()
                    .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, true)
                    .build()
            }
        }
    }

    fun selectSubtitle(opt: SubOption?) {
        // Pilihan manual pengguna: jangan ditimpa auto-select.
        subtitleUserChosen = true
        val code = when {
            opt == null -> ""
            // Track yang sudah terpasang: ambil bahasa dari Format-nya, BUKAN
            // dari key ("sub_id#0" -> dulu salah jadi "sub" sehingga subtitle hilang).
            opt.group != null -> normalizeLangCode(
                opt.group.getFormat(opt.index).language ?: opt.label,
            )
            else -> normalizeLangCode(opt.key.removePrefix("api:").ifBlank { opt.label })
        }
        if (isMalay(code) || code == "ms") return

        // Ganti subtitle TANPA memuat ulang video: cukup ubah TrackSelectionParameters.
        // Dulu ini memicu reload penuh (stop + prepare) sehingga video berhenti/jeda.
        if (code.isEmpty()) {
            player.trackSelectionParameters = player.trackSelectionParameters.buildUpon()
                .clearOverridesOfType(C.TRACK_TYPE_TEXT)
                .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, true)
                .build()
            selectedSubKey = null
            forcedSubLang = ""
            com.mikdadstream.app.data.local.SubtitleLog.add("Pengguna memilih: Nonaktif")
            showHud(Icons.Filled.Subtitles, "Subtitle nonaktif")
            return
        }

        com.mikdadstream.app.data.local.SubtitleLog.add("Pengguna memilih: $code (${opt?.label ?: "?"})")

        if (applyLanguage(code)) {
            forcedSubLang = code
            showHud(Icons.Filled.Subtitles, "Subtitle ${opt?.label ?: code}")
            return
        }

        // Track tidak tersedia saat ini. Simpan pilihan lalu minta LaunchedEffect
        // menerapkannya (ia menunggu track muncul). Ini bukan klaim "gagal":
        // pesan netral dan pemasangan tetap dicoba.
        if (code == forcedSubLang && applyLanguage(code)) return
        forcedSubLang = code
        pendingSeek.longValue = player.currentPosition
        subOffsetTrigger++
        showHud(Icons.Filled.Subtitles, "Memuat subtitle ${opt?.label ?: code}")
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
        if (show) {
            showHud(null, "${formatTime(t)}  \u2022  sisa ${formatTime(dur - t)}")
        }
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
    LaunchedEffect(episodeId, reload, streamLoadTrigger) {
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
        // Jangan pernah pakai subtitle Melayu. Saring HANYA dari kode bahasa:
        // label dari API tidak bisa dipercaya (track berisi teks Indonesia bisa
        // dilabeli "Malay"), dan menyaring label membuat subtitle Indonesia
        // hilang sehingga Indonesia "gagal dimuat" saat dipilih.
        fun cleanSubs(list: List<SubtitleTrack>): List<SubtitleTrack> =
            list.filterNot { normalizeLangCode(it.lang) == "ms" }

        fun indoOf(s: SubtitleTrack): Boolean =
            normalizeLangCode(s.lang) == "id" || subtitleLangName(s.label) == "Indonesia"

        val prefetchedClean = cleanSubs(request.subtitles)
        val prefetchedHasIndo = prefetchedClean.any(::indoOf)
        Log.i(
            "PlayerScreen",
            "masuk player: episode=$episodeId prefetchStreams=${request.streams.size} " +
                "prefetchSubs=${prefetchedClean.size} prefetchIndo=$prefetchedHasIndo",
        )
        if (reload == 0 && episodeId == request.episodeId && request.streams.isNotEmpty()) {
            val list = request.streams
            // Pasang sumber + pilih stream SEGERA: video mulai tanpa menunggu
            // subtitle. Subtitle menyusul belakangan (apply ulang otomatis).
            streams = list
            selectedIndex = pickStreamIndex(list)
            loadingStreams = false
            // Prefetch sudah memuat Indonesia (per-episode sejak v2.1.32) ->
            // pakai langsung, JANGAN fetch ulang. Fetch ulang dulu membuat
            // video mulai lalu di-reload lagi saat daftar baru tiba.
            if (prefetchedHasIndo) {
                apiSubtitles = prefetchedClean
                Log.i("PlayerScreen", "subtitle prefetch dipakai (${prefetchedClean.size})")
                return@LaunchedEffect
            }
            // Hanya Moviebox Local yang contentId-nya id Moviebox; sumber lain
            // memanggilnya hanya membuang waktu pemutaran.
            if (!request.modelId.equals("moviebox-local", true)) {
                apiSubtitles = prefetchedClean
                return@LaunchedEffect
            }
            // Kosongkan dulu: subtitle episode SEBELUMNYA jangan sempat terpasang
            // di video baru selagi subtitle segar diambil (apply ulang otomatis).
            apiSubtitles = emptyList()
            val parts = episodeId.split("_")
            val se = parts.getOrNull(0)?.toIntOrNull() ?: 1
            val ep = parts.getOrNull(1)?.toIntOrNull() ?: 1
            val fresh = cleanSubs(
                runCatching {
                    com.mikdadstream.app.data.moviebox.LocalMovieBoxClient
                        .fetchSubtitles(request.contentId, se, ep)
                }.getOrDefault(emptyList()),
            )
            apiSubtitles = when {
                fresh.any(::indoOf) -> fresh
                fresh.isNotEmpty() -> fresh
                else -> prefetchedClean
            }
            Log.i(
                "PlayerScreen",
                "subtitle segar: ${apiSubtitles.map { (it.lang ?: "") + "/" + (it.label ?: "") }}",
            )
            return@LaunchedEffect
        }
        try {
            val data = withTimeoutOrNull(30_000L) {
                AppGraph.repository.source(request.modelId, episodeId, request.contentId)
            } ?: run {
                loadingStreams = false
                streamError = "Waktu habis memuat sumber. Coba lagi."
                return@LaunchedEffect
            }
            val list = data.playableStreams
            // Sumber dipasang SEBELUM fetch subtitle cadangan: video langsung
            // berputar, subtitle menyusul tanpa menunda pemutaran.
            streams = list
            selectedIndex = pickStreamIndex(list)
            loadingStreams = false
            apiSubtitles = cleanSubs(data.subtitles.orEmpty())
            Log.i("PlayerScreen", "subtitle source: ${apiSubtitles.map { (it.lang ?: "") + "/" + (it.label ?: "") }}")
            if (list.isEmpty()) {
                streamError = "Tidak ada sumber pemutaran untuk episode ini."
                return@LaunchedEffect
            }
            // Bila daftar tidak memuat Indonesia, coba sekali lagi langsung ke API
            // (hanya Moviebox Local; sumber lain tidak punya id Moviebox).
            if (request.modelId.equals("moviebox-local", true) && apiSubtitles.none(::indoOf)) {
                val parts = episodeId.split("_")
                val se = parts.getOrNull(0)?.toIntOrNull() ?: 1
                val ep = parts.getOrNull(1)?.toIntOrNull() ?: 1
                val retry = cleanSubs(
                    runCatching {
                        com.mikdadstream.app.data.moviebox.LocalMovieBoxClient
                            .fetchSubtitles(request.contentId, se, ep)
                    }.getOrDefault(emptyList()),
                )
                Log.i("PlayerScreen", "subtitle retry: ${retry.size} track, indo=${retry.any(::indoOf)}")
                if (retry.isNotEmpty()) apiSubtitles = retry
            }
        } catch (e: ApiError) {
            loadingStreams = false
            streamError = e.message
        } catch (e: Exception) {
            loadingStreams = false
            streamError = e.message ?: "Gagal memuat sumber video."
        }
    }

    // Prefetch sumber episode SEBELUM & SESUDAH yang sedang diputar di latar
    // belakang: saat panel episode dibuka / pengguna menekan "Episode
    // berikutnya", sumber sudah ada di cache repository -> pemutaran instan.
    LaunchedEffect(episodeId, loadingStreams, episodes) {
        if (loadingStreams || streams.isEmpty()) return@LaunchedEffect
        if (!request.modelId.equals("moviebox-local", true)) return@LaunchedEffect
        val idx = episodes.indexOfFirst { it.safeId == episodeId }
        if (idx < 0) return@LaunchedEffect
        // Urut dari yang paling mungkin diklik: episode berikutnya dulu.
        val targets = listOf(idx + 1, idx + 2, idx + 3, idx - 1)
            .mapNotNull { episodes.getOrNull(it) }
        if (targets.isEmpty()) return@LaunchedEffect
        delay(300)
        for (target in targets) {
            runCatching {
                AppGraph.repository.source(request.modelId, target.safeId, request.contentId)
            }
            Log.i("PlayerScreen", "prefetch episode: ${target.safeId}")
            delay(120)
        }
    }

    // ---- Terapkan stream terpilih ke ExoPlayer (pemutar native) ----
    LaunchedEffect(
        streams,
        selectedIndex,
        episodeId,
        apiSubtitles,
        subOffsetTrigger,
        contentRetryTrigger,
        streamLoadTrigger,
    ) {
        val stream = streams.getOrNull(selectedIndex) ?: return@LaunchedEffect
        val url = resolveStreamUrl(stream.safeUrl) ?: return@LaunchedEffect
        // Episode yang sama di-apply ulang (subtitle/sumber/sub sinkron menyusul
        // setelah video berjalan): lanjut dari posisi sekarang, jangan dari awal.
        val resumeNow = if (hasStartedOnce && appliedEpisodeId == episodeId) {
            player.currentPosition.takeIf { it > 0L }
        } else null
        appliedEpisodeId = episodeId
        playerError = null
        activeStreamUrl = url
        activeStreamFormat = stream.format
        // Cegah playback ganda: hentikan media sebelumnya sebelum memuat yang baru.
        // Simpan niat putar/jeda agar reload (ganti sumber/kualitas) tidak
        // memaksa video berputar sendiri saat pengguna sedang menjeda.
        val wasPlayWhenReady = if (hasStartedOnce) player.playWhenReady else true
        // Muat ulang di tempat tanpa memicu jeda yang terlihat: prepare() akan
        // melanjutkan playback sendiri karena playWhenReady dipertahankan.
        player.stop()
        player.clearMediaItems()
        streamHeaders.set(stream.headers.orEmpty())
        // Pasang MIME tipe konten eksplisit: HLS/DASH tetap dikenali meski URL
        // tidak berakhiran .m3u8/.mpd (lihat inferStreamKind).
        val kind = contentKindRetry[url] ?: inferStreamKind(url, stream.format)
        Log.i("PlayerScreen", "pasang sumber: kind=${kind ?: "auto"} format=${stream.format} url=$url")
        val builder = ExoMediaItem.Builder().setUri(url).also { b ->
            streamMimeType(kind)?.let { b.setMimeType(it) }
        }

        // ---- DIAGNOSTIK: cetak daftar kode API, track diterima, normalisasi, cocokan "id" ----
        val apiCodes = apiSubtitles.map { normalizeLangCode(it.lang ?: it.label ?: "") }
        Log.d("PlayerScreen", "subtitle API codes: $apiCodes")
        Log.d("PlayerScreen", "subtitle received: ${apiSubtitles.map { (it.lang ?: "") + "|" + (it.label ?: "") }}")
        com.mikdadstream.app.data.local.SubtitleLog.add(
            "API kirim ${apiSubtitles.size} track: " +
                apiSubtitles.joinToString(", ") { "${it.lang ?: "?"}/${it.label ?: "?"}" },
        )

        // Terapkan offset sinkronisasi: bila pengguna menggeser waktu subtitle,
        // setiap file subtitle digeser lalu dipakai versi lokalnya.
        // Hapus subtitle Melayu HANYA berdasarkan kode bahasa (label tidak akurat).
        val cleanApiSubs = apiSubtitles.filterNot { normalizeLangCode(it.lang) == "ms" }
        val sourceSubs = if (subOffsetMs == 0L) {
            cleanApiSubs
        } else {
            val out = ArrayList<SubtitleTrack>(cleanApiSubs.size)
            for (s in cleanApiSubs) {
                val u = s.url
                out.add(
                    if (u.isNullOrBlank()) s
                    else s.copy(url = shiftedSubtitleUrl(u, subOffsetMs)),
                )
            }
            out
        }
        Log.i("PlayerScreen", "subtitle offset=${subOffsetMs}ms tracks=${sourceSubs.size}")

        // SEMUA track dipasang sebagai SubtitleConfiguration. Bahasa yang dipilih
        // pengguna diterapkan lewat TrackSelectionOverride (applyLanguage), BUKAN
        // dengan menyaring daftar ini. Menyaring di sini pernah membuat track
        // Indonesia hilang total ketika kode bahasa dari API ("in"/"ind"/
        // "Indonesian") tidak persis sama dengan kode pilihan, sehingga muncul
        // pesan "gagal dimuat".
        val activeSubs = if (forcedSubLang == "") emptyList() else sourceSubs
        Log.i("PlayerScreen", "subtitle forced=${forcedSubLang ?: "all"} installed=${activeSubs.size}/${sourceSubs.size}")

        val seenLang = LinkedHashSet<String>()
        val played = activeSubs.mapNotNull { s ->
            val u = s.url?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
            // Saring dari kode bahasa saja: label API tidak bisa dipercaya dan
            // pernah membuat subtitle Indonesia (berlabel "Malay") hilang.
            val lang = normalizeLangCode(s.lang)
            if (lang == "ms" || isMalay(lang)) return@mapNotNull null
            // MIME dipilih dari ISI file lokal (sniff WEBVTT/SRT) bila tersedia,
            // bukan hanya dari ekstensi URL.
            val mime = detectSubtitleMime(u)
            // Dedup: satu track per kode hasil normalisasi (setId jadi unik per track).
            if (!seenLang.add(lang)) return@mapNotNull null
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
                // Tanpa SELECTION_FLAG_DEFAULT: subtitle tidak boleh terpilih
                // otomatis. Pengguna memilih sendiri dari menu.
                .setSelectionFlags(0)
                .build()
        }
        // Track Melayu sudah dibuang di atas; Indonesia diletakkan paling depan
        // agar menjadi kandidat default pertama saat prepare().
        val subs = played.sortedByDescending { it.first == "id" }.map { it.second }
        installedHasIndo = played.any { it.first == "id" }
        Log.d("PlayerScreen", "subtitle configs: ${subs.joinToString { (it.language ?: "") + "/" + (it.label ?: "") }}")
        Log.d("PlayerScreen", "id match: api=${apiCodes.contains("id")} installed=$installedHasIndo visible=${subs.any { it.label == "Indonesia" }}")
        com.mikdadstream.app.data.local.SubtitleLog.add(
            "Terpasang ${subs.size} track: " +
                (subs.joinToString(", ") { "${it.language}/${it.label}" }.ifEmpty { "-" }) +
                " | Indonesia dari API=${apiCodes.contains("id")}",
        )
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
        // Pilihan pengguna (termasuk "Nonaktif" -> forcedSubLang == "") harus
        // dipertahankan agar tidak ditimpa auto-pilih setelah reload.
        val userPicked = forcedSubLang != null
        subtitleUserChosen = userPicked
        val wantSubs = !forcedSubLang.isNullOrEmpty()
        selectedSubKey = null
        // Tanpa preferensi bahasa teks: ExoPlayer TIDAK memilih subtitle sendiri.
        // Tipe TEXT dinonaktifkan sampai pengguna memilih dari menu.
        val newParams = player.trackSelectionParameters.buildUpon()
            .clearOverridesOfType(C.TRACK_TYPE_TEXT)
            .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
            .setSelectUndeterminedTextLanguage(false)
            .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, !wantSubs)
            .build()
        player.trackSelectionParameters = newParams
        Log.i(
            "PlayerScreen",
            "track params: preferredText=${newParams.preferredTextLanguages} " +
                "undetermined=${newParams.selectUndeterminedTextLanguage} " +
                "textDisabled=${newParams.disabledTrackTypes.contains(C.TRACK_TYPE_TEXT)}",
        )
        player.setMediaItem(builder.build())
        val seek = resumeNow ?: pendingSeek.longValue
        if (seek > 0L) player.seekTo(seek)
        player.prepare()
        player.playWhenReady = wasPlayWhenReady
        hasStartedOnce = true
        // Kualitas video pilihan pengguna dipertahankan setelah reload.
        selectedVideoHeight?.let { h -> selectVideoHeight(h) }
        showControls = true
        // Track subtitle baru tersedia beberapa saat setelah prepare; jangan
        // hanya bergantung pada onTracksChanged (bisa terlewat).
        val wanted = forcedSubLang
        if (wanted == "") {
            player.trackSelectionParameters = player.trackSelectionParameters.buildUpon()
                .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, true)
                .build()
        } else if (wanted == null) {
            // Pengguna belum memilih: biarkan subtitle nonaktif dan TANPA HUD.
            // Tidak ada auto-pilih Indonesia dan tidak ada pesan "tidak tersedia".
            player.trackSelectionParameters = player.trackSelectionParameters.buildUpon()
                .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, true)
                .build()
            Log.i("PlayerScreen", "subtitle belum dipilih pengguna (${apiSubtitles.size} track API)")
        } else {
            repeat(24) {
                if (applyLanguage(wanted)) return@LaunchedEffect
                delay(250)
            }
            Log.w("PlayerScreen", "Subtitle $wanted belum tersedia di track pemutar")
            com.mikdadstream.app.data.local.SubtitleLog.add(
                "GAGAL pasang '$wanted': tidak ada track cocok dari ${subs.size} track terpasang",
            )
            // Untuk auto-subtitle (dari Lokasi Negara) tidak perlu mengganggu
            // pengguna dengan HUD: subtitle otomatis bersifat best-effort.
            if (subtitleUserChosen) {
                showHud(Icons.Filled.Subtitles, "Subtitle belum tersedia, coba lagi")
            }
        }
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
            // ---- Percobaan ulang jenis konten (HLS <-> progressive) ----
            // Bila parser tidak mengenali isi stream, atau server mengirim tipe
            // konten yang tidak terduga, kemungkinan besar URL ini playlist HLS
            // tanpa ekstensi .m3u8. Ganti jenis konten SEKALI per URL (anti-loop)
            // sebelum menampilkan error ke pengguna.
            val url = activeStreamUrl
            var cause: Throwable? = error
            var depth = 0
            var parseFailed = false
            while (cause != null && depth < 6) {
                if (cause is ParserException) {
                    parseFailed = true
                    break
                }
                cause = cause.cause
                depth++
            }
            val badContentType =
                error.errorCode == PlaybackException.ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE
            if (url != null && url !in contentKindRetry && (parseFailed || badContentType)) {
                val next = alternateStreamKind(inferStreamKind(url, activeStreamFormat))
                contentKindRetry = contentKindRetry + (url to next)
                contentRetryTrigger++
                Log.w(
                    "PlayerScreen",
                    "gagal membaca stream (${error.errorCodeName}) -> coba ulang sebagai $next: $url",
                )
                showHud(null, "Mencoba mode ${next.uppercase()}…")
                return
            }
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
            // Pulihkan kualitas video pilihan setelah reload (ganti subtitle/offset).
            val h = selectedVideoHeight
            if (h != null) {
                val alreadySelected = tracks.groups.any { g ->
                    g.type == C.TRACK_TYPE_VIDEO &&
                        (0 until g.length).any { i ->
                            g.getTrackFormat(i).height == h && g.isTrackSelected(i)
                        }
                }
                if (!alreadySelected) selectVideoHeight(h)
            }
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
    LaunchedEffect(showControls, isPlaying, scrubbing, showSpeedMenu, showSubMenu, showSyncMenu, showSleepMenu, showQualityMenu, showSources, showEpisodes, inPip) {
        if (showControls && isPlaying && !inPip && !scrubbing &&
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

    // Keyboard ditutup (kolom cari episode ditinggalkan) -> bar sistem
    // disembunyikan lagi supaya pemutar tetap fullscreen.
    LaunchedEffect(searchFocused) {
        if (!searchFocused) activityRef.value?.hideSystemBars()
    }

    // Masuk Picture-in-Picture: tutup panel & menu supaya tidak ada overlay
    // yang menggantung di jendela PiP.
    LaunchedEffect(inPip) {
        if (inPip) {
            showSources = false
            showEpisodes = false
            showSpeedMenu = false
            showSubMenu = false
            showSyncMenu = false
            showQualityMenu = false
            showSleepMenu = false
            searchFocused = false
            scrubbing = false
            PlayerRemoteController.textInputActive = false
            focusManager.clearFocus()
        }
    }

    // Android TV: BACK tidak boleh langsung menutup pemutar (mencegah pemutar
    // "keluar sendiri" balik ke layar film karena tekanan BACK tercecer di
    // remote/firmware). Tekan pertama menampilkan kontrol; tekan kedua dalam
    // 2,5 detik keluar. Ponsel tetap satu tekan seperti sebelumnya.
    val isTv = remember { context.isTelevision() }
    var lastGuardBack by remember { mutableLongStateOf(0L) }
    BackHandler {
        when {
            showSpeedMenu -> showSpeedMenu = false
            showSubMenu -> showSubMenu = false
            showSyncMenu -> showSyncMenu = false
            showSleepMenu -> showSleepMenu = false
            showQualityMenu -> showQualityMenu = false
            // Kolom cari episode aktif: tombol kembali dulu menutup keyboard,
            // baru berikutnya menutup panel — pemutar tidak ikut tertutup.
            searchFocused -> {
                searchFocused = false
                PlayerRemoteController.textInputActive = false
                focusManager.clearFocus()
            }
            showSources -> showSources = false
            showEpisodes -> showEpisodes = false
            showControls && mediaReady && !isTv -> showControls = false
            else -> {
                val now = System.currentTimeMillis()
                if (isTv && (lastGuardBack == 0L || now - lastGuardBack > 2_500)) {
                    lastGuardBack = now
                    showControls = true
                    runCatching { controlFocus.requestFocus() }
                } else {
                    lastGuardBack = 0L
                    saveProgressNow()
                    onBack()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .onSizeChanged { rootHeight = it.height }
            .onGloballyPositioned {
                val p = it.positionInRoot()
                rootTopY = p.y
                rootLeftX = p.x
            },
    ) {
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
                                                    showHud(
                                                        null,
                                                        "${formatTime(t)}  \u2022  sisa ${formatTime(dur - t)}",
                                                    )
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

        // ---- Bar atas saat sumber belum siap (jangan layar hitam kosong) ----
        if (!mediaReady && !inPip) {
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
                        .clickable { onBack() }
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
                        text = when {
                            loadingStreams -> "Menyiapkan pemutar\u2026"
                            streamError != null -> "Sumber gagal dimuat"
                            else -> "Menyiapkan pemutar\u2026"
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.75f),
                        maxLines = 1,
                    )
                }
                if (loadingStreams) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }

        // ---- Bar atas ----
        if (showControls && mediaReady && !inPip) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(listOf(Color(0xE6000000), Color.Transparent))
                    )
                    .padding(horizontal = 14.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0x33FFFFFF))
                        .focusRequester(backFocus)
                        .focusProperties { down = controlFocus }
                        .clickable {
                            saveProgressNow()
                            onBack()
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp),
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = request.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
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
                        color = Color.White.copy(alpha = 0.72f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                if (episodeNumber > 0) {
                    Text(
                        text = "EP $episodeNumber",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
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
                            .background(Color(0x33FFFFFF))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                    )
                }
            }
        }

        // ---- Transport tengah ----
        if (showControls && mediaReady && !inPip) {
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp),
            ) {
                Icon(
                    Icons.Filled.Replay10,
                    contentDescription = "Mundur 10 detik",
                    tint = Color.White,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Color(0x40FFFFFF))
                        .focusRequester(rewindFocus)
                        .focusProperties {
                            right = controlFocus
                            up = backFocus
                            down = bottomFirstFocus
                        }
                        .clickable { seekBy(-10_000L) }
                        .padding(14.dp),
                )
                Icon(
                    if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (isPlaying) "Jeda" else "Putar",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(66.dp)
                        .clip(CircleShape)
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
                        .padding(18.dp),
                )
                Icon(
                    Icons.Filled.Forward10,
                    contentDescription = "Maju 10 detik",
                    tint = Color.White,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Color(0x40FFFFFF))
                        .focusRequester(forwardFocus)
                        .focusProperties {
                            left = controlFocus
                            up = backFocus
                            down = bottomFirstFocus
                        }
                        .clickable { seekBy(10_000L) }
                        .padding(14.dp),
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
                        Brush.verticalGradient(listOf(Color.Transparent, Color(0xD9000000)))
                    )
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xB31A1D24))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = formatTime(userSeek ?: uiPosition),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                        )
                        Slider(
                            value = (userSeek ?: uiPosition)
                                .toFloat()
                                .coerceIn(0f, (uiDuration.coerceAtLeast(1L)).toFloat()),
                            onValueChange = { v ->
                                userSeek = v.toLong()
                                // Slider di material3 versi ini tidak punya
                                // onValueChangeStarted -> mulai scrub saat nilai
                                // pertama berubah (seret/tap).
                                if (!scrubbing) {
                                    scrubbing = true
                                    showControls = true
                                }
                            },
                            onValueChangeFinished = {
                                userSeek?.let { t -> seekTo(t, show = false) }
                                userSeek = null
                                scrubbing = false
                            },
                            valueRange = 0f..(uiDuration.coerceAtLeast(1L)).toFloat(),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                                .onSizeChanged { sliderW = it.width }
                                .onGloballyPositioned { c ->
                                    val p = c.positionInRoot()
                                    sliderRootX = p.x
                                    sliderRootY = p.y
                                },
                            colors = SliderDefaults.colors(
                                thumbColor = Color.White,
                                activeTrackColor = Color.White,
                                inactiveTrackColor = Color.White.copy(alpha = 0.22f),
                            ),
                        )
                        Text(
                            text = "-" + formatTime((uiDuration - (userSeek ?: uiPosition)).coerceAtLeast(0L)),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White.copy(alpha = 0.7f),
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
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
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
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
        }

        // ---- Bubble pratinjau saat scrub (seret thumb seek bar) ----
        // Menempel tepat di atas thumb: waktu target + sisa durasi, mengikuti
        // posisi jempol selama pengguna menyeret scrubber.
        if (scrubbing && !inPip && uiDuration > 0L) {
            val scrubPos = userSeek ?: uiPosition
            val frac = scrubPos.toFloat().coerceIn(0f, uiDuration.toFloat()) / uiDuration.toFloat()
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .onSizeChanged {
                        bubbleW = it.width
                        bubbleH = it.height
                    }
                    .offset {
                        val bw = if (bubbleW > 0) bubbleW.toFloat() else 96.dp.toPx()
                        val bh = if (bubbleH > 0) bubbleH.toFloat() else 52.dp.toPx()
                        val pad = 14.dp.toPx()
                        val span = (sliderW - 2 * pad).coerceAtLeast(0f)
                        val thumbX = sliderRootX + pad + frac * span
                        val maxX = (sliderRootX + sliderW - bw).coerceAtLeast(0f)
                        IntOffset(
                            x = (thumbX - rootLeftX - bw / 2f).coerceIn(0f, maxX).roundToInt(),
                            y = -(rootHeight - (sliderRootY - rootTopY) + 16.dp.toPx() + bh)
                                .roundToInt(),
                        )
                    }
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xF0101014))
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = formatTime(scrubPos),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                )
                Text(
                    text = "sisa " + formatTime((uiDuration - scrubPos).coerceAtLeast(0L)),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.65f),
                )
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

        // ---- Status memuat ----
        // Hanya spinner kecil ala Moviebox: tanpa teks "Mencari sumber".
        // Sumber dipasang seketika bila sudah di-prefetch dari layar detail;
        // bila belum, URL dicari secepat mungkin di latar belakang.
        if (loadingStreams && streams.isEmpty()) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.5.dp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(34.dp),
            )
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
                            // Lanjutkan dari posisi terakhir, bukan dari awal.
                            pendingSeek.longValue = player.currentPosition
                                .takeIf { it > 0L } ?: pendingSeek.longValue
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
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 92.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xE6301018))
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                    )
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color.White)
                            .clickable {
                                playerError = null
                                streamError = null
                                loadingStreams = true
                                pendingSeek.longValue = player.currentPosition
                                    .takeIf { it > 0L } ?: pendingSeek.longValue
                                streams = emptyList()
                                reload++
                            }
                            .padding(horizontal = 16.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Icon(
                            Icons.Filled.Refresh,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(15.dp),
                        )
                        Text("Muat Ulang Sumber", style = MaterialTheme.typography.labelLarge, color = Color.Black)
                    }
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
        // Catatan status ("tidak ada subtitle") sengaja TIDAK ditampilkan:
        // pengguna cukup memilih dari daftar bahasa yang tersedia.
        OptionMenu(
            title = "Subtitle",
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
            -7500L to "-7.5 detik",
            -5000L to "-5.0 detik",
            -4000L to "-4.0 detik",
            -3000L to "-3.0 detik",
            -2500L to "-2.5 detik",
            -2000L to "-2.0 detik",
            -1500L to "-1.5 detik",
            -1000L to "-1.0 detik",
            -750L to "-0.75 detik",
            -500L to "-0.5 detik",
            -250L to "-0.25 detik",
            0L to "Normal (0.0 detik)",
            250L to "+0.25 detik",
            500L to "+0.5 detik",
            750L to "+0.75 detik",
            1000L to "+1.0 detik",
            1500L to "+1.5 detik",
            2000L to "+2.0 detik",
            2500L to "+2.5 detik",
            3000L to "+3.0 detik",
            4000L to "+4.0 detik",
            5000L to "+5.0 detik",
            7500L to "+7.5 detik",
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

    // ---- Panel sumber (dalam jendela pemutar) ----
    if (showSources) {
        val panelFocus = remember { FocusRequester() }
        TvRequestFocusOnShow(panelFocus)
        PlayerPanel(onDismiss = { showSources = false }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Sumber Pemutaran",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                    Text(
                        text = if (streams.isEmpty()) "Belum ada sumber tersedia."
                        else "${streams.size} sumber ditemukan",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.6f),
                    )
                }
                IconButton(
                    modifier = Modifier.focusRequester(panelFocus),
                    onClick = { showSources = false },
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "Tutup", tint = Color.White)
                }
            }
            if (streams.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Belum ada sumber tersedia.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f),
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(streams.indices.toList()) { index ->
                        val stream = streams[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    if (index == selectedIndex) MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                                    else Color(0xFF23262E)
                                )
                                .clickable {
                                    saveProgressNow()
                                    pendingSeek.longValue = player.currentPosition
                                    selectedIndex = index
                                    showSources = false
                                }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
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
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Text(
                                    text = stream.kindLabel + if (stream.isDrm == true) " \u2022 DRM" else "",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White.copy(alpha = 0.6f),
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

    // ---- Panel episode + kolom cari ("ketik episode") ----
    if (showEpisodes) {
        val panelFocus = remember { FocusRequester() }
        TvRequestFocusOnShow(panelFocus)
        val filtered = remember(episodes, episodeQuery) { filterEpisodes(episodes, episodeQuery) }
        PlayerPanel(onDismiss = {
            showEpisodes = false
            searchFocused = false
            PlayerRemoteController.textInputActive = false
            focusManager.clearFocus()
        }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Daftar Episode",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                    Text(
                        text = if (episodeQuery.isBlank()) {
                            "${episodes.size} episode tersedia"
                        } else {
                            "${filtered.size} dari ${episodes.size} episode"
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.6f),
                    )
                }
                IconButton(
                    modifier = Modifier.focusRequester(panelFocus),
                    onClick = {
                        showEpisodes = false
                        searchFocused = false
                        PlayerRemoteController.textInputActive = false
                        focusManager.clearFocus()
                    },
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "Tutup", tint = Color.White)
                }
            }

            OutlinedTextField(
                value = episodeQuery,
                onValueChange = { episodeQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focus ->
                        searchFocused = focus.isFocused
                        PlayerRemoteController.textInputActive = focus.isFocused
                    },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                placeholder = {
                    Text(
                        text = "Ketik nomor atau judul episode",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.45f),
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp),
                    )
                },
                trailingIcon = {
                    if (episodeQuery.isNotEmpty()) {
                        IconButton(onClick = { episodeQuery = "" }) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Hapus pencarian",
                                tint = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.size(16.dp),
                            )
                        }
                    }
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                // Enter tidak menutup panel/keyboard supaya pengguna bisa
                // menyunting kata kunci lalu memilih episode.
                keyboardActions = KeyboardActions(onSearch = {}),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2A2E37),
                    unfocusedContainerColor = Color(0xFF1F222A),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = MaterialTheme.colorScheme.primary,
                ),
            )

            Text(
                text = "Ketik nomor episode untuk lompat cepat, atau cari judulnya.",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.5f),
            )

            if (filtered.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Episode tidak ditemukan",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f),
                    )
                }
            } else {                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    itemsIndexed(filtered, key = { index, _ -> "episode-$index" }) { _, episode ->
                        val active = episode.safeId == episodeId
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    if (active) MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                                    else Color(0xFF23262E)
                                )
                                .clickable { playEpisode(episode) }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (active) MaterialTheme.colorScheme.primary
                                        else Color(0xFF2E323B)
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = if (episode.safeNumber > 0) "${episode.safeNumber}" else "\u2014",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (active) Color.White else Color.White.copy(alpha = 0.85f),
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = episode.badgeLabel ?: "Episode ${episode.safeNumber}",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Text(
                                    text = listOfNotNull(
                                        episode.durationSeconds?.takeIf { it > 0 }?.let { "${it / 60} menit" },
                                        episode.lockedReason?.takeIf { it.isNotBlank() },
                                    ).joinToString(" \u2022 ").ifBlank { "Ketuk untuk memutar" },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White.copy(alpha = 0.6f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
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

/**
 * Saring daftar episode berdasarkan kata kunci.
 * Angka -> cari nomor episode (persis atau sebagian), teks -> cari judul.
 */
private fun filterEpisodes(list: List<EpisodeData>, query: String): List<EpisodeData> {
    val q = query.trim()
    if (q.isEmpty()) return list
    val number = q.toIntOrNull()
    if (number != null) {
        // Nomor persis selalu tampil paling atas biar bisa langsung diputar.
        return list
            .filter { it.safeNumber == number || it.safeNumber.toString().contains(q) }
            .sortedBy { if (it.safeNumber == number) 0 else 1 }
    }
    return list.filter { episode ->
        episode.badgeLabel?.contains(q, ignoreCase = true) == true ||
            episode.safeId.contains(q, ignoreCase = true)
    }
}

/**
 * Panel overlay di dalam jendela pemutar (bukan bottom sheet/dialog terpisah).
 * Dipakai untuk daftar episode & sumber: keyboard saat mengetik episode tetap
 * di jendela yang sama, jadi pemutar tidak ikut tertutup.
 */
@Composable
private fun PlayerPanel(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val panelTop = remember { mutableIntStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(Color(0xB3000000))
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    // Ketukan di dalam panel tidak menutup panel.
                    if (offset.y < panelTop.intValue) onDismiss()
                }
            },
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.82f)
                .onGloballyPositioned { panelTop.intValue = it.positionInRoot().y.toInt() }
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xF216181F))
                .focusGroup()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = content,
        )
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
                // Menu bisa berisi banyak pilihan (mis. 25 langkah sinkronisasi
                // subtitle): batasi tinggi dan beri gulir agar tidak melebihi
                // layar dan tidak memotong pilihan di luar jangkauan.
                .heightIn(max = 420.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xF01C1C22))
                .pointerInput(Unit) { detectTapGestures { } }
                .focusRequester(firstFocus)
                .focusGroup()
                .padding(horizontal = 18.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
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
