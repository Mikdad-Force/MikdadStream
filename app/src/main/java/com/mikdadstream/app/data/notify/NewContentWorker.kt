package com.mikdadstream.app.data.notify

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mikdadstream.app.AppGraph
import com.mikdadstream.app.data.moviebox.LocalMovieBoxClient
import kotlinx.coroutines.flow.first

/**
 * Pekerja latar belakang yang mendeteksi **film baru** dan **episode baru**,
 * lalu memunculkan notifikasi.
 *
 * Cara deteksi: membandingkan katalog saat ini dengan daftar ID yang sudah
 * pernah dilihat, disimpan di SharedPreferences. Tanpa server push, ini satu-
 * satunya cara yang dapat diandalkan dan hemat.
 *
 * - Film baru  : judul di katalog yang belum pernah tercatat.
 * - Episode baru: judul di Watchlist yang jumlah episodenya bertambah.
 *
 * Dijalankan berkala oleh WorkManager (lihat [NewContentScheduler]).
 */
class NewContentWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val ctx = applicationContext
        // WorkManager dapat menjalankan worker sebelum Application.onCreate
        // selesai, sehingga AppGraph.prefs (lateinit) belum terisi dan akses
        // langsung akan melempar UninitializedPropertyAccessException.
        // AppGraph.init() idempoten (guard ::prefs.isInitialized), aman dipanggil.
        val readyError = runCatching { AppGraph.init(ctx) }.exceptionOrNull()
        if (readyError != null) {
            android.util.Log.w(TAG, "AppGraph belum siap: ${readyError.javaClass.simpleName}")
            return Result.retry()
        }
        NewContentNotifier.ensureChannels(ctx)
        // Kalau izin belum ada / notifikasi dimatikan, tidak perlu memanggil API.
        if (!NewContentNotifier.canNotify(ctx)) return Result.success()

        val prefs = AppGraph.prefs
        if (!prefs.notifyNewContent.value) return Result.success()

        val sp = ctx.getSharedPreferences(SP, Context.MODE_PRIVATE)
        return try {
            checkNewMovies(ctx, sp)
            checkNewEpisodes(ctx, sp)
            Result.success()
        } catch (t: Throwable) {
            android.util.Log.w(TAG, "gagal cek konten baru: ${t.javaClass.simpleName}")
            Result.retry()
        }
    }

    /** Film baru: bandingkan katalog (trending) dengan yang sudah dilihat. */
    private suspend fun checkNewMovies(ctx: Context, sp: android.content.SharedPreferences) {
        val catalog = runCatching { LocalMovieBoxClient.trendingItems() }
            .getOrDefault(emptyList())
            .filterNot { it.isNews }
        if (catalog.isEmpty()) return

        val known = sp.getStringSet(KEY_SEEN_IDS, emptySet()).orEmpty().toMutableSet()
        // Kunjungan pertama hanya menyimpan daftar acuan: jangan banjiri
        // pengguna dengan notifikasi untuk semua judul yang sudah ada.
        if (known.isEmpty()) {
            sp.edit().putStringSet(KEY_SEEN_IDS, catalog.map { it.safeId }.toSet()).apply()
            android.util.Log.i(TAG, "acuan awal ${catalog.size} judul disimpan")
            return
        }

        val fresh = catalog.filter { it.safeId.isNotBlank() && it.safeId !in known }
        // Batasi jumlah notifikasi per siklus agar tidak mengganggu.
        fresh.take(MAX_NOTIF_PER_CYCLE).forEachIndexed { i, item ->
            NewContentNotifier.show(
                context = ctx,
                channel = NewContentNotifier.CHANNEL_MOVIE,
                id = NOTIF_ID_MOVIE + i,
                title = "Film Baru",
                text = item.safeTitle,
            )
        }
        if (fresh.isNotEmpty()) {
            known.addAll(fresh.map { it.safeId })
            sp.edit().putStringSet(KEY_SEEN_IDS, known).apply()
            android.util.Log.i(TAG, "film baru=${fresh.size}")
        }
    }

    /**
     * Episode baru untuk judul di Watchlist: bandingkan jumlah episode.
     * Hanya judul yang eksplisit disimpan pengguna yang dipantau, agar tidak
     * memanggil detail untuk seluruh katalog.
     */
    private suspend fun checkNewEpisodes(ctx: Context, sp: android.content.SharedPreferences) {
        val watchlist = runCatching {
            AppGraph.database.watchlistDao().observeAll().first()
        }.getOrNull().orEmpty()
        if (watchlist.isEmpty()) return

        for (w in watchlist.take(MAX_WATCHLIST_PER_CYCLE)) {
            val detail = runCatching {
                LocalMovieBoxClient.subjectDetailData(w.contentId)
            }.getOrNull() ?: continue

            val count = detail.episodes?.size ?: 0
            if (count <= 0) continue

            val prevKey = "ep_${w.contentId}"
            val prev = sp.getInt(prevKey, -1)
            // Pertama kali dipantau: simpan acuan tanpa notifikasi.
            if (prev < 0) {
                sp.edit().putInt(prevKey, count).apply()
                continue
            }
            if (count > prev) {
                NewContentNotifier.show(
                    context = ctx,
                    channel = NewContentNotifier.CHANNEL_EPISODE,
                    // ID berbasis hash judul: notifikasi baru menimpa yang lama.
                    id = NOTIF_ID_EPISODE + (w.contentId.hashCode() and 0xFFFF),
                    title = "Episode Baru",
                    text = "${w.title} — episode ${count}",
                )
                sp.edit().putInt(prevKey, count).apply()
                android.util.Log.i(TAG, "episode baru ${w.title}: $prev -> $count")
            }
        }
    }

    private companion object {
        const val TAG = "NewContentWorker"
        const val SP = "new_content_notify"
        const val KEY_SEEN_IDS = "seen_subject_ids"
        const val MAX_NOTIF_PER_CYCLE = 3
        const val MAX_WATCHLIST_PER_CYCLE = 12
        const val NOTIF_ID_MOVIE = 4100
        const val NOTIF_ID_EPISODE = 4300
    }
}
