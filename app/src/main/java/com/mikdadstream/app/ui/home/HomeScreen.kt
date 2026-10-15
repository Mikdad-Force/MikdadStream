package com.mikdadstream.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mikdadstream.app.data.local.HistoryEntity
import com.mikdadstream.app.data.remote.MediaItem
import com.mikdadstream.app.ui.components.FilterChipPill
import com.mikdadstream.app.ui.components.pressScale
import com.mikdadstream.app.ui.components.softShadow
import com.mikdadstream.app.ui.components.FullScreenState
import com.mikdadstream.app.ui.components.LoadingGrid
import com.mikdadstream.app.ui.components.PosterImage
import com.mikdadstream.app.ui.components.SectionHeader
import com.mikdadstream.app.ui.components.VideoCard
import com.mikdadstream.app.ui.theme.brandBrush

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    contentPadding: PaddingValues,
    onOpenItem: (item: MediaItem, modelId: String?) -> Unit,
    onContinue: (item: HistoryEntity) -> Unit,
    onSeeHistory: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val history by viewModel.continueWatching.collectAsState()
    val gridState = rememberLazyGridState()
    val hero = if (state.loading || state.error != null) null
    else state.items.firstOrNull { !it.isNews } ?: state.items.firstOrNull()
    val rest = state.items.filter { it !== hero }

    LaunchedEffect(gridState, state.hasMore, state.loading, state.loadingMore) {
        snapshotFlow {
            val info = gridState.layoutInfo
            (info.visibleItemsInfo.lastOrNull()?.index ?: 0) to info.totalItemsCount
        }.collect { (last, total) ->
            if (state.hasMore && !state.loading && !state.loadingMore && total > 0 && last >= total - 6) {
                viewModel.loadMore()
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = state.refreshing,
        onRefresh = { viewModel.refresh() },
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Adaptive(minSize = 118.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 14.dp + contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding() + 24.dp,
            ),
            horizontalArrangement = Arrangement.spacedBy(13.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            // ---- Hero / sorotan ----
            if (hero != null) {
                item(span = { GridItemSpan(maxLineSpan) }, key = "hero") {
                    HeroCard(
                        item = hero,
                        onClick = { onOpenItem(hero, viewModel.activeSourceId()) },
                    )
                }
            }

            // ---- Lanjutkan menonton ----
            if (history.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        SectionHeader("Lanjutkan Menonton", actionLabel = "Riwayat", onAction = onSeeHistory)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(history.take(8), key = { it.contentId }) { entry ->
                                ContinueCard(entry = entry, onClick = { onContinue(entry) })
                            }
                        }
                    }
                }
            }

            // ---- Kategori ----
            if (state.categories.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(state.categories, key = { it.id }) { category ->
                            FilterChipPill(
                                label = category.name,
                                selected = category.id == state.activeCategory?.id,
                                onClick = { viewModel.selectCategory(category) },
                            )
                        }
                    }
                }
            }

            // ---- Status ----
            when {
                state.loading && state.items.isEmpty() -> item(span = { GridItemSpan(maxLineSpan) }) {
                    LoadingGrid(modifier = Modifier.padding(top = 8.dp))
                }

                state.error != null && state.items.isEmpty() -> item(span = { GridItemSpan(maxLineSpan) }) {
                    FullScreenState(
                        icon = Icons.Filled.ErrorOutline,
                        title = "Ups, gagal memuat",
                        subtitle = state.error.orEmpty(),
                        actionLabel = "Coba Lagi",
                        onAction = { viewModel.retry() },
                    )
                }

                state.items.isEmpty() -> item(span = { GridItemSpan(maxLineSpan) }) {
                    FullScreenState(
                        icon = Icons.Filled.PlayArrow,
                        title = "Belum ada konten",
                        subtitle = "Kategori ini sedang kosong. Coba kategori lain atau ganti sumber video.",
                    )
                }

                else -> {
                    item(span = { GridItemSpan(maxLineSpan) }, key = "section") {
                        SectionHeader(state.activeCategory?.name ?: "Semua Konten")
                    }
                    items(
                        items = rest,
                        key = { it.safeId + (it.source ?: "") + it.hashCode() },
                    ) { item ->
                        VideoCard(
                            item = item,
                            onClick = { onOpenItem(item, viewModel.activeSourceId()) },
                        )
                    }
                }
            }

            // ---- Footer ----
            if (state.loadingMore) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        LinearProgressIndicator(
                            modifier = Modifier.width(140.dp),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            } else if (!state.hasMore && state.items.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = "Kamu sudah sampai konten terakhir.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
private fun ContinueCard(entry: HistoryEntity, onClick: () -> Unit) {
    Column(
        modifier = Modifier.width(132.dp).pressScale(onClick),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .softShadow(9.dp, RoundedCornerShape(18.dp))
                .clip(RoundedCornerShape(18.dp)),
        ) {
            PosterImage(url = entry.cover, modifier = Modifier.fillMaxSize())
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        androidx.compose.ui.graphics.Brush.verticalGradient(
                            listOf(
                                androidx.compose.ui.graphics.Color.Transparent,
                                androidx.compose.ui.graphics.Color(0xD904060F),
                            ),
                            startY = 140f,
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(brandBrush())
                    .padding(3.dp),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(999.dp))
                        .background(androidx.compose.ui.graphics.Color(0xCC05060F)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = "Lanjutkan",
                        tint = androidx.compose.ui.graphics.Color.White,
                        modifier = Modifier.size(22.dp),
                    )
                }
            }
            if (entry.episodeNumber != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(androidx.compose.ui.graphics.Color(0xB305060F))
                        .padding(horizontal = 7.dp, vertical = 3.dp),
                ) {
                    Text(
                        text = "EP ${entry.episodeNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        color = androidx.compose.ui.graphics.Color.White,
                    )
                }
            }
        }
        LinearProgressIndicator(
            progress = { entry.progress },
            modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(999.dp)),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.outline,
        )
        Text(
            text = entry.title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}


@Composable
private fun HeroCard(item: com.mikdadstream.app.data.remote.MediaItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(214.dp)
            .softShadow(14.dp, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
            .pressScale(onClick),
    ) {
        PosterImage(url = item.cover, modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    0f to Color(0x14000000),
                    0.45f to Color(0x5904060F),
                    1f to Color(0xF205070F),
                )
            )
        )
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.horizontalGradient(
                    0f to Color(0xE605070F),
                    0.7f to Color(0x3305070F),
                    1f to Color.Transparent,
                )
            )
        )

        // pil kategori atas kiri
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(14.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(Color(0xB305060F))
                .border(0.5.dp, Color.White.copy(alpha = 0.18f), RoundedCornerShape(999.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp),
        ) {
            Text(
                text = item.tag?.firstOrNull() ?: "Sorotan",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.95f),
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = item.safeTitle,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                val score = item.hotScore?.takeIf { it.isNotBlank() && !it.equals("N/A", true) }
                if (score != null && score.toFloatOrNull() != null) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color(0x33FFFFFF))
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFB020),
                            modifier = Modifier.size(12.dp),
                        )
                        Text(
                            text = String.format("%.1f", score.toFloat()),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                        )
                    }
                }
                item.source?.takeIf { it.isNotBlank() && it != "System" }?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.8f),
                    )
                }
                val ep = item.episodeCount ?: 0
                if (ep > 0) {
                    Text(
                        text = "$ep Episode",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.8f),
                    )
                }

                Spacer(Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(brandBrush())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(17.dp),
                    )
                    Text(
                        text = "Tonton",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                    )
                }
            }
        }
    }
}
