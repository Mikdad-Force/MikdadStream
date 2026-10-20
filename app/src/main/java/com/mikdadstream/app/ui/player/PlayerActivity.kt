package com.mikdadstream.app.ui.player

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.view.KeyEvent
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mikdadstream.app.AppGraph
import com.mikdadstream.app.data.local.HistoryEntity
import com.mikdadstream.app.data.remote.EpisodeData
import com.mikdadstream.app.data.remote.MediaStream
import com.mikdadstream.app.data.remote.SubtitleTrack
import com.mikdadstream.app.ui.detail.PlayRequest
import com.mikdadstream.app.ui.theme.MikdadStreamTheme

class PlayerActivity : ComponentActivity() {

    private val gson = Gson()
    private val saveScope = kotlinx.coroutines.CoroutineScope(
        kotlinx.coroutines.SupervisorJob() + kotlinx.coroutines.Dispatchers.IO
    )

    val inPipState = mutableStateOf(false)

    override fun onDestroy() {
        saveScope.cancel()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        val request = parseRequest(intent) ?: run {
            finish()
            return
        }

        setContent {
            MikdadStreamTheme(darkTheme = true) {
                PlayerScreen(
                    request = request,
                    onBack = { finish() },
                    onProgress = ::saveProgress,
                )
            }
        }
    }

    /**
     * Dukungan remote TV / D-pad. Tombol media selalu ditangani. Saat kontrol
     * tersembunyi, D-pad mengontrol pemutaran langsung; saat kontrol tampil,
     * D-pad diteruskan ke Compose agar bisa menjelajah tombol kontrol.
     */
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (PlayerRemoteController.ready && event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE,
                KeyEvent.KEYCODE_HEADSETHOOK -> {
                    if (event.repeatCount == 0) PlayerRemoteController.togglePlay()
                    return true
                }
                KeyEvent.KEYCODE_MEDIA_PLAY -> {
                    PlayerRemoteController.play()
                    return true
                }
                KeyEvent.KEYCODE_MEDIA_PAUSE -> {
                    PlayerRemoteController.pause()
                    return true
                }
                KeyEvent.KEYCODE_MEDIA_REWIND -> {
                    PlayerRemoteController.seekBack()
                    return true
                }
                KeyEvent.KEYCODE_MEDIA_FAST_FORWARD -> {
                    PlayerRemoteController.seekForward()
                    return true
                }
            }
            if (!PlayerRemoteController.controlsVisible()) {
                when (event.keyCode) {
                    KeyEvent.KEYCODE_DPAD_CENTER,
                    KeyEvent.KEYCODE_ENTER,
                    KeyEvent.KEYCODE_NUMPAD_ENTER,
                    KeyEvent.KEYCODE_SPACE,
                    KeyEvent.KEYCODE_BUTTON_A -> {
                        PlayerRemoteController.togglePlay()
                        return true
                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        PlayerRemoteController.seekBack()
                        return true
                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        PlayerRemoteController.seekForward()
                        return true
                    }
                    KeyEvent.KEYCODE_DPAD_UP,
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        PlayerRemoteController.showControls()
                        return true
                    }
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }

