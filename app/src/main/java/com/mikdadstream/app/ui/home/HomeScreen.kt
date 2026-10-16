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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
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
import com.mikdadstream.app.ui.i18n.LocalStrings
import com.mikdadstream.app.ui.theme.StreamPrimarySoft

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    contentPadding: PaddingValues,
    profile: Profile?,
    accountBadge: String = "Normal",
    isPremium: Boolean = false,
    onProfileClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onOpenItem: (item: MediaItem, modelId: String?) -> Unit,
    onContinue: (item: HistoryEntity) -> Unit,
    onSeeHistory: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val history by viewModel.continueWatching.collectAsState()
    val s = LocalStrings.current
    val gridState = rememberLazyGridState()
    val heroes = if (state.loading || state.error != null) emptyList()
    else state.items.filter { !it.isNews }.ifEmpty { state.items }.take(6)
    val rest = state.items.filter { item -> heroes.none { it === item } }

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
                    accountBadge = accountBadge,
                    isPremium = isPremium,
                    onProfileClick = onProfileClick,
                    onSearchClick = onSearchClick,
                    onNotificationClick = onNotificationClick,
                )
            }

            // ---- Hero / sorotan ----
            if (heroes.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }, key = "hero") {
                    HeroCarousel(
                        items = heroes,
                        onOpen = { onOpenItem(it, viewModel.activeSourceId()) },
                    )
                }
            }

            // ---- Lanjutkan menonton ----
            if (history.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        SectionHeader(s.continueWatching, actionLabel = s.seeAll, onAction = onSeeHistory)
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
                        text = s.endOfContent,
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
    accountBadge: String,
    isPremium: Boolean,
    onProfileClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
) {
    val ls = LocalStrings.current
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(modifier = Modifier.size(50.dp)) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .align(Alignment.TopStart)
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
            if (isPremium) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(2.dp, MaterialTheme.colorScheme.background, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Premium",
                        tint = Color.White,
                        modifier = Modifier.size(10.dp),
                    )
                }
            }
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = "${ls.greetingPrefix} ${profile?.displayName?.substringBefore(" ")?.ifBlank { null } ?: ls.greetingFallback}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false),
                )
                AccountBadge(label = accountBadge, highlighted = isPremium)
            }
            Text(
                text = ls.greetingSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        IconHeaderButton(Icons.Outlined.Search, onClick = onSearchClick)
        IconHeaderButton(Icons.Outlined.Notifications, showDot = true, onClick = onNotificationClick)
    }
}

@Composable
private fun AccountBadge(label: String, highlighted: Boolean) {
    val bg = if (highlighted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val fg = if (highlighted) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 2.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = fg,
            fontWeight = FontWeight.SemiBold,
        )
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
    val ls = LocalStrings.current
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, Color(0xFF6BC1F5))))
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
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = ls.heroPopular,
                    style = MaterialTheme.typography.bodyMedium,
                    color = StreamPrimarySoft,
                )
                Text(
                    text = item.safeTitle,
                    style = MaterialTheme.typography.headlineSmall.copy(fontFamily = PlusJakarta, fontSize = 17.sp, lineHeight = 22.sp),
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
                        .softShadow(6.dp, RoundedCornerShape(999.dp))
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
                        text = ls.watchNow,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

@Composable
private fun HeroCarousel(items: List<MediaItem>, onOpen: (MediaItem) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { items.size })
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            pageSpacing = 10.dp,
        ) { page ->
            HeroBanner(item = items[page], onClick = { onOpen(items[page]) })
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(items.size) { index ->
                val selected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(width = if (selected) 22.dp else 6.dp, height = 6.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(
                            if (selected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outline
                        )
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
