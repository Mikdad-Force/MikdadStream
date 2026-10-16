package com.mikdadstream.app.data.local

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val key: String,
    @ColumnInfo(name = "content_id") val contentId: String,
    @ColumnInfo(name = "model_id") val modelId: String,
    val title: String,
    val cover: String?,
    val tag: String?,
    @ColumnInfo(name = "added_at") val addedAt: Long,
)

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey @ColumnInfo(name = "content_id") val contentId: String,
    @ColumnInfo(name = "model_id") val modelId: String,
    val title: String,
    val cover: String?,
    @ColumnInfo(name = "episode_id") val episodeId: String?,
    @ColumnInfo(name = "episode_number") val episodeNumber: Int?,
    @ColumnInfo(name = "position_ms") val positionMs: Long,
    @ColumnInfo(name = "duration_ms") val durationMs: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
) {
    val progress: Float
        get() = if (durationMs > 0) (positionMs.toFloat() / durationMs).coerceIn(0f, 1f) else 0f
}

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey val query: String,
    val at: Long,
)

@Entity(tableName = "downloads")
data class DownloadEntity(
    @PrimaryKey val key: String,
    @ColumnInfo(name = "content_id") val contentId: String,
    @ColumnInfo(name = "model_id") val modelId: String,
    val title: String,
    val cover: String?,
    @ColumnInfo(name = "episode_id") val episodeId: String?,
    @ColumnInfo(name = "episode_number") val episodeNumber: Int?,
    val quality: String?,
    val format: String?,
    val url: String,
    @ColumnInfo(name = "headers_json") val headersJson: String?,
    @ColumnInfo(name = "file_path") val filePath: String?,
    @ColumnInfo(name = "bytes_total") val bytesTotal: Long,
    @ColumnInfo(name = "bytes_done") val bytesDone: Long,
    val status: String,
    val progress: Float,
    @ColumnInfo(name = "created_at") val createdAt: Long,
)

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist ORDER BY added_at DESC")
    fun observeAll(): Flow<List<WatchlistEntity>>

    @Query("SELECT * FROM watchlist WHERE key = :key LIMIT 1")
    suspend fun find(key: String): WatchlistEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: WatchlistEntity)

    @Query("DELETE FROM watchlist WHERE key = :key")
    suspend fun delete(key: String)

    @Query("DELETE FROM watchlist")
    suspend fun clear()
}

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY updated_at DESC")
    fun observeAll(): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM history WHERE content_id = :contentId LIMIT 1")
    suspend fun find(contentId: String): HistoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: HistoryEntity)

    @Query("DELETE FROM history WHERE content_id = :contentId")
    suspend fun delete(contentId: String)

    @Query("DELETE FROM history")
    suspend fun clear()
}

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY at DESC LIMIT 30")
    fun observeAll(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: SearchHistoryEntity)

    @Query("DELETE FROM search_history WHERE query = :query")
    suspend fun delete(query: String)

    @Query("DELETE FROM search_history")
    suspend fun clear()
}

@Dao
interface DownloadDao {
    @Query("SELECT * FROM downloads ORDER BY created_at DESC")
    fun observeAll(): Flow<List<DownloadEntity>>

    @Query("SELECT * FROM downloads WHERE key = :key LIMIT 1")
    suspend fun find(key: String): DownloadEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: DownloadEntity)

    @Query(
        "UPDATE downloads SET bytes_done = :done, bytes_total = :total, " +
            "progress = :progress, status = :status WHERE key = :key"
    )
    suspend fun updateProgress(key: String, done: Long, total: Long, progress: Float, status: String)

    @Query("DELETE FROM downloads WHERE key = :key")
    suspend fun delete(key: String)

    @Query("DELETE FROM downloads")
    suspend fun clear()
}

@Database(
    entities = [WatchlistEntity::class, HistoryEntity::class, SearchHistoryEntity::class, DownloadEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
    abstract fun historyDao(): HistoryDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun downloadDao(): DownloadDao
}
