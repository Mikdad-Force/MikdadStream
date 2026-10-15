package com.mikdadstream.app.ui.library

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    Column(modifier = Modifier.fillMaxSize().padding(top = contentPadding.calculateTopPadding())) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(14.dp))
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
                        start = 16.dp,
                        end = 16.dp,
                        top = 4.dp,
                        bottom = contentPadding.calculateBottomPadding() + 24.dp,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(13.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp),
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
                                    .background(androidx.compose.ui.graphics.Color(0x9905060F)),
                            ) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = "Hapus",
                                    tint = androidx.compose.ui.graphics.Color.White,
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
                        start = 16.dp,
                        end = 16.dp,
                        top = 4.dp,
                        bottom = contentPadding.calculateBottomPadding() + 24.dp,
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Terakhir ditonton",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { viewModel.clearHistory() }
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                            ) {
                                Icon(
                                    Icons.Filled.DeleteSweep,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(16.dp),
                                )
                                Text(
                                    text = "Bersihkan",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.secondary,
                                )
                            }
                        }
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
    val bg by androidx.compose.animation.animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.Transparent,
        animationSpec = androidx.compose.animation.core.tween(220),
        label = "tabBg",
    )
    val fg by androidx.compose.animation.animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = androidx.compose.animation.core.tween(220),
        label = "tabFg",
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(11.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
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
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f))
            .clickable(onClick = onResume)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .width(64.dp)
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(12.dp)),
        ) {
            PosterImage(url = entry.cover, modifier = Modifier.fillMaxSize())
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        androidx.compose.ui.graphics.Brush.verticalGradient(
                            listOf(
                                androidx.compose.ui.graphics.Color.Transparent,
                                androidx.compose.ui.graphics.Color(0xCC04060F),
                            )
                        )
                    )
            )
            Icon(
                Icons.Filled.PlayArrow,
                contentDescription = null,
                tint = androidx.compose.ui.graphics.Color.White,
                modifier = Modifier.align(Alignment.Center).size(24.dp),
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
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            LinearProgressIndicator(
                progress = { entry.progress },
                modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(999.dp)),
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
