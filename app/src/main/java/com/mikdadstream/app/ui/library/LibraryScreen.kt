package com.mikdadstream.app.ui.library

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikdadstream.app.data.local.HistoryEntity
import com.mikdadstream.app.data.local.WatchlistEntity
import com.mikdadstream.app.data.remote.MediaItem
import com.mikdadstream.app.data.repository.StreamRepository
import com.mikdadstream.app.ui.components.FullScreenState
import com.mikdadstream.app.ui.components.PosterImage
import com.mikdadstream.app.ui.components.SectionHeader
import com.mikdadstream.app.ui.components.VideoCard
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LibraryViewModel(repo: StreamRepository) : ViewModel() {

    val watchlist: StateFlow<List<WatchlistEntity>> = repo.watchlistDao.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val history: StateFlow<List<HistoryEntity>> = repo.historyDao.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val watchlistDao = repo.watchlistDao
    private val historyDao = repo.historyDao

    fun removeWatchlist(key: String) {
        viewModelScope.launch { watchlistDao.delete(key) }
    }

    fun removeHistory(contentId: String) {
        viewModelScope.launch { historyDao.delete(contentId) }
    }

    fun clearHistory() {
        viewModelScope.launch { historyDao.clear() }
    }
}

@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel,
    initialTab: Int,
    contentPadding: PaddingValues,
    onOpenItem: (modelId: String, contentId: String) -> Unit,
    onOpenHistory: (HistoryEntity) -> Unit,
) {
    val watchlist by viewModel.watchlist.collectAsState()
    val history by viewModel.history.collectAsState()
    var tab by remember { mutableStateOf(initialTab) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        // ---- Streamview header ----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = if (tab == 0) "Koleksi" else "Riwayat",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
            )
        }

        // ---- Segmented pill (Koleksi / Riwayat) ----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(4.dp),
        ) {
            TabButton(
                label = "Koleksi",
                selected = tab == 0,
                count = watchlist.size,
                modifier = Modifier.weight(1f),
            ) { tab = 0 }
            TabButton(
                label = "Riwayat",
                selected = tab == 1,
                count = history.size,
                modifier = Modifier.weight(1f),
            ) { tab = 1 }
        }

        Spacer(Modifier.height(12.dp))

        when (tab) {
            0 -> if (watchlist.isEmpty()) {
                FullScreenState(
                    icon = Icons.Filled.Bookmark,
                    title = "Koleksi masih kosong",
                    subtitle = "Ketuk ikon bookmark di halaman detail untuk menyimpan film atau anime favoritmu.",
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 118.dp),
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        end = 20.dp,
                        top = 4.dp,
                        bottom = contentPadding.calculateBottomPadding() + 24.dp,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(watchlist, key = { it.key }) { entry ->
                        Box {
                            VideoCard(
                                item = MediaItem(
                                    id = entry.contentId,
                                    title = entry.title,
                                    cover = entry.cover,
                                    tag = listOfNotNull(entry.tag),
                                    source = entry.modelId.ifBlank { null },
                                ),
                                onClick = { onOpenItem(entry.modelId, entry.contentId) },
                            )
                            IconButton(
                                onClick = { viewModel.removeWatchlist(entry.key) },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(Color(0x9902050F)),
                            ) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = "Hapus",
                                    tint = Color.White,
                                    modifier = Modifier.size(15.dp),
                                )
                            }
                        }
                    }
                }
            }

            else -> if (history.isEmpty()) {
                FullScreenState(
                    icon = Icons.Filled.History,
                    title = "Belum ada riwayat",
                    subtitle = "Tontonanmu akan muncul di sini agar mudah dilanjutkan kembali.",
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        end = 20.dp,
                        top = 4.dp,
                        bottom = contentPadding.calculateBottomPadding() + 24.dp,
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    item {
                        SectionHeader(
                            title = "Terakhir ditonton",
                            actionLabel = "Bersihkan",
                            onAction = { viewModel.clearHistory() },
                        )
                    }
                    items(history, key = { it.contentId }) { entry ->
                        HistoryRow(
                            entry = entry,
                            onResume = { onOpenHistory(entry) },
                            onRemove = { viewModel.removeHistory(entry.contentId) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TabButton(
    label: String,
    selected: Boolean,
    count: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val bg by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = tween(220),
        label = "tabBg",
    )
    val fg by animateColorAsState(
        targetValue = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(220),
        label = "tabFg",
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(9.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                color = fg,
            )
            if (count > 0) {
                Text(
                    text = "$count",
                    style = MaterialTheme.typography.labelSmall,
                    color = fg.copy(alpha = 0.85f),
                )
            }
        }
    }
}

@Composable
private fun HistoryRow(
    entry: HistoryEntity,
    onResume: () -> Unit,
    onRemove: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onResume),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(72.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            PosterImage(url = entry.cover, modifier = Modifier.fillMaxSize())
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color(0x9902050F),
                            )
                        )
                    )
            )
            Icon(
                Icons.Filled.PlayArrow,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center).size(22.dp),
            )
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = entry.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = listOfNotNull(
                    entry.episodeNumber?.let { "Episode $it" },
                    if (entry.durationMs > 0) formatProgress(entry) else null,
                ).joinToString(" • "),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            LinearProgressIndicator(
                progress = { entry.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(999.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.outline,
            )
        }

        IconButton(onClick = onRemove) {
            Icon(
                Icons.Filled.Close,
                contentDescription = "Hapus dari riwayat",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}

private fun formatProgress(entry: HistoryEntity): String {
    val pos = entry.positionMs / 1000
    val dur = entry.durationMs / 1000
    return "%02d:%02d / %02d:%02d".format(pos / 60, pos % 60, dur / 60, dur % 60)
}
