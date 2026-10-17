package com.mikdadstream.app.ui.tv

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.mikdadstream.app.ui.components.LoadingGrid
import com.mikdadstream.app.ui.components.PosterImage
import com.mikdadstream.app.ui.home.HomeViewModel
import com.mikdadstream.app.ui.theme.Outfit
import com.mikdadstream.app.ui.theme.Oswald
import com.mikdadstream.app.ui.theme.Poppins
import com.mikdadstream.app.ui.theme.StreamPrimarySoft
import kotlinx.coroutines.launch

private val TvBg = Color(0xFF0A0B12)
private val TvCardBg = Color(0xFF14151F)
private val TvAccent = Color(0xFFFA4776)
private val TvStar = Color(0xFFFFC700)
private val TvPanel = Color(0xFF676571).copy(alpha = 0.34f)

@Composable
fun TvTopBar(
    activeSourceName: String?,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onSourceClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(listOf(Color(0xCC0A0B12), TvBg.copy(alpha = 0f)))
            )
            .padding(horizontal = 40.dp, vertical = 18.dp),
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(34.dp),
        ) {
            TvTopLink("Beranda", currentRoute == "home") { onNavigate("home") }
            TvTopLink("Cari", currentRoute == "search") { onNavigate("search") }
            TvTopLink("Koleksi", currentRoute == "library") { onNavigate("library") }
        }

        Text(
            text = "MIKDAD",
            fontFamily = Oswald,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            letterSpacing = 3.sp,
            color = TvAccent,
            modifier = Modifier.align(Alignment.Center),
        )

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            TvTopIcon(Icons.Filled.Search, "Cari") { onNavigate("search") }
            TvTopLink(
                text = activeSourceName?.takeIf { it.isNotBlank() } ?: "Sumber",
                selected = false,
                onClick = onSourceClick,
            )
        }
    }
}

@Composable
private fun TvTopLink(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.08f else 1f, tween(140), label = "topLink")
    Text(
        text = text,
        fontFamily = Outfit,
        fontWeight = if (selected || focused) FontWeight.SemiBold else FontWeight.Medium,
        fontSize = 20.sp,
        color = if (selected || focused) Color.White else Color(0xFFD3D3DA),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .scale(scale)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp),
    )
}

