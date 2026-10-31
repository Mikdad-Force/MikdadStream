package com.mikdadstream.app.ui.player

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Mengambil gambar pratinjau pada waktu tertentu untuk seek bar — meniru
 * pratinjau scrub YouTube.
 *
 * Kendala nyata: sumber MovieBox berupa HLS/DASH, sedangkan
 * [MediaMetadataRetriever] paling andal pada file progresif. Karena itu:
 *  - retriever diberi header (User-Agent/Referer) yang sama dengan pemutar,
 *    sebab tanpa itu CDN sering menolak;
 *  - kegagalan dicatat dan TIDAK dicoba ulang terus-menerus (mahal);
 *  - pemanggil (lihat `ScrubPreview`) memakai hasil ini sebagai pelengkap,
 *    bukan satu-satunya sumber gambar.
 *
 * Semua kerja berat di [Dispatchers.IO]; memanggil retriever di main thread
 * akan membuat video tersendat.
 */
class ThumbnailLoader(
    private val url: String,
    private val headers: Map<String, String> = emptyMap(),
) {

    private var retriever: MediaMetadataRetriever? = null
    private var failed = false

    /** Apakah pratinjau gambar masih mungkin untuk stream ini. */
    val available: Boolean get() = !failed

    /** Ambil frame pada [timeMs]. Null bila tidak didukung / gagal. */
    suspend fun frameAt(timeMs: Long, maxWidthPx: Int = 360): Bitmap? =
        withContext(Dispatchers.IO) {
            if (failed) return@withContext null
            val r = retriever ?: openRetriever() ?: run {
                failed = true
                return@withContext null
            }.also { retriever = it }

            runCatching {
                val bmp = r.getFrameAtTime(
                    timeMs.coerceAtLeast(0L) * 1000L,
                    MediaMetadataRetriever.OPTION_CLOSEST_SYNC,
                ) ?: return@withContext null
                scaleDown(bmp, maxWidthPx)
            }.getOrElse { null }
        }

    private fun openRetriever(): MediaMetadataRetriever? = runCatching {
        MediaMetadataRetriever().apply { setDataSource(url, headers) }
    }.getOrNull()

    private fun scaleDown(bmp: Bitmap, maxWidthPx: Int): Bitmap {
        if (bmp.width <= maxWidthPx) return bmp
        val ratio = maxWidthPx.toFloat() / bmp.width
        val scaled = Bitmap.createScaledBitmap(
            bmp,
            maxWidthPx,
            (bmp.height * ratio).toInt().coerceAtLeast(1),
            true,
        )
        if (scaled != bmp) bmp.recycle()
        return scaled
    }

    fun release() {
        runCatching { retriever?.release() }
        retriever = null
    }
}
