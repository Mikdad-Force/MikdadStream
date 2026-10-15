package com.mikdadstream.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mikdadstream.app.data.local.AppDatabase
import com.mikdadstream.app.data.local.PrefsStore
import com.mikdadstream.app.data.remote.ApiClient
import com.mikdadstream.app.data.repository.StreamRepository

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

    fun init(context: Context) {
        if (::prefs.isInitialized) return
        val app = context.applicationContext
        prefs = PrefsStore(app)
        database = Room.databaseBuilder(app, AppDatabase::class.java, "mikdad_stream.db")
            .fallbackToDestructiveMigration()
            .build()
        apiClient = ApiClient(prefs)
        repository = StreamRepository(
            api = apiClient.api,
            watchlistDao = database.watchlistDao(),
            historyDao = database.historyDao(),
            searchHistoryDao = database.searchHistoryDao(),
        )
    }
}

class MikdadStreamApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppGraph.init(this)
    }
}
