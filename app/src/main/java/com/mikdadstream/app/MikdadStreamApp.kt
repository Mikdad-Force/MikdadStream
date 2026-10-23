package com.mikdadstream.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mikdadstream.app.data.download.DownloadManager
import com.mikdadstream.app.data.firebase.UserRepository
import com.mikdadstream.app.data.local.AppDatabase
import com.mikdadstream.app.data.local.PrefsStore
import com.mikdadstream.app.data.remote.ApiClient
import com.mikdadstream.app.data.repository.StreamRepository
import com.mikdadstream.app.data.update.AppUpdater

/** Kontainer dependensi sederhana (tanpa Hilt agar ringan). */
object AppGraph {
    lateinit var prefs: PrefsStore
        private set
    lateinit var database: AppDatabase
        private set
    lateinit var apiClient: ApiClient
        private set
    lateinit var repository: StreamRepository
        private set
    lateinit var downloads: DownloadManager
        private set
    lateinit var users: UserRepository
        private set
    lateinit var updater: AppUpdater
        private set

    var appContext: Context? = null
        private set

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `downloads` (" +
                    "`key` TEXT NOT NULL, " +
                    "`content_id` TEXT NOT NULL, " +
                    "`model_id` TEXT NOT NULL, " +
                    "`title` TEXT NOT NULL, " +
                    "`cover` TEXT, " +
                    "`episode_id` TEXT, " +
                    "`episode_number` INTEGER, " +
                    "`quality` TEXT, " +
                    "`format` TEXT, " +
                    "`url` TEXT NOT NULL, " +
                    "`headers_json` TEXT, " +
                    "`file_path` TEXT, " +
                    "`bytes_total` INTEGER NOT NULL, " +
                    "`bytes_done` INTEGER NOT NULL, " +
                    "`status` TEXT NOT NULL, " +
                    "`progress` REAL NOT NULL, " +
                    "`created_at` INTEGER NOT NULL, " +
                    "PRIMARY KEY(`key`))"
            )
        }
    }

    fun init(context: Context) {
        if (::prefs.isInitialized) return
        val app = context.applicationContext
        appContext = app
        prefs = PrefsStore(app)
        database = Room.databaseBuilder(app, AppDatabase::class.java, "mikdad_stream.db")
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
        apiClient = ApiClient(prefs)
        repository = StreamRepository(
            api = apiClient.api,
            watchlistDao = database.watchlistDao(),
            historyDao = database.historyDao(),
            searchHistoryDao = database.searchHistoryDao(),
        )
        downloads = DownloadManager(app, database.downloadDao())
        users = UserRepository()
        users.start()
        updater = AppUpdater(app)
    }
}

class MikdadStreamApp : Application(), coil.ImageLoaderFactory {
    /** Coil: fade-in halus saat poster selesai dimuat (tidak "pop" mendadak). */
    override fun newImageLoader(): coil.ImageLoader =
        coil.ImageLoader.Builder(this)
            .crossfade(250)
            .respectCacheHeaders(false)
            .build()

    override fun onCreate() {
        super.onCreate()
        installCrashBreadcrumb()
        AppGraph.init(this)
        com.mikdadstream.app.data.moviebox.MovieboxSyncJob.maybeRun(this)
    }

    /**
     * Simpan jejak crash terakhir ke files/diag/crash-last.txt. Bila pemain
     * "keluar sendiri" (mis. di Android TV), file ini membuat penyebab
     * crash dapat dilaporkan tanpa perlu adb.
     */
    private fun installCrashBreadcrumb() {
        val prev = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, error ->
            runCatching {
                val dir = java.io.File(filesDir, "diag")
                dir.mkdirs()
                val stamp = java.text.SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss",
                    java.util.Locale.US,
                ).format(java.util.Date())
                java.io.File(dir, "crash-last.txt").writeText(
                    "=== $stamp thread=${thread.name} app=${BuildConfig.VERSION_NAME}\n" +
                        android.util.Log.getStackTraceString(error).take(60_000),
                )
            }
            prev?.uncaughtException(thread, error)
        }
    }
}
