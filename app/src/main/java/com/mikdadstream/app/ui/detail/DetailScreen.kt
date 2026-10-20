package com.mikdadstream.app.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.OndemandVideo
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mikdadstream.app.data.remote.EpisodeData
import com.mikdadstream.app.data.remote.MediaStream
import com.mikdadstream.app.ui.components.FullScreenState
import com.mikdadstream.app.ui.components.PosterImage
import com.mikdadstream.app.ui.components.SectionHeader
import com.mikdadstream.app.ui.components.ShimmerBox
import com.mikdadstream.app.ui.theme.StreamBg
import com.mikdadstream.app.ui.tv.TvRequestFocusOnShow
import com.mikdadstream.app.ui.tv.isTelevision
import kotlinx.coroutines.delay

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    contentPadding: PaddingValues,
    onBack: () -> Unit,
    onOpenPremium: () -> Unit = {},
    onPlay: (PlayRequest) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    when {
        state.loading -> DetailSkeleton(onBack = onBack, contentPadding = contentPadding)

        state.error != null || state.detail == null -> Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = contentPadding.calculateTopPadding()),
        ) {
            Box(modifier = Modifier.statusBarsPadding().padding(start = 16.dp, top = 12.dp)) {
                ScrimIcon(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    onClick = onBack,
                )
            }
            FullScreenState(
                icon = Icons.Filled.ErrorOutline,
                title = "Tidak bisa memuat detail",
                subtitle = state.error.orEmpty(),
                actionLabel = "Kembali",
                onAction = onBack,
            )
        }

        else -> DetailContent(
            state = state,
            contentPadding = contentPadding,
            onBack = onBack,
            onPlay = { request -> viewModel.preparePlay(request) { ready -> onPlay(ready) } },
            onToggleWatchlist = { viewModel.toggleWatchlist() },
            onDownload = { episode -> viewModel.openDownloads(episode) },
            onDownloadFile = { file -> viewModel.startDownload(file) },
            onCloseDownloads = { viewModel.closeDownloads() },
        )
    }

    if (state.downloadLocked) {
        val premiumFocus = remember { FocusRequester() }
        TvRequestFocusOnShow(premiumFocus)
        AlertDialog(
            onDismissRequest = { viewModel.dismissDownloadLock() },
            title = { Text("Butuh Premium") },
            text = {
                Text("Fitur unduh video hanya untuk pengguna Premium. Buka sekarang dengan Rp 10.000 sekali bayar.")
            },
            confirmButton = {
                TextButton(
                    modifier = Modifier.focusRequester(premiumFocus),
                    onClick = {
                        viewModel.dismissDownloadLock()
                        viewModel.closeDownloads()
                        onOpenPremium()
                    },
                ) { Text("Beli Premium") }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissDownloadLock() }) { Text("Nanti") }
            },
        )
    }
}