    fun enterPip() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                enterPictureInPictureMode(
                    android.app.PictureInPictureParams.Builder()
                        .setAspectRatio(Rational(16, 9))
                        .build()
                )
            } catch (_: Exception) {
            }
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration,
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        inPipState.value = isInPictureInPictureMode
    }

    private fun saveProgress(
        modelId: String,
        contentId: String,
        title: String,
        cover: String?,
        episodeId: String,
        episodeNumber: Int,
        positionMs: Long,
        durationMs: Long,
    ) {
        // Kanal live tidak disimpan ke riwayat (tidak punya durasi/episode).
        if (contentId.startsWith("live:")) return
        saveScope.launch {
            AppGraph.repository.historyDao.upsert(
                HistoryEntity(
                    contentId = contentId,
                    modelId = modelId,
                    title = title,
                    cover = cover,
                    episodeId = episodeId,
                    episodeNumber = episodeNumber,
                    positionMs = positionMs,
                    durationMs = durationMs,
                    updatedAt = System.currentTimeMillis(),
                )
            )
        }
    }

    private fun parseRequest(intent: Intent?): PlayRequest? {
        intent ?: return null
        val modelId = intent.getStringExtra(EXTRA_MODEL_ID) ?: return null
        val contentId = intent.getStringExtra(EXTRA_CONTENT_ID) ?: return null
        val episodeId = intent.getStringExtra(EXTRA_EPISODE_ID) ?: return null
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "Pemutar"
        val episodesJson = intent.getStringExtra(EXTRA_EPISODES)
        val episodes: List<EpisodeData> = if (episodesJson.isNullOrBlank()) emptyList() else {
            runCatching {
                val type = object : TypeToken<List<EpisodeData>>() {}.type
                gson.fromJson<List<EpisodeData>>(episodesJson, type) ?: emptyList()
            }.getOrDefault(emptyList())
        }
        val streamsJson = intent.getStringExtra(EXTRA_STREAMS)
        val streams: List<MediaStream> = if (streamsJson.isNullOrBlank()) emptyList() else {
            runCatching {
                val type = object : TypeToken<List<MediaStream>>() {}.type
                gson.fromJson<List<MediaStream>>(streamsJson, type) ?: emptyList()
            }.getOrDefault(emptyList())
        }
        val subtitlesJson = intent.getStringExtra(EXTRA_SUBTITLES)
        val subtitles: List<SubtitleTrack> = if (subtitlesJson.isNullOrBlank()) emptyList() else {
            runCatching {
                val type = object : TypeToken<List<SubtitleTrack>>() {}.type
                gson.fromJson<List<SubtitleTrack>>(subtitlesJson, type) ?: emptyList()
            }.getOrDefault(emptyList())
        }
        return PlayRequest(
            modelId = modelId,
            contentId = contentId,
            episodeId = episodeId,
            episodeNumber = intent.getIntExtra(EXTRA_EPISODE_NUMBER, 0),
            title = title,
            cover = intent.getStringExtra(EXTRA_COVER),
            episodes = episodes,
            startPositionMs = intent.getLongExtra(EXTRA_POSITION, 0L),
            localUrl = intent.getStringExtra(EXTRA_LOCAL_URL),
            streams = streams,
            subtitles = subtitles,
        )
    }

    companion object {
        private const val EXTRA_MODEL_ID = "model_id"
        private const val EXTRA_CONTENT_ID = "content_id"
        private const val EXTRA_EPISODE_ID = "episode_id"
        private const val EXTRA_EPISODE_NUMBER = "episode_number"
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_COVER = "cover"
        private const val EXTRA_EPISODES = "episodes"
        private const val EXTRA_POSITION = "position"
        private const val EXTRA_LOCAL_URL = "local_url"
        private const val EXTRA_STREAMS = "streams"
        private const val EXTRA_SUBTITLES = "subtitles"

        fun intent(context: Context, request: PlayRequest): Intent =
            Intent(context, PlayerActivity::class.java).apply {
                putExtra(EXTRA_MODEL_ID, request.modelId)
                putExtra(EXTRA_CONTENT_ID, request.contentId)
                putExtra(EXTRA_EPISODE_ID, request.episodeId)
                putExtra(EXTRA_EPISODE_NUMBER, request.episodeNumber)
                putExtra(EXTRA_TITLE, request.title)
                putExtra(EXTRA_COVER, request.cover)
                putExtra(EXTRA_POSITION, request.startPositionMs)
                putExtra(EXTRA_EPISODES, Gson().toJson(request.episodes))
                if (request.streams.isNotEmpty()) {
                    putExtra(EXTRA_STREAMS, Gson().toJson(request.streams))
                }
                if (request.subtitles.isNotEmpty()) {
                    putExtra(EXTRA_SUBTITLES, Gson().toJson(request.subtitles))
                }
                request.localUrl?.let { putExtra(EXTRA_LOCAL_URL, it) }
            }
    }
}
