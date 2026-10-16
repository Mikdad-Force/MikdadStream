package com.mikdadstream.app.ui.home

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mikdadstream.app.data.local.HistoryEntity
import com.mikdadstream.app.data.local.Profile
import com.mikdadstream.app.data.remote.MediaItem
import com.mikdadstream.app.ui.components.FilterChipPill
import com.mikdadstream.app.ui.components.FullScreenState
import com.mikdadstream.app.ui.components.LoadingGrid
import com.mikdadstream.app.ui.components.PosterImage
import com.mikdadstream.app.ui.components.SectionHeader
import com.mikdadstream.app.ui.components.VideoCard
import com.mikdadstream.app.ui.components.pressScale
import com.mikdadstream.app.ui.components.softShadow
import com.mikdadstream.app.ui.theme.PlusJakarta
import com.mikdadstream.app.ui.theme.StreamPrimarySoft

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    contentPadding: PaddingValues,
    profile: Profile?,
    onProfileClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onLibraryClick: () -> Unit,
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
                start = 20.dp,
                end = 20.dp,
                top = 8.dp + contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding() + 24.dp,
            ),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxSize().statusBarsPadding(),
        ) {
            // ---- Greeting header ----
            item(span = { GridItemSpan(maxLineSpan) }, key = "greeting") {
                GreetingHeader(
                    profile = profile,
                    onProfileClick = onProfileClick,
                    onSearchClick = onSearchClick,
                    onNotificationClick = onNotificationClick,
                    onLibraryClick = onLibraryClick,
                )
            }

            // ---- Hero / sorotan ----
            if (hero != null) {
                item(span = { GridItemSpan(maxLineSpan) }, key = "hero") {
                    HeroBanner(
                        item = hero,
                        onClick = { onOpenItem(hero, viewModel.activeSourceId()) },
                    )
                }
            }

            // ---- Lanjutkan menonton ----
            if (history.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        SectionHeader("Lanjutkan Menonton", actionLabel = "Lihat Semua", onAction = onSeeHistory)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
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
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        SectionHeader("Kategori")
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
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
            }

            // ---- Status ----
            when {
                state.loading && state.items.isEmpty() -> item(span = { GridItemSpan(maxLineSpan) }) {
                    LoadingGrid(modifier = Modifier.padding(top = 8.dp))
                }

                state.error != null && state.items.isEmpty() -> item(span = { GridItemSpan(maxLineSpan) }) {
                    FullScreenState(
                        icon = Icons.Outlined.ErrorOutline,
                        title = "Ups, gagal memuat",
                        subtitle = state.error.orEmpty(),
                        actionLabel = "Coba Lagi",
                        onAction = { viewModel.retry() },
                    )
                }

                state.items.isEmpty() -> item(span = { GridItemSpan(maxLineSpan) }) {
                    FullScreenState(
                        icon = Icons.Outlined.ErrorOutline,
                        title = "Belum ada konten",
                        subtitle = "Kategori ini sedang kosong. Coba kategori lain atau ganti sumber video.",
                    )
                }

                else -> {
                    if (rest.isNotEmpty()) {
                        item(span = { GridItemSpan(maxLineSpan) }, key = "recommended") {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                SectionHeader("Rekomendasi untukmu", actionLabel = "Lihat Semua")
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    items(rest.take(8), key = { "rec" + it.safeId + it.hashCode() }) { item ->
                                        Box(modifier = Modifier.width(120.dp)) {
                                            VideoCard(
                                                item = item,
                                                onClick = { onOpenItem(item, viewModel.activeSourceId()) },
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
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
                        style = MaterialTheme.typography.bodyMedium,
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
private fun GreetingHeader(
    profile: Profile?,
    onProfileClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onLibraryClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable(onClick = onProfileClick),
            contentAlignment = Alignment.Center,
        ) {
            val avatar = profile?.avatarUrl
            if (!avatar.isNullOrBlank()) {
                AsyncImage(
                    model = avatar,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Text(
                    text = profile?.initials ?: "S",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = "Hi, ${profile?.displayName?.substringBefore(" ")?.ifBlank { null } ?: "Sobat"}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = "Let's watch a movie",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        IconHeaderButton(Icons.Outlined.BookmarkBorder, onClick = onLibraryClick)
        IconHeaderButton(Icons.Outlined.Search, onClick = onSearchClick)
        IconHeaderButton(Icons.Outlined.Notifications, showDot = true, onClick = onNotificationClick)
    }
}

@Composable
private fun IconHeaderButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    showDot: Boolean = false,
    onClick: () -> Unit,
) {
    Box(contentAlignment = Alignment.TopEnd) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        if (showDot) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error),
            )
        }
    }
}

@Composable
private fun HeroBanner(item: MediaItem, onClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, Color(0xFF8A7BFF))))
                .pressScale(onClick),
        ) {
            PosterImage(
                url = item.cover,
                modifier = Modifier.fillMaxSize().align(Alignment.CenterEnd).width(230.dp),
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            0f to MaterialTheme.colorScheme.primary,
                            0.55f to MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
                            1f to Color.Transparent,
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
                    .fillMaxWidth(0.62f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "Watch popular movies",
                    style = MaterialTheme.typography.bodyMedium,
                    color = StreamPrimarySoft,
                )
                Text(
                    text = item.safeTitle,
                    style = MaterialTheme.typography.headlineSmall.copy(fontFamily = PlusJakarta, fontSize = 18.sp),
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = item.desc?.takeIf { it.isNotBlank() } ?: "Tonton sekarang di Mikdad Stream",
                    style = MaterialTheme.typography.bodyMedium,
                    color = StreamPrimarySoft,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp),
                    )
                    Text(
                        text = "Tonton",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(width = 22.dp, height = 6.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.width(6.dp))
            repeat(2) {
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.outline)
                )
            }
        }
    }
}

@Composable
private fun ContinueCard(entry: HistoryEntity, onClick: () -> Unit) {
    Column(
        modifier = Modifier.width(120.dp).pressScale(onClick),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .softShadow(8.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp)),
        ) {
            PosterImage(url = entry.cover, modifier = Modifier.fillMaxSize())
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color(0xD902050F)),
                            startY = 140f,
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color(0xCC02050F)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = "Lanjutkan",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp),
                )
            }
            if (entry.episodeNumber != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xCC02050F))
                        .padding(horizontal = 7.dp, vertical = 3.dp),
                ) {
                    Text(
                        text = "EP ${entry.episodeNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                    )
                }
            }
        }
        LinearProgressIndicator(
            progress = { entry.progress },
            modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(999.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.outline,
        )
        Text(
            text = entry.title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