@Composable
private fun DetailContent(
    state: DetailUiState,
    contentPadding: PaddingValues,
    onBack: () -> Unit,
    onPlay: (PlayRequest) -> Unit,
    onToggleWatchlist: () -> Unit,
    onDownload: (EpisodeData) -> Unit,
    onDownloadFile: (MediaStream) -> Unit,
    onCloseDownloads: () -> Unit,
) {
    val detail = state.detail ?: return
    var descriptionExpanded by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Episode", "Rekomendasi", "Tentang", "Ulasan")

    val playLabel = state.history?.let { h ->
        if (h.episodeNumber != null && h.positionMs > 0L) "Lanjutkan • EP ${h.episodeNumber}"
        else "Putar"
    } ?: "Putar"

    // Season tersinkron dengan data film: dari kode episode "se_ep" hasil season-info.
    val seasons = detail.episodeList
        .mapNotNull { it.safeId.substringBefore("_").toIntOrNull() }
        .distinct().sorted()
    var selectedSeason by remember(detail.safeId) {
        mutableStateOf(seasons.lastOrNull() ?: 1)
    }
    val seasonEpisodes = if (seasons.size > 1) {
        detail.episodeList.filter { it.safeId.substringBefore("_").toIntOrNull() == selectedSeason }
    } else {
        detail.episodeList
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding() + 32.dp),
    ) {
        // ---------- Backdrop + aksi atas ----------
        item {
            Box(modifier = Modifier.fillMaxWidth().height(360.dp)) {
                PosterImage(
                    url = detail.cover,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier.fillMaxSize().background(
                        Brush.verticalGradient(listOf(Color.Transparent, StreamBg))
                    )
                )
                if (!androidx.compose.ui.platform.LocalContext.current.isTelevision()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ScrimIcon(
                            icon = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            onClick = onBack,
                        )
                        ScrimIcon(
                            icon = if (state.inWatchlist) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (state.inWatchlist) "Hapus dari daftar" else "Tambah ke daftar",
                            onClick = onToggleWatchlist,
                        )
                    }
                }
            }
        }

        // ---------- Judul ----------
        item {
            Text(
                text = detail.safeTitle,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 4.dp, bottom = 10.dp),
            )
            if (!detail.metaLine.isNullOrBlank()) {
                Text(
                    text = detail.metaLine,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 10.dp),
                )
            }
        }

        // ---------- Sinopsis ----------
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                if (!detail.description.isNullOrBlank()) {
                    Text(
                        text = detail.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = if (descriptionExpanded) Int.MAX_VALUE else 4,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { descriptionExpanded = !descriptionExpanded },
                    )
                    if (detail.description.length > 120) {
                        Text(
                            text = if (descriptionExpanded) "Lebih sedikit" else "Selengkapnya",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .clickable { descriptionExpanded = !descriptionExpanded },
                        )
                    }
                }
            }
        }

        // ---------- Meta ----------
        item {
            val count = detail.episodeList.size
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (count > 1) {
                    MetaItem(Icons.Outlined.OndemandVideo, "$count Episode")
                } else if (count == 1) {
                    MetaItem(Icons.Outlined.OndemandVideo, "Film")
                }
                detail.category?.takeIf { it.isNotBlank() }?.let { category ->
                    MetaItem(Icons.Outlined.LocalOffer, category)
                }
                detail.durationSeconds?.takeIf { it > 0 }?.let { seconds ->
                    MetaItem(Icons.Outlined.Schedule, "${seconds / 60} menit")
                }
            }
        }

        // ---------- Baris aksi ----------
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable(enabled = !state.preparing) {
                            val request = state.history?.let { resumePlayRequest(state) }
                            onPlay(request ?: firstPlayRequest(state) ?: return@clickable)
                        }
                        .padding(horizontal = 22.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (state.preparing) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp),
                        )
                    } else {
                        Icon(
                            Icons.Filled.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                    Text(
                        text = if (state.preparing) "Menyiapkan\u2026" else playLabel,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        maxLines = 1,
                    )
                }

                Spacer(Modifier.weight(1f))

                CircleAction(
                    icon = if (state.inWatchlist) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (state.inWatchlist) "Hapus dari daftar" else "Tambah ke daftar",
                    background = MaterialTheme.colorScheme.primaryContainer,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    onClick = onToggleWatchlist,
                )
                CircleAction(
                    icon = Icons.Filled.Download,
                    contentDescription = "Unduh",
                    background = MaterialTheme.colorScheme.surfaceVariant,
                    tint = Color.White,
                    onClick = {
                        val target = state.history?.episodeId
                            ?.let { id -> detail.episodeList.firstOrNull { it.safeId == id } }
                            ?: detail.episodeList.firstOrNull()
                        if (target != null) onDownload(target)
                    },
                )
            }
        }

        // ---------- Tab ----------
        item {
            TabsRow(tabs = tabs, selected = selectedTab, onSelect = { selectedTab = it })
        }

        when (selectedTab) {
            0 -> {
                item {
                    SectionHeader(
                        title = if (seasons.size > 1) {
                            "Season $selectedSeason"
                        } else if (detail.episodeList.size > 1) "Daftar Episode" else "Putaran",
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                    )
                }

                if (seasons.size > 1) {
                    item {
                        // Baris pilih Season (sinkron jumlah musim film).
                        androidx.compose.foundation.lazy.LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(seasons, key = { it }) { s ->
                                val sel = s == selectedSeason
                                Text(
                                    text = "Season $s",
                                    color = if (sel) Color.White else Color(0xFF9AA3AE),
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(
                                            if (sel) MaterialTheme.colorScheme.primary
                                            else MaterialTheme.colorScheme.surfaceVariant
                                        )
                                        .clickable { selectedSeason = s }
                                        .padding(horizontal = 16.dp, vertical = 9.dp),
                                )
                            }
                        }
                    }
                }

                if (seasonEpisodes.isEmpty()) {
                    item {
                        Text(
                            text = "Belum ada daftar episode untuk konten ini.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                        )
                    }
                }

                items(seasonEpisodes, key = { it.safeId }) { episode ->
                    EpisodeRow(
                        modifier = Modifier.animateItem(),
                        episode = episode,
                        active = episode.safeId == state.history?.episodeId,
                        onClick = {
                            val request = playRequestFor(state, episode)
                            if (request != null) onPlay(request)
                        },
                        onDownload = { onDownload(episode) },
                    )
                }
            }

            2 -> item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    SectionHeader(title = "Tentang")
                    if (!detail.description.isNullOrBlank()) {
                        Text(
                            text = detail.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    val count = detail.episodeList.size
                    if (count > 0) {
                        Text(
                            text = if (count > 1) "$count episode tersedia" else "1 episode tersedia",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            else -> item {
                FullScreenState(
                    icon = if (selectedTab == 1) Icons.Filled.SmartDisplay else Icons.Filled.Star,
                    title = if (selectedTab == 1) "Belum ada rekomendasi" else "Belum ada ulasan",
                    subtitle = if (selectedTab == 1) {
                        "Rekomendasi serupa akan muncul di sini."
                    } else {
                        "Ulasan penonton akan muncul di sini."
                    },
                )
            }
        }
    }

    // ---------- Dialog unduhan ----------
    state.downloadEpisode?.let { episode ->
        val rowFocus = remember { FocusRequester() }
        TvRequestFocusOnShow(rowFocus, !state.loadingDownloads && state.downloads.isNotEmpty())
        AlertDialog(
            onDismissRequest = onCloseDownloads,
            containerColor = MaterialTheme.colorScheme.surface,
            title = {
                Text(
                    text = "Unduh • Episode ${episode.safeNumber}",
                    color = MaterialTheme.colorScheme.onBackground,
                )
            },
            text = {
                when {
                    state.loadingDownloads -> Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) { CircularProgressIndicator() }

                    state.downloadError != null -> Text(
                        text = state.downloadError.orEmpty(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    else -> Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = "Pilih kualitas untuk mulai mengunduh:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        state.downloads.forEachIndexed { idx, file ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(
                                        if (idx == 0)
                                            Modifier.focusRequester(rowFocus)
                                        else Modifier
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .clickable { onDownloadFile(file) }
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Icon(
                                    Icons.Filled.Download,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp),
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = file.label,
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                    Text(
                                        text = file.kindLabel,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                                Icon(
                                    Icons.Filled.Download,
                                    contentDescription = "Unduh",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = onCloseDownloads) { Text("Tutup") }
            },
        )
    }
}

// ---------- Pembantu ----------

private fun firstPlayRequest(state: DetailUiState): PlayRequest? {
    val detail = state.detail ?: return null
    val modelId = state.modelId ?: return null
    val episode = detail.episodeList.firstOrNull() ?: return null
    return PlayRequest(
        modelId = modelId,
        contentId = detail.safeId,
        episodeId = episode.safeId,
        episodeNumber = episode.safeNumber,
        title = detail.safeTitle,
        cover = detail.cover,
        episodes = detail.episodeList,
    )
}

private fun resumePlayRequest(state: DetailUiState): PlayRequest? {
    val detail = state.detail ?: return null
    val modelId = state.modelId ?: return null
    val history = state.history ?: return null
    val episode = detail.episodeList.firstOrNull { it.safeId == history.episodeId }
        ?: detail.episodeList.firstOrNull() ?: return null
    return PlayRequest(
        modelId = modelId,
        contentId = detail.safeId,
        episodeId = episode.safeId,
        episodeNumber = episode.safeNumber,
        title = detail.safeTitle,
        cover = detail.cover,
        episodes = detail.episodeList,
        startPositionMs = if (episode.safeId == history.episodeId) history.positionMs else 0L,
    )
}

private fun playRequestFor(state: DetailUiState, episode: EpisodeData): PlayRequest? {
    val detail = state.detail ?: return null
    val modelId = state.modelId ?: return null
    val history = state.history
    return PlayRequest(
        modelId = modelId,
        contentId = detail.safeId,
        episodeId = episode.safeId,
        episodeNumber = episode.safeNumber,
        title = detail.safeTitle,
        cover = detail.cover,
        episodes = detail.episodeList,
        startPositionMs = if (history?.episodeId == episode.safeId) history.positionMs else 0L,
    )
}

@Composable
private fun ScrimIcon(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0x8002050F))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
private fun CircleAction(
    icon: ImageVector,
    contentDescription: String?,
    background: Color,
    tint: Color,
    onClick: () -> Unit,
    size: Dp = 48.dp,
    iconSize: Dp = 22.dp,
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(iconSize),
        )
    }
}

@Composable
private fun MetaItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(15.dp),
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun TabsRow(
    tabs: List<String>,
    selected: Int,
    onSelect: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        tabs.forEachIndexed { index, title ->
            val active = index == selected
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .clickable { onSelect(index) },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (active) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1,
                )
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(if (active) MaterialTheme.colorScheme.primary else Color.Transparent),
                )
            }
        }
    }
}

