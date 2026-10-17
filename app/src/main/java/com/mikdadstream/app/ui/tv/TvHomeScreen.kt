package com.mikdadstream.app.ui.tv

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.StarBorder
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
import com.mikdadstream.app.ui.components.LoadingGrid
import com.mikdadstream.app.ui.components.PosterImage
import com.mikdadstream.app.ui.home.HomeViewModel
import com.mikdadstream.app.ui.theme.LocalAccent
import kotlinx.coroutines.launch

private val TvBg = Color(0xFF0A0B10)
private val TvSurface = Color(0xFF161722)
private val TvStroke = Color(0xFF262839)
private val TvMuted = Color(0xFF9A9BB0)
private val TvHero = Color(0xFF2A1C3F)
private val TvTrack = Color(0xFF23243A)
private val TvStar = Color(0xFFFFC700)

fun Modifier.tvFocusable(shape: Shape, onClick: () -> Unit = {}): Modifier = composed {
    var focused by remember { mutableStateOf(false) }
    this.onFocusChanged { focused = it.isFocused }
        .border(if (focused) 3.dp else 0.dp, Color.White, shape)
        .clip(shape)
        .clickable(onClick = onClick)
}

@Composable
private fun TvTag(text: String, bg: Color, fg: Color) {
    Box(
        Modifier.clip(RoundedCornerShape(12.dp)).background(bg).padding(horizontal = 12.dp, vertical = 5.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = fg)
    }
}

