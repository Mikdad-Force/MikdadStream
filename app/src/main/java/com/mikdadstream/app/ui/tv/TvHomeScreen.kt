package com.mikdadstream.app.ui.tv

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.mikdadstream.app.ui.components.LoadingGrid
import com.mikdadstream.app.ui.components.PosterImage
import com.mikdadstream.app.ui.home.HomeViewModel
import com.mikdadstream.app.ui.theme.PlusJakarta
import com.mikdadstream.app.ui.theme.StreamPrimarySoft

private val TvBg = Color(0xFF0A0B12)
private val TvCardBg = Color(0xFF14151F)

@Composable
fun TvTopBar(
    profile: Profile?,
    activeSourceName: String?,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onSourceClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(listOf(TvBg, TvBg.copy(alpha = 0.0f)))
            )
            .padding(horizontal = 32.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = "MIKDAD",
            style = MaterialTheme.typography.titleLarge.copy(fontFamily = PlusJakarta, fontSize = 22.sp),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold,
        )
        TvNavItem("Beranda", Icons.Filled.Home, currentRoute == "home") { onNavigate("home") }
        TvNavItem("Cari", Icons.Filled.Search, currentRoute == "search") { onNavigate("search") }
        TvNavItem("Koleksi", Icons.Outlined.BookmarkBorder, currentRoute == "library") { onNavigate("library") }
        TvNavItem("Profil", Icons.Filled.Person, currentRoute == "settings") { onProfileClick() }
        Spacer(Modifier.weight(1f))
        TvNavItem("Sumber", Icons.Outlined.Refresh, false, label = activeSourceName ?: "Sumber") { onSourceClick() }
    }
}