@Composable
private fun EpisodeRow(
    episode: EpisodeData,
    active: Boolean,
    onClick: () -> Unit,
    onDownload: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (active) MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
                else Color.Transparent
            )
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(72.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            if (!episode.thumbnailUrl.isNullOrBlank()) {
                PosterImage(
                    url = episode.thumbnailUrl,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Icon(
                    Icons.Filled.SmartDisplay,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(26.dp),
                )
            }
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp),
            ) {
                Text(
                    text = if (episode.safeNumber > 0) "Episode ${episode.safeNumber}" else "Putar",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                val badge = episode.badgeLabel?.takeIf { it.isNotBlank() }
                if (badge != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (episode.isLocked) MaterialTheme.colorScheme.secondary.copy(alpha = 0.18f)
                                else MaterialTheme.colorScheme.tertiary.copy(alpha = 0.18f)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (episode.isLocked) MaterialTheme.colorScheme.secondary
                            else MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
            Text(
                text = listOfNotNull(
                    episode.durationSeconds?.takeIf { it > 0 }?.let { "${it / 60} menit" },
                    episode.lockedReason?.takeIf { it.isNotBlank() },
                ).joinToString(" • ").ifBlank { "Ketuk untuk memutar" },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        CircleAction(
            icon = Icons.Filled.Download,
            contentDescription = "Unduh",
            background = MaterialTheme.colorScheme.surfaceVariant,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            onClick = onDownload,
            size = 36.dp,
            iconSize = 18.dp,
        )
    }
}

@Composable
private fun DetailSkeleton(onBack: () -> Unit, contentPadding: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Box(modifier = Modifier.fillMaxWidth().height(340.dp)) {
            ShimmerBox(modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(0.dp))
            Box(
                modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(listOf(Color.Transparent, StreamBg))
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ScrimIcon(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    onClick = onBack,
                )
                ShimmerBox(modifier = Modifier.size(40.dp), shape = CircleShape)
            }
        }
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ShimmerBox(modifier = Modifier.fillMaxWidth(0.65f).height(24.dp), shape = RoundedCornerShape(8.dp))
            repeat(3) {
                ShimmerBox(modifier = Modifier.fillMaxWidth().height(12.dp), shape = RoundedCornerShape(6.dp))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ShimmerBox(
                    modifier = Modifier.width(150.dp).height(50.dp),
                    shape = RoundedCornerShape(999.dp),
                )
                Spacer(Modifier.weight(1f))
                ShimmerBox(modifier = Modifier.size(48.dp), shape = CircleShape)
                ShimmerBox(modifier = Modifier.size(48.dp), shape = CircleShape)
            }
            Spacer(Modifier.height(4.dp))
            repeat(3) {
                ShimmerBox(modifier = Modifier.fillMaxWidth().height(72.dp), shape = RoundedCornerShape(10.dp))
            }
        }
        Spacer(Modifier.navigationBarsPadding().height(1.dp))
        Spacer(Modifier.height(contentPadding.calculateBottomPadding()))
    }
}