@Composable
private fun TvTopIcon(icon: ImageVector, label: String, onClick: () -> Unit) {
    Icon(
        icon,
        contentDescription = label,
        tint = Color.White,
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(8.dp)
            .size(24.dp),
    )
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
            .background(if (selected) TvAccent else Color.White.copy(alpha = 0.06f))
            .border(
                width = 1.dp,
                color = if (selected) Color.Transparent else Color.White.copy(alpha = 0.22f),
                shape = RoundedCornerShape(999.dp),
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 9.dp),
    ) {
        Text(
            text = label,
            fontFamily = Outfit,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            fontSize = 15.sp,
            color = if (selected) Color.White else Color(0xFFD3D3DA),
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
    initialFocus: FocusRequester,
    onOpenItem: (item: MediaItem, modelId: String?) -> Unit,
    onContinue: (item: HistoryEntity) -> Unit,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding() + 40.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(28.dp),
        ) {
            if (hero != null) {
                TvHero(
                    item = hero,
                    profile = profile,
                    playFocusRequester = initialFocus,
                    onPlay = { onOpenItem(hero, viewModel.activeSourceId()) },
                    onInfo = { onOpenItem(hero, viewModel.activeSourceId()) },
                )
            }

            if (state.categories.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 40.dp),
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
                    LoadingGrid(modifier = Modifier.padding(horizontal = 40.dp))
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
                    TvRow(title = "Film Populer") {
                        items(rest.take(14), key = { "rec" + it.safeId + it.hashCode() }) { item ->
                            TvPosterCard(item) { onOpenItem(item, viewModel.activeSourceId()) }
                        }
                    }
                    TvRow(title = state.activeCategory?.name ?: "Semua Konten") {
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
                                color = TvAccent,
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
    playFocusRequester: FocusRequester,
    onPlay: () -> Unit,
    onInfo: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(430.dp),
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
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 48.dp, end = 40.dp)
                .fillMaxWidth(0.52f),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Text(
                text = item.safeTitle,
                fontFamily = Oswald,
                fontWeight = FontWeight.Bold,
                fontSize = 56.sp,
                lineHeight = 60.sp,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            val subtitle = item.tag?.firstOrNull { it.isNotBlank() }
            if (subtitle != null) {
                Text(
                    text = subtitle.uppercase(),
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    lineHeight = 34.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Text(
                text = item.desc?.takeIf { it.isNotBlank() }
                    ?: "Streaming film & series favoritmu dalam kualitas terbaik.",
                fontFamily = Outfit,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                lineHeight = 27.sp,
                color = Color(0xFFE4E4EA),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            item.hotScore?.toDoubleOrNull()?.let { TvStarRating(it) }
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                TvHeroButton(
                    text = "Putar",
                    icon = Icons.Filled.PlayArrow,
                    primary = true,
                    onClick = onPlay,
                    modifier = Modifier.focusRequester(playFocusRequester),
                )
                TvHeroButton("Info", Icons.Outlined.Info, primary = false, onClick = onInfo)
            }
        }
        Text(
            text = "Selamat datang, ${profile?.displayName?.substringBefore(" ")?.ifBlank { null } ?: "Sobat"}",
            fontFamily = Outfit,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = Color.White.copy(alpha = 0.85f),
            modifier = Modifier.align(Alignment.TopStart).padding(start = 48.dp, top = 6.dp),
        )
    }
}

@Composable
private fun TvStarRating(score: Double) {
    val rating = (if (score > 5.0) score / 2.0 else score).coerceIn(0.0, 5.0)
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(5) { index ->
            val filled = index < rating
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = if (filled) TvStar else Color.White.copy(alpha = 0.35f),
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Composable
private fun TvHeroButton(
    text: String,
    icon: ImageVector,
    primary: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.06f else 1f, tween(150), label = "heroBtn")
    val bg = if (primary) TvAccent else Color.Transparent
    val fg = Color.White
    Row(
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(999.dp))
            .background(bg)
            .border(
                width = if (primary) 0.dp else 2.dp,
                color = if (primary) Color.Transparent else Color.White.copy(alpha = 0.75f),
                shape = RoundedCornerShape(999.dp),
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 26.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(icon, contentDescription = null, tint = fg, modifier = Modifier.size(22.dp))
        Text(
            text = text,
            fontFamily = Outfit,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = fg,
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
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 40.dp),
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 34.dp)
                    .background(TvPanel, RoundedCornerShape(24.dp))
                    .padding(vertical = 16.dp),
            ) {
                LazyRow(
                    state = listState,
                    contentPadding = PaddingValues(horizontal = 22.dp),
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                    content = content,
                )
            }
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
            .background(if (focused) TvCardBg else Color.Transparent)
            .padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(if (focused) 16.dp else 12.dp))
                .then(
                    if (focused) Modifier.border(2.dp, TvAccent, RoundedCornerShape(16.dp))
                    else Modifier
                ),
        ) {
            PosterImage(url = item.cover, modifier = Modifier.fillMaxSize())
        }
        if (focused) {
            Text(
                text = item.safeTitle,
                fontFamily = Outfit,
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
    Column(
        modifier = Modifier
            .width(214.dp)
            .scale(scale)
            .onFocusChanged { focused = it.isFocused }
            .clip(RoundedCornerShape(if (focused) 18.dp else 14.dp))
            .clickable(onClick = onClick)
            .background(if (focused) TvCardBg else Color.Transparent)
            .padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(if (focused) 16.dp else 12.dp))
                .then(
                    if (focused) Modifier.border(2.dp, TvAccent, RoundedCornerShape(16.dp))
                    else Modifier
                ),
        ) {
            PosterImage(url = entry.cover, modifier = Modifier.fillMaxSize())
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color(0xCC02050F)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = "Lanjutkan",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp),
                )
            }
            LinearProgressIndicator(
                progress = { entry.progress },
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(4.dp),
                color = TvAccent,
                trackColor = Color(0x66000000),
            )
        }
        if (focused) {
            Text(
                text = entry.title,
                fontFamily = Outfit,
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
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.White,
        )
        Text(
            text = subtitle,
            fontFamily = Outfit,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = StreamPrimarySoft,
        )
        if (actionLabel != null && onAction != null) {
            TvHeroButton(actionLabel, Icons.Outlined.Refresh, primary = true, onClick = onAction)
        }
    }
}