@Composable
private fun TvNavItem(
    text: String,
    icon: ImageVector,
    selected: Boolean,
    label: String? = null,
    onClick: () -> Unit,
) {
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.06f else 1f, tween(150), label = "navScale")
    val color = when {
        focused -> Color.White
        selected -> Color.White
        else -> Color(0xFFB9BAC4)
    }
    Row(
        modifier = Modifier
            .scale(scale)
            .clip(RoundedCornerShape(10.dp))
            .background(if (focused) Color.White.copy(alpha = 0.14f) else Color.Transparent)
            .onFocusChanged { focused = it.isFocused }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
        Text(
            text = label ?: text,
            style = MaterialTheme.typography.titleSmall,
            color = color,
            fontWeight = if (selected || focused) FontWeight.Bold else FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun TvHomeScreen(
    viewModel: HomeViewModel,
    contentPadding: PaddingValues,
    profile: Profile?,
    onOpenItem: (item: MediaItem, modelId: String?) -> Unit,
    onContinue: (item: HistoryEntity) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val history by viewModel.continueWatching.collectAsState()
    val hero = if (state.loading || state.error != null) null
    else state.items.firstOrNull { !it.isNews } ?: state.items.firstOrNull()
    val rest = state.items.filter { it !== hero }
    val listState = rememberLazyListState()

    LaunchedEffect(listState, state.hasMore, state.loading, state.loadingMore) {
        snapshotFlow {
            val info = listState.layoutInfo
            (info.visibleItemsInfo.lastOrNull()?.index ?: 0) to info.totalItemsCount
        }.collect { (last, total) ->
            if (state.hasMore && !state.loading && !state.loadingMore && total > 0 && last >= total - 3) {
                viewModel.loadMore()
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(TvBg),
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding(),
            bottom = contentPadding.calculateBottomPadding() + 40.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(28.dp),
    ) {
        if (hero != null) {
            item(key = "hero") {
                TvHero(
                    item = hero,
                    profile = profile,
                    onPlay = { onOpenItem(hero, viewModel.activeSourceId()) },
                    onInfo = { onOpenItem(hero, viewModel.activeSourceId()) },
                )
            }
        }

        if (state.categories.isNotEmpty()) {
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
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

        when {
            state.loading && state.items.isEmpty() -> item {
                LoadingGrid(modifier = Modifier.padding(horizontal = 40.dp))
            }

            state.error != null && state.items.isEmpty() -> item {
                TvMessage(
                    title = "Gagal memuat konten",
                    subtitle = state.error.orEmpty(),
                    actionLabel = "Coba Lagi",
                    onAction = { viewModel.retry() },
                )
            }

            state.items.isEmpty() -> item {
                TvMessage(
                    title = "Belum ada konten",
                    subtitle = "Kategori ini kosong. Coba kategori atau sumber lain.",
                )
            }

            else -> {
                if (history.isNotEmpty()) {
                    item {
                        TvRow("Lanjutkan Menonton") {
                            items(history, key = { it.contentId }) { entry ->
                                TvContinueCard(entry) { onContinue(entry) }
                            }
                        }
                    }
                }
                item {
                    TvRow("Rekomendasi untukmu") {
                        items(rest.take(14), key = { "rec" + it.safeId + it.hashCode() }) { item ->
                            TvPosterCard(item) { onOpenItem(item, viewModel.activeSourceId()) }
                        }
                    }
                }
                item {
                    TvRow(state.activeCategory?.name ?: "Semua Konten") {
                        items(rest, key = { it.safeId + (it.source ?: "") + it.hashCode() }) { item ->
                            TvPosterCard(item) { onOpenItem(item, viewModel.activeSourceId()) }
                        }
                    }
                }
                if (state.loadingMore) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            LinearProgressIndicator(
                                modifier = Modifier.width(160.dp),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TvHero(
    item: MediaItem,
    profile: Profile?,
    onPlay: () -> Unit,
    onInfo: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp),
    ) {
        AsyncImage(
            model = item.cover,
            contentDescription = item.safeTitle,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        0f to TvBg,
                        0.5f to TvBg.copy(alpha = 0.75f),
                        1f to Color.Transparent,
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        1f to TvBg,
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 40.dp, end = 40.dp)
                .fillMaxWidth(0.55f),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Text(
                text = "MIKDAD STREAM",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp,
            )
            Text(
                text = item.safeTitle,
                style = MaterialTheme.typography.displaySmall.copy(fontFamily = PlusJakarta, fontSize = 40.sp),
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item.desc?.takeIf { it.isNotBlank() }
                    ?: "Streaming film & series favoritmu dalam kualitas terbaik.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFD5D6DE),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                TvHeroButton("Putar", Icons.Filled.PlayArrow, primary = true, onClick = onPlay)
                TvHeroButton("Info", Icons.Outlined.Info, primary = false, onClick = onInfo)
            }
        }
        Text(
            text = "Selamat datang, ${profile?.displayName?.substringBefore(" ")?.ifBlank { null } ?: "Sobat"}",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White.copy(alpha = 0.85f),
            modifier = Modifier.align(Alignment.TopStart).padding(start = 40.dp, top = 8.dp),
        )
    }
}

@Composable
private fun TvHeroButton(
    text: String,
    icon: ImageVector,
    primary: Boolean,
    onClick: () -> Unit,
) {
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.06f else 1f, tween(150), label = "heroBtn")
    val bg = if (primary) Color.White else Color(0x40FFFFFF)
    val fg = if (primary) Color(0xFF02050F) else Color.White
    Row(
        modifier = Modifier
            .scale(scale)
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .then(
                if (focused) Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                else Modifier
            )
            .onFocusChanged { focused = it.isFocused }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 26.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(icon, contentDescription = null, tint = fg, modifier = Modifier.size(22.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = fg,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun TvRow(
    title: String,
    content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontFamily = PlusJakarta),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 40.dp),
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            content = content,
        )
    }
}

@Composable
private fun TvPosterCard(item: MediaItem, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.09f else 1f, tween(150), label = "poster")
    Column(
        modifier = Modifier
            .width(156.dp)
            .scale(scale)
            .onFocusChanged { focused = it.isFocused }
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .background(if (focused) TvCardBg else Color.Transparent)
            .padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(8.dp))
                .then(
                    if (focused) Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                    else Modifier
                ),
        ) {
            PosterImage(url = item.cover, modifier = Modifier.fillMaxSize())
        }
        Text(
            text = item.safeTitle,
            style = MaterialTheme.typography.titleSmall,
            color = if (focused) Color.White else Color(0xFFC9CAD3),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun TvContinueCard(entry: HistoryEntity, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.09f else 1f, tween(150), label = "cont")
    Column(
        modifier = Modifier
            .width(210.dp)
            .scale(scale)
            .onFocusChanged { focused = it.isFocused }
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .background(if (focused) TvCardBg else Color.Transparent)
            .padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(8.dp))
                .then(
                    if (focused) Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                    else Modifier
                ),
        ) {
            PosterImage(url = entry.cover, modifier = Modifier.fillMaxSize())
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xCC02050F)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = "Lanjutkan",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }
            LinearProgressIndicator(
                progress = { entry.progress },
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(4.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color(0x66000000),
            )
        }
        Text(
            text = entry.title,
            style = MaterialTheme.typography.titleSmall,
            color = if (focused) Color.White else Color(0xFFC9CAD3),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun TvMessage(
    title: String,
    subtitle: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = StreamPrimarySoft,
        )
        if (actionLabel != null && onAction != null) {
            TvHeroButton(actionLabel, Icons.Outlined.Refresh, primary = true, onClick = onAction)
        }
    }
}