@Composable
private fun TvHeaderSourceBar(
    activeSourceName: String?,
    onSourceClick: () -> Unit,
) {
    Row(
        Modifier
            .width(260.dp)
            .height(44.dp)
            .clip(CircleShape)
            .background(TvSurface)
            .border(1.dp, TvStroke, CircleShape)
            .clickable(onClick = onSourceClick)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = LocalAccent.current, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(8.dp))
        Text("Sumber Video", fontSize = 14.sp, color = TvMuted)
        Spacer(Modifier.width(8.dp))
        Text(
            activeSourceName?.takeIf { it.isNotBlank() } ?: "Server 1 HD",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Icon(Icons.Rounded.Settings, contentDescription = null, tint = TvMuted, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun TvSourceBar(modifier: Modifier = Modifier.fillMaxWidth()) {
    var open by remember { mutableStateOf(false) }
    Box(modifier) {
        Row(
            Modifier.fillMaxWidth().height(44.dp).clip(CircleShape).background(TvSurface)
                .border(1.dp, TvStroke, CircleShape).clickable { open = true }.padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.Info, null, tint = LocalAccent.current, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text("Sumber Video", fontSize = 14.sp, color = TvMuted)
            Spacer(Modifier.width(8.dp))
            Text("Server 1 HD", fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Icon(Icons.Rounded.Settings, null, tint = TvMuted, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun TvHomeScreen(
    viewModel: HomeViewModel,
    contentPadding: PaddingValues,
    profile: Profile?,
    initialFocus: FocusRequester,
    headerFocus: FocusRequester,
    currentRoute: String? = null,
    activeSourceName: String? = null,
    onSourceClick: () -> Unit = {},
    onOpenItem: (item: MediaItem, modelId: String?) -> Unit,
    onContinue: (item: HistoryEntity) -> Unit,
    onNavigate: (String) -> Unit = {},
    railFocusRequester: FocusRequester? = null,
    contentFocusRequester: FocusRequester? = null,
) {
    val state by viewModel.state.collectAsState()
    val history by viewModel.continueWatching.collectAsState()
    val hero = if (state.loading || state.error != null) null
    else state.items.firstOrNull { !it.isNews } ?: state.items.firstOrNull()
    val rest = state.items.filter { it !== hero }
    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState, state.hasMore, state.loading, state.loadingMore) {
        snapshotFlow { scrollState.value to scrollState.maxValue }
            .collect { (value, max) ->
                if (state.hasMore && !state.loading && !state.loadingMore && max > 0 && value >= max - 480) {
                    viewModel.loadMore()
                }
            }
    }

    LaunchedEffect(hero?.safeId, initialFocus) {
        if (hero == null) return@LaunchedEffect
        repeat(3) {
            runCatching { initialFocus.requestFocus() }
            kotlinx.coroutines.delay(90)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TvBg),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .statusBarsPadding()
                .padding(start = 66.dp, end = 16.dp, top = 12.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            TvHeaderSourceBar(
                activeSourceName = activeSourceName,
                onSourceClick = onSourceClick,
            )
        }
        TvSideRail(
            currentRoute = currentRoute,
            onNavigate = onNavigate,
            contentFocusRequester = contentFocusRequester,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(start = 66.dp)
                .padding(
                    top = 16.dp,
                    bottom = contentPadding.calculateBottomPadding() + 40.dp,
                )
                .then(
                    if (railFocusRequester != null) Modifier.focusProperties { left = railFocusRequester }
                    else Modifier
                ),
            verticalArrangement = Arrangement.spacedBy(28.dp),
        ) {
            Row(
                Modifier.fillMaxWidth().padding(start = 36.dp, end = 16.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(Modifier.weight(1f)) {
                    Text("Mikdad", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                    Text("Stream", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = LocalAccent.current)
                }
            }

            if (hero != null) {
                TvHero(
                    item = hero,
                    playFocusRequester = initialFocus,
                    onPlay = { onOpenItem(hero, viewModel.activeSourceId()) },
                    onInfo = { onOpenItem(hero, viewModel.activeSourceId()) },
                )
            }

            if (state.categories.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 36.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.offset(y = (-18).dp),
                ) {
                    items(state.categories, key = { it.id }) { category ->
                        TvCategoryPill(
                            label = category.name,
                            selected = category.id == state.activeCategory?.id,
                            onClick = { viewModel.selectCategory(category) },
                        )
                    }
                }
            }

            when {
                state.loading && state.items.isEmpty() -> {
                    LoadingGrid(modifier = Modifier.padding(horizontal = 36.dp))
                }

                state.error != null && state.items.isEmpty() -> {
                    TvMessage(
                        title = "Gagal memuat konten",
                        subtitle = state.error.orEmpty(),
                        actionLabel = "Coba Lagi",
                        onAction = { viewModel.retry() },
                    )
                }

                state.items.isEmpty() -> {
                    TvMessage(
                        title = "Belum ada konten",
                        subtitle = "Kategori ini kosong. Coba kategori atau sumber lain.",
                    )
                }

                else -> {
                    if (history.isNotEmpty()) {
                        TvRow(title = "Lanjutkan Menonton") {
                            items(history, key = { it.contentId }) { entry ->
                                TvContinueCard(entry) { onContinue(entry) }
                            }
                        }
                    }
                    TvRow(title = "Rekomendasi untukmu") {
                        items(rest, key = { it.safeId + (it.source ?: "") + it.hashCode() }) { item ->
                            TvPosterCard(item) { onOpenItem(item, viewModel.activeSourceId()) }
                        }
                    }
                    if (state.loadingMore) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            LinearProgressIndicator(
                                modifier = Modifier.width(160.dp),
                                color = LocalAccent.current,
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
    playFocusRequester: FocusRequester,
    onPlay: () -> Unit,
    onInfo: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(202.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(TvHero),
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
                        0.55f to TvBg.copy(alpha = 0.72f),
                        1f to Color.Transparent,
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to TvBg.copy(alpha = 0.35f),
                        0.35f to Color.Transparent,
                        1f to TvBg,
                    )
                )
        )
        Box(Modifier.align(Alignment.TopEnd)) {
            Box(
                Modifier.size(260.dp).clip(CircleShape).background(LocalAccent.current)
                    .offset(x = 40.dp, y = (-30).dp)
            )
        }
        Box(Modifier.align(Alignment.CenterEnd)) {
            Box(
                Modifier.size(112.dp).clip(CircleShape).background(Color(0xFFFFB84D))
                    .offset(x = (-181).dp, y = 0.dp)
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 28.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TvTag("TOP 1 HARI INI", Color(0xFFFFB84D), Color(0xFF14101C))
            Text(
                text = item.safeTitle,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 38.sp,
                lineHeight = 42.sp,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item.tag?.firstOrNull { it.isNotBlank() }?.uppercase() ?: "Serial · 2026 · Thriller · 8 Episode · 4K",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFF9A9BB0),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    Modifier.focusRequester(playFocusRequester).height(44.dp).tvFocusable(CircleShape) { onPlay() }
                        .background(LocalAccent.current).padding(horizontal = 28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = Color(0xFF14101C), modifier = Modifier.size(18.dp))
                        Text("Putar", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF14101C))
                    }
                }
                Box(
                    Modifier.height(44.dp).tvFocusable(CircleShape) { onInfo() }
                        .border(1.dp, Color(0xFF6B5A8A), CircleShape).padding(horizontal = 28.dp),
                    contentAlignment = Alignment.Center
                ) { Text("Detail", fontSize = 15.sp, fontWeight = FontWeight.Bold) }
                Box(
                    Modifier.height(44.dp).tvFocusable(CircleShape) {}
                        .border(1.dp, Color(0xFF6B5A8A), CircleShape).padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) { Text("+ Daftar", fontSize = 15.sp, fontWeight = FontWeight.Bold) }
            }
        }
    }
}

@Composable
private fun TvCategoryPill(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(if (selected) LocalAccent.current else TvSurface)
            .border(
                width = 1.dp,
                color = if (selected) Color.Transparent else TvStroke,
                shape = RoundedCornerShape(999.dp),
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 9.dp),
    ) {
        Text(
            text = label,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            fontSize = 15.sp,
            color = if (selected) Color.White else Color(0xFFD3D3DA),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun TvRow(
    title: String,
    content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit,
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 36.dp),
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            LazyRow(
                state = listState,
                contentPadding = PaddingValues(horizontal = 36.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
                content = content,
            )
            TvRowNext(
                onClick = {
                    scope.launch {
                        val next = (listState.firstVisibleItemIndex + 2)
                            .coerceAtMost((listState.layoutInfo.totalItemsCount - 1).coerceAtLeast(0))
                        listState.animateScrollToItem(next)
                    }
                },
                modifier = Modifier.align(Alignment.CenterEnd).padding(end = 44.dp),
            )
        }
    }
}

@Composable
private fun TvRowNext(onClick: () -> Unit, modifier: Modifier = Modifier) {
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.12f else 1f, tween(150), label = "rowNext")
    Box(
        modifier = modifier
            .size(56.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = if (focused) 1f else 0.9f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            Icons.AutoMirrored.Filled.NavigateNext,
            contentDescription = "Berikutnya",
            tint = Color(0xFF1A1A1F),
            modifier = Modifier.size(30.dp),
        )
    }
}

@Composable
private fun TvPosterCard(item: MediaItem, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.16f else 1f, tween(160), label = "poster")
    Column(
        modifier = Modifier
            .width(158.dp)
            .scale(scale)
            .onFocusChanged { focused = it.isFocused }
            .clip(RoundedCornerShape(if (focused) 18.dp else 14.dp))
            .clickable(onClick = onClick)
            .background(if (focused) TvSurface else Color.Transparent)
            .padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(if (focused) 16.dp else 12.dp))
                .then(
                    if (focused) Modifier.border(2.dp, LocalAccent.current, RoundedCornerShape(16.dp))
                    else Modifier
                ),
        ) {
            PosterImage(url = item.cover, modifier = Modifier.fillMaxSize())
        }
        if (focused) {
            Text(
                text = item.safeTitle,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        } else {
            Spacer(Modifier.height(14.dp))
        }
    }
}

@Composable
private fun TvContinueCard(entry: HistoryEntity, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.16f else 1f, tween(160), label = "cont")
    Box(
        modifier = Modifier
            .size(165.dp, 94.dp)
            .scale(scale)
            .tvFocusable(RoundedCornerShape(14.dp), onClick)
            .background(TvSurface),
    ) {
        PosterImage(url = entry.cover, modifier = Modifier.fillMaxSize())
        Box(Modifier.align(Alignment.BottomStart).fillMaxWidth().height(3.dp).background(Color(0x40000000)))
        Box(Modifier.align(Alignment.BottomStart).fillMaxWidth(entry.progress).height(3.dp).background(LocalAccent.current))
        Text(
            entry.title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.BottomStart).padding(start = 10.dp, bottom = 10.dp),
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
        modifier = Modifier.fillMaxWidth().padding(horizontal = 36.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.White,
        )
        Text(
            text = subtitle,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        if (actionLabel != null && onAction != null) {
            Box(
                Modifier.height(44.dp).tvFocusable(CircleShape) { onAction() }
                    .background(LocalAccent.current).padding(horizontal = 28.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(actionLabel, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF14101C))
            }
        }
    }
}

@Composable
private fun TvSideRail(
    currentRoute: String? = null,
    onNavigate: (String) -> Unit,
    contentFocusRequester: FocusRequester? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(66.dp)
            .background(Color.Transparent)
            .drawBehind {
                drawLine(
                    Color(0xFF1B1C28),
                    Offset(size.width, 0f),
                    Offset(size.width, size.height),
                    2f,
                )
            }
            .padding(vertical = 14.dp)
            .then(
                if (contentFocusRequester != null) Modifier.focusProperties { right = contentFocusRequester }
                else Modifier
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF241A3A))
                .border(2.dp, LocalAccent.current, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "M",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
            )
        }
        Spacer(Modifier.height(26.dp))
        val items = listOf(
            Triple(Icons.Rounded.Search, "Cari", "search"),
            Triple(Icons.Rounded.Home, "Home", "home"),
            Triple(Icons.Rounded.AutoAwesome, "Rekomendasi", "recommend"),
            Triple(Icons.Rounded.Download, "Download", "downloads"),
            Triple(Icons.Rounded.History, "History", "history"),
        )
        items.forEach { (icon, label, route) ->
            val sel = currentRoute == route
            Box(
                Modifier.padding(vertical = 8.dp).size(44.dp).tvFocusable(CircleShape) { onNavigate(route) }
                    .background(if (sel) LocalAccent.current else Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = label,
                    tint = if (sel) Color(0xFF14101C) else TvMuted,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Box(
            Modifier.size(44.dp).tvFocusable(CircleShape) { onNavigate("settings") },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.Settings, contentDescription = "Pengaturan", tint = TvMuted, modifier = Modifier.size(24.dp))
        }
    }
}
