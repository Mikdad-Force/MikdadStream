package com.mikdadstream.app.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mikdadstream.app.data.remote.EpisodeData
import com.mikdadstream.app.ui.components.FullScreenState
import com.mikdadstream.app.ui.components.PosterImage
import com.mikdadstream.app.ui.components.SectionHeader
import com.mikdadstream.app.ui.components.ShimmerBox
import com.mikdadstream.app.ui.components.softShadow
import com.mikdadstream.app.ui.theme.brandBrush

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    contentPadding: PaddingValues,
    onBack: () -> Unit,
    onPlay: (PlayRequest) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    when {
        state.loading -> DetailSkeleton(onBack = onBack, contentPadding = contentPadding)

        state.error != null || state.detail == null -> Column(
            modifier = Modifier.fillMaxSize().padding(top = contentPadding.calculateTopPadding()),
        ) {
            BackButton(onBack = onBack)
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
            onPlay = { request -> onPlay(request) },
            onToggleWatchlist = { viewModel.toggleWatchlist() },
            onDownload = { episode -> viewModel.openDownloads(episode) },
            onCloseDownloads = { viewModel.closeDownloads() },
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
    onCloseDownloads: () -> Unit,
) {
    val detail = state.detail ?: return
    var descriptionExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding() + 32.dp),
    ) {
        // ---------- Backdrop ----------
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp),
            ) {
                PosterImage(
                    url = detail.cover,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier.fillMaxSize().background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0x6604060F),
                                Color.Transparent,
                                Color(0xF2070A13),
                            )
                        )
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(start = 10.dp, end = 16.dp, top = 12.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    BackButton(onBack = onBack)
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        detail.category?.takeIf { it.isNotBlank() }?.let { category ->
                            Badge(text = category, background = MaterialTheme.colorScheme.primary)
                        }
                        val count = detail.episodeList.size
                        if (count > 1) {
                            Badge(text = "$count Episode", background = MaterialTheme.colorScheme.secondary)
                        } else if (count == 1) {
                            Badge(text = "Film", background = MaterialTheme.colorScheme.tertiary)
                        }
                    }
                    Text(
                        text = detail.safeTitle,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }

        // ---------- Aksi ----------
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                val historyLabel = state.history?.let { h ->
                    if (h.episodeNumber != null && h.positionMs > 0L) "Lanjutkan • EP ${h.episodeNumber}"
                    else "Putar Sekarang"
                } ?: "Putar Sekarang"

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .shadow(12.dp, RoundedCornerShape(16.dp), ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))
                        .clip(RoundedCornerShape(16.dp))
                        .background(brandBrush())
                        .clickable {
                            val request = state.history?.let { resumePlayRequest(state) } ?: null
                            onPlay(request ?: firstPlayRequest(state) ?: return@clickable)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(26.dp),
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = historyLabel,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                    )
                }

                ActionCircle(
                    icon = if (state.inWatchlist) Icons.Filled.BookmarkAdded else Icons.Filled.BookmarkBorder,
                    tint = if (state.inWatchlist) MaterialTheme.colorScheme.tertiary
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = onToggleWatchlist,
                )
                ActionCircle(
                    icon = Icons.Filled.Download,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = {
                        val target = state.history?.episodeId
                            ?.let { id -> detail.episodeList.firstOrNull { it.safeId == id } }
                            ?: detail.episodeList.firstOrNull()
                        if (target != null) onDownload(target)
                    },
                )
            }
        }

        // ---------- Info & sinopsis ----------
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                state.history?.takeIf { it.positionMs > 0 }?.let { h ->
                    Text(
                        text = buildString {
                            append("Terakhir ditonton")
                            h.episodeNumber?.let { append(" • Episode $it") }
                            if (h.durationMs > 0) {
                                append(" • ")
                                append(formatTime(h.positionMs))
                                append(" / ")
                                append(formatTime(h.durationMs))
                            }
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                if (!detail.description.isNullOrBlank()) {
                    Text(
                        text = detail.description,
                        style = MaterialTheme.typography.bodyMedium,
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
                            modifier = Modifier.clickable { descriptionExpanded = !descriptionExpanded },
                        )
                    }
                }
            }
        }

        // ---------- Daftar episode ----------
        item {
            SectionHeader(
                title = if (detail.episodeList.size > 1) "Daftar Episode" else "Putaran",
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        if (detail.episodeList.isEmpty()) {
            item {
                Text(
                    text = "Belum ada daftar episode untuk konten ini.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                )
            }
        }

        items(detail.episodeList, key = { it.safeId }) { episode ->
            EpisodeRow(
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

    // ---------- Dialog unduhan ----------
    state.downloadEpisode?.let { episode ->
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
                        state.downloads.forEach { file ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
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

private fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val h = totalSeconds / 3600
    val m = (totalSeconds % 3600) / 60
    val s = totalSeconds % 60
    return if (h > 0) "%d:%02d:%02d".format(h, m, s) else "%02d:%02d".format(m, s)
}

@Composable
private fun BackButton(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0x9905060F))
            .clickable(onClick = onBack),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Kembali",
            tint = Color.White,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun Badge(text: String, background: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(background.copy(alpha = 0.92f))
            .padding(horizontal = 8.dp, vertical = 3.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun ActionCircle(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(23.dp))
    }
}

@Composable
private fun EpisodeRow(
    episode: EpisodeData,
    active: Boolean,
    onClick: () -> Unit,
    onDownload: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .softShadow(6.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (active) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
            .clickable(onClick = onClick)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface),
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
                    modifier = Modifier.size(22.dp),
                )
            }
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp),
            ) {
                Text(
                    text = if (episode.safeNumber > 0) "Episode ${episode.safeNumber}" else "Putar",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
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
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Text(
            text = "Unduh",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable(onClick = onDownload)
                .padding(horizontal = 8.dp, vertical = 8.dp),
        )

        Icon(
            Icons.Filled.PlayArrow,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp),
        )
    }
}

@Composable
private fun DetailSkeleton(onBack: () -> Unit, contentPadding: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
            ShimmerBox(modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(0.dp))
            Box(
                modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(listOf(Color.Transparent, Color(0xF2070A13)))
                )
            )
            Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 12.dp)) {
                BackButton(onBack = onBack)
            }
        }
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ShimmerBox(modifier = Modifier.fillMaxWidth(0.7f).height(26.dp), shape = RoundedCornerShape(8.dp))
            ShimmerBox(modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(16.dp))
            repeat(3) {
                ShimmerBox(modifier = Modifier.fillMaxWidth().height(14.dp), shape = RoundedCornerShape(6.dp))
            }
            Spacer(Modifier.height(8.dp))
            repeat(3) {
                ShimmerBox(modifier = Modifier.fillMaxWidth().height(66.dp), shape = RoundedCornerShape(16.dp))
            }
        }
        Spacer(Modifier.navigationBarsPadding().height(1.dp))
        Spacer(Modifier.height(contentPadding.calculateBottomPadding()))
    }
}
