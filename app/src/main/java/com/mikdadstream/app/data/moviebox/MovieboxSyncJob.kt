package com.mikdadstream.app.data.moviebox

import android.content.Context
import com.mikdadstream.app.AppGraph
import com.mikdadstream.app.data.remote.MovieboxSubmitBody
import com.mikdadstream.app.data.remote.MovieboxUpdate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Sinkronisasi katalog Moviebox: perangkat asli menarik info subject
 * langsung dari Moviebox (server-IP diblokir), lalu mengirim patch ke
 * worker untuk diteruskan ke katalog semua pengguna.
 */
object MovieboxSyncJob {

    private const val TAG = "MovieboxSync"
    private const val SP = "moviebox_sync"
    private const val INTERVAL_MS = 10 * 60 * 1000L
    private const val PLAN_CAP = 40
    private const val DISCOVER_CAP = 4

    fun maybeRun(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                runOnce(context.applicationContext)
            } catch (t: Throwable) {
                android.util.Log.w(TAG, "sync error: ${t.javaClass.simpleName}: ${t.message}")
            }
        }
    }

    suspend fun runOnce(context: Context): Boolean = withContext(Dispatchers.IO) {
        val sp = context.getSharedPreferences(SP, Context.MODE_PRIVATE)
        val now = System.currentTimeMillis()
        if (now - sp.getLong("last_run", 0) < INTERVAL_MS) return@withContext false

        val updates = ArrayList<MovieboxUpdate>()
        try {
            val api = AppGraph.apiClient.api

            // Plan — perbarui judul/episodes yang sudah ada di katalog.
            // Kursor berputar (wrap) karena plan terus bertambah.
            run {
                val cursor = sp.getInt("plan_cursor", 0)
                val plan = runCatching { api.movieboxPlan(cursor) }.getOrNull()?.data
                val items = plan?.items.orEmpty()
                var processed = 0
                for (item in items) {
                    if (processed >= PLAN_CAP) break
                    val info = MovieboxResolver.subjectInfo(item.subjectId) ?: continue
                    updates.add(info.copy(vid = item.vid, isNew = false))
                    processed++
                }
                val next = plan?.cursorNext
                sp.edit().putInt("plan_cursor", next ?: 0).apply()
            }

            // Discover — judul baru dari katalog Moviebox (H5).
            val discover = MovieboxResolver.h5Discover()
            var dcount = 0
            for (d in discover) {
                if (dcount >= DISCOVER_CAP) break
                val info = MovieboxResolver.subjectInfo(d.subjectId) ?: continue
                updates.add(
                    info.copy(
                        vid = MovieboxResolver.slugFromDetailPath(d.detailPath, d.subjectId),
                        detailPath = d.detailPath,
                        isNew = true,
                    ),
                )
                dcount++
            }

            if (updates.isNotEmpty()) {
                runCatching { api.movieboxSubmit(MovieboxSubmitBody(updates)) }
            }
            sp.edit().putLong("last_run", now).apply()
            android.util.Log.i(TAG, "sync kirim=${updates.size} discover=${discover.size}")
            true
        } catch (t: Throwable) {
            android.util.Log.w(TAG, "sync gagal: ${t.javaClass.simpleName}: ${t.message}")
            false
        }
    }
}
