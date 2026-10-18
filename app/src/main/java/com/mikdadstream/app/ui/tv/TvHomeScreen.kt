package com.mikdadstream.app.ui.tv

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mikdadstream.app.data.local.HistoryEntity
import com.mikdadstream.app.data.local.Profile
import com.mikdadstream.app.data.remote.MediaItem
import com.mikdadstream.app.ui.components.LoadingGrid
import com.mikdadstream.app.ui.components.PosterImage
import com.mikdadstream.app.data.repository.CategoryBundle
import com.mikdadstream.app.ui.home.HomeViewModel

// ───────── Warna (sesuai Theme/MainActivity.kt) ─────────
private val Bg = Color(0xFF0B0B10)
private val SidebarBg = Color.Transparent
private val Line = Color(0xFF1A1A22)
private val Pink = Color(0xFFFF4D6D)
private val Yellow = Color(0xFFFFB84D)
private val Teal = Color(0xFF2DD4BF)
private val Purple = Color(0xFF8B7BFF)
private val Green = Color(0xFF4ADE80)
private val HeroBg = Color(0xFF2B1D40)
private val PillBg = Color(0xFF14141C)
private val Muted = Color(0xFFB4AAC4)
private val OutlineBtn = Color(0xFF5A4A70)

// ───────── Skala desain 1280 x 720 ─────────
@Composable
fun DesignScale(designWidthDp: Float = 1280f, content: @Composable () -> Unit) {
    val cfg = LocalConfiguration.current
    val d = LocalDensity.current
    val screenWidthPx = cfg.screenWidthDp * d.density
    CompositionLocalProvider(
        LocalDensity provides Density(density = screenWidthPx / designWidthDp, fontScale = 1f),
        content = content
    )
}

// ───────── Data poster ─────────
private data class Poster(val title: String, val cover: String?, val bg: Color, val progress: Float? = null)

// ───────── Screen ─────────
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
) {
    val state by viewModel.state.collectAsState()
    val history by viewModel.continueWatching.collectAsState()
    val hero = if (state.loading || state.error != null) null
    else state.items.firstOrNull { !it.isNews } ?: state.items.firstOrNull()
    val rest = state.items.filter { it !== hero }.take(12)

    val continuePosters = history.map { entry ->
        Poster(entry.title, entry.cover, Color(0xFF1E3A5F), entry.progress)
    }
    val recoPosters = rest.map { item ->
        Poster(item.safeTitle, item.cover, Color(0xFF2B5F45), null)
    }

    DesignScale {
        Row(Modifier.fillMaxSize().background(Bg)) {
            Sidebar(currentRoute = currentRoute, onNavigate = onNavigate)
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = contentPadding.calculateBottomPadding() + 40.dp),
            ) {
                Spacer(Modifier.height(22.dp))
                Header(activeSourceName = activeSourceName, onSourceClick = onSourceClick)
                Spacer(Modifier.height(16.dp))

                when {
                    state.loading && state.items.isEmpty() -> {
                        LoadingGrid(modifier = Modifier.padding(horizontal = 48.dp))
                    }
                    state.error != null && state.items.isEmpty() -> {
                        Text(
                            text = state.error.orEmpty(),
                            color = Muted,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 48.dp, top = 24.dp),
                        )
                    }
                    state.items.isEmpty() -> {
                        Text(
                            text = "Belum ada konten",
                            color = Muted,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 48.dp, top = 24.dp),
                        )
                    }
                    else -> {
                        LaunchedEffect(hero?.safeId) {
                            runCatching { initialFocus.requestFocus() }
                        }
                        Hero(
                            item = hero,
                            profile = profile,
                            playFocus = initialFocus,
                            onPlay = { hero?.let { onOpenItem(it, viewModel.activeSourceId()) } },
                            onInfo = { hero?.let { onOpenItem(it, viewModel.activeSourceId()) } },
                        )
                        Spacer(Modifier.height(20.dp))

                        // ---- Kategori chips (plus "Semua Film" untuk moviebox) ----
                        if (state.categories.isNotEmpty()) {
                            CategoryChipsRow(
                                sourceModelId = viewModel.activeSourceId(),
                                categories = state.categories,
                                activeCategory = state.activeCategory,
                                onCategorySelected = { cat ->
                                    if (cat == null) {
                                        viewModel.showAllFilms()
                                    } else {
                                        viewModel.selectCategory(cat)
                                    }
                                }
                            )
                            Spacer(Modifier.height(18.dp))
                        }

                        // ---- Section konten sesuai kategori aktif ----
                        val activeCategoryName = state.activeCategory?.name ?: "Semua Film"
                        CategoryContentSection(
                            items = state.items,
                            categoryName = activeCategoryName,
                            onItemClick = { onOpenItem(it, viewModel.activeSourceId()) }
                        )
                        Spacer(Modifier.height(19.dp))

                        // ---- Lanjutkan Menonton ----
                        if (continuePosters.isNotEmpty()) {
                            SectionTitle("Lanjutkan Menonton")
                            Spacer(Modifier.height(13.dp))
                            PosterRow(continuePosters, showTitle = true) { entry ->
                                history.firstOrNull { it.contentId == entry.title }?.let { onContinue(it) }
                            }
                            Spacer(Modifier.height(19.dp))
                        }

                        Spacer(Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

// ───────── Sidebar (lebar 87) ─────────
@Composable
private fun Sidebar(currentRoute: String? = null, onNavigate: (String) -> Unit) {
    Row(Modifier.width(87.dp).fillMaxHeight()) {
        Column(
            Modifier.width(86.dp).fillMaxHeight().background(SidebarBg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(22.dp))
            Box(
                Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2A1A2E))
                    .border(2.dp, Pink, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("M", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(27.dp))
            NavItem(Icons.Outlined.Search, currentRoute == "search") { onNavigate("search") }
            Spacer(Modifier.height(12.dp))
            NavItem(Icons.Filled.Home, currentRoute == "home") { onNavigate("home") }
            Spacer(Modifier.height(12.dp))
            NavItem(Icons.Outlined.StarBorder, currentRoute == "recommend") { onNavigate("recommend") }
            Spacer(Modifier.height(12.dp))
            NavItem(Icons.Outlined.FileDownload, currentRoute == "downloads") { onNavigate("downloads") }
            Spacer(Modifier.height(12.dp))
            NavItem(Icons.Outlined.History, currentRoute == "history") { onNavigate("history") }
            Spacer(Modifier.weight(1f))
            NavItem(Icons.Outlined.Tune, currentRoute == "settings") { onNavigate("settings") }
            Spacer(Modifier.height(25.dp))
        }
        Box(Modifier.width(1.dp).fillMaxHeight().background(Line))
    }
}

@Composable
private fun NavItem(icon: ImageVector, active: Boolean, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.05f else 1f, tween(120), label = "navScale")
    Box(
        Modifier
            .size(56.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(if (active) Pink else Color.Transparent)
            .border(if (focused) 3.dp else 0.dp, Color.White, CircleShape)
            .onFocusChanged { focused = it.isFocused }
            .focusable()
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, null, tint = if (active) Color.White else Color.White.copy(alpha = 0.8f), modifier = Modifier.size(24.dp))
    }
}

// ───────── Header ─────────
@Composable
private fun Header(activeSourceName: String? = null, onSourceClick: () -> Unit = {}) {
    Row(
        Modifier.fillMaxWidth().height(57.dp).padding(start = 48.dp, end = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text("Mikdad", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
            Text("Stream", color = Pink, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
        }
        Spacer(Modifier.weight(1f))
        var focused by remember { mutableStateOf(false) }
        Row(
            Modifier
                .width(389.dp)
                .height(57.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(PillBg)
                .border(
                    if (focused) 3.dp else 1.dp,
                    if (focused) Color.White else Color(0xFF2A2A35),
                    RoundedCornerShape(28.dp)
                )
                .onFocusChanged { focused = it.isFocused }
                .focusable()
                .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onSourceClick)
                .padding(horizontal = 26.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.PlayCircleOutline, null, tint = Pink, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(16.dp))
            Text("Sumber Video", color = Muted, fontSize = 20.sp)
            Spacer(Modifier.width(13.dp))
            Text(activeSourceName?.takeIf { it.isNotBlank() } ?: "Server 1 HD", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Filled.KeyboardArrowDown, null, tint = Muted, modifier = Modifier.size(24.dp))
        }
    }
}

// ───────── Hero ─────────
@Composable
private fun Hero(
    item: MediaItem?,
    profile: Profile?,
    playFocus: FocusRequester,
    onPlay: () -> Unit,
    onInfo: () -> Unit,
) {
    Box(
        Modifier
            .padding(start = 48.dp)
            .fillMaxWidth()
            .height(270.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(HeroBg)
    ) {
        Box(
            Modifier
                .align(Alignment.TopStart)
                .offset(x = 825.dp, y = (-50).dp)
                .size(380.dp)
                .clip(CircleShape)
                .background(Pink)
        )
        Box(
            Modifier
                .align(Alignment.TopStart)
                .offset(x = 755.dp, y = 100.dp)
                .size(150.dp)
                .clip(CircleShape)
                .background(Yellow)
        )

        Column(Modifier.padding(start = 36.dp, top = 53.dp)) {
            Box(
                Modifier
                    .height(22.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(Yellow)
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("TOP 1 HARI INI", color = Color(0xFF2B1D40), fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = item?.safeTitle ?: "Neon Harbor",
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item?.tag?.firstOrNull() ?: "Serial · 2026 · Thriller · 8 Episode · 4K",
                color = Muted,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(11.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                HeroButton("▶ Putar", filled = true, width = 148.dp, modifier = Modifier.focusRequester(playFocus), onClick = onPlay)
                HeroButton("Detail", filled = false, width = 128.dp, onClick = onInfo)
                HeroButton("+ Daftar", filled = false, width = 146.dp, onClick = onInfo)
            }
        }
    }
}

@Composable
private fun HeroButton(text: String, filled: Boolean, width: androidx.compose.ui.unit.Dp, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    var focused by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(29.dp)
    Box(
        modifier
            .width(width)
            .height(58.dp)
            .clip(shape)
            .background(if (filled) Pink else Color.Transparent)
            .border(
                BorderStroke(
                    if (focused) 3.dp else 2.dp,
                    if (focused) Color.White else if (filled) Pink else OutlineBtn
                ),
                shape
            )
            .onFocusChanged { focused = it.isFocused }
            .focusable()
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

// ───────── Baris poster ─────────
@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 48.dp).height(30.dp)
    )
}

// ───────── Kategori chips (mirip HP) ─────────
@Composable
private fun CategoryChipsRow(
    sourceModelId: String,
    categories: List<CategoryBundle>,
    activeCategory: CategoryBundle?,
    onCategorySelected: (CategoryBundle?) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(start = 48.dp, end = 48.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.heightIn(max = 80.dp)
    ) {
        // Chip "Semua Film" hanya tampil untuk sumber moviebox
        if (sourceModelId == "moviebox") {
            item {
                val isActive = activeCategory == null
                var focused by remember { mutableStateOf(false) }
                val chipModifier = Modifier
                    .width(160.dp)
                    .height(52.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .background(
                        if (isActive) Pink
                        else if (focused) Color.White.copy(alpha = 0.12f)
                        else Color.Transparent
                    )
                    .border(
                        BorderStroke(
                            if (focused) 3.dp else if (isActive) 2.dp else 1.dp,
                            if (focused) Color.White else if (isActive) Pink else Color.White.copy(alpha = 0.4f)
                        ),
                        RoundedCornerShape(26.dp)
                    )
                    .onFocusChanged { focused = it.isFocused }
                    .focusable()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onCategorySelected(null) }
                    )
                Box(modifier = chipModifier, contentAlignment = Alignment.Center) {
                    Text(
                        text = "Semua Film",
                        color = if (isActive) Color.White else Color.White.copy(alpha = 0.85f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            item(key = "spacer") { Spacer(modifier = Modifier.width(12.dp)) }
        }
        items(categories, key = { it.id }) { category ->
            val isActive = category.id == activeCategory?.id
            var focused by remember { mutableStateOf(false) }
            val chipModifier = Modifier
                .width(160.dp)
                .height(52.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(
                    if (isActive) Pink
                    else if (focused) Color.White.copy(alpha = 0.12f)
                    else Color.Transparent
                )
                .border(
                    BorderStroke(
                        if (focused) 3.dp else if (isActive) 2.dp else 1.dp,
                        if (focused) Color.White else if (isActive) Pink else Color.White.copy(alpha = 0.4f)
                    ),
                    RoundedCornerShape(26.dp)
                )
                .onFocusChanged { focused = it.isFocused }
                .focusable()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onCategorySelected(category) }
                )
            Box(modifier = chipModifier, contentAlignment = Alignment.Center) {
                Text(
                    text = category.name,
                    color = if (isActive) Color.White else Color.White.copy(alpha = 0.85f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

// ───────── Grid non-lazy (aman di dalam Column.verticalScroll) ─────────
private val TvGridPadding = PaddingValues(start = 48.dp, end = 48.dp, top = 4.dp, bottom = 24.dp)

@Composable
private fun <T> PosterGrid(
    items: List<T>,
    columns: Int,
    horizontalSpacing: Dp,
    verticalSpacing: Dp,
    contentPadding: PaddingValues = TvGridPadding,
    itemContent: @Composable (T) -> Unit,
) {
    val direction = LocalLayoutDirection.current
    Column(
        Modifier
            .fillMaxWidth()
            .padding(
                start = contentPadding.calculateStartPadding(direction),
                end = contentPadding.calculateEndPadding(direction),
                top = contentPadding.calculateTopPadding(),
                bottom = contentPadding.calculateBottomPadding(),
            )
    ) {
        items.chunked(columns).forEachIndexed { index, rowItems ->
            if (index > 0) Spacer(Modifier.height(verticalSpacing))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)
            ) {
                rowItems.forEach { item ->
                    Box(Modifier.weight(1f)) { itemContent(item) }
                }
                repeat(columns - rowItems.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

// ───────── Section judul + daftar konten (7 kolom, ke bawah seperti HP) ─────────
@Composable
private fun CategoryContentSection(
    items: List<MediaItem>,
    categoryName: String,
    onItemClick: (MediaItem) -> Unit,
) {
    Column {
        Text(
            text = categoryName,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 48.dp, top = 22.dp, bottom = 14.dp)
        )
        val displayItems = items.take(70)
        if (displayItems.isEmpty()) {
            Text(
                text = "Belum ada konten",
                color = Muted,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 48.dp, top = 8.dp)
            )
        } else {
            PosterGrid(
                items = displayItems,
                columns = 7,
                horizontalSpacing = 14.dp,
                verticalSpacing = 18.dp
            ) { item ->
                val poster = Poster(
                    title = item.safeTitle,
                    cover = item.cover,
                    bg = Color(0xFF2B5F45),
                    progress = null
                )
                PosterCard(poster, showTitle = true) { onItemClick(item) }
            }
        }
    }
}

@Composable
private fun PosterRow(items: List<Poster>, showTitle: Boolean, onItemClick: (Poster) -> Unit = { _ -> }) {
    PosterGrid(
        items = items,
        columns = 4,
        horizontalSpacing = 14.dp,
        verticalSpacing = 16.dp
    ) { PosterCard(it, showTitle) { onItemClick(it) } }
}

@Composable
private fun PosterCard(p: Poster, showTitle: Boolean, onClick: () -> Unit = {}) {
    var focused by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(8.dp)
    Box(
        Modifier
            .aspectRatio(2f / 3f)
            .clip(shape)
            .background(p.bg)
            .onFocusChanged { focused = it.isFocused }
            .focusable()
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick)
    ) {
        if (p.cover != null) {
            PosterImage(url = p.cover, modifier = Modifier.fillMaxSize())
        }
        if (showTitle) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color(0xCC000000))
            )
            Text(
                p.title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.BottomStart).padding(start = 10.dp, bottom = 6.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        p.progress?.let {
            Box(
                Modifier.align(Alignment.BottomStart).fillMaxWidth().height(4.dp)
                    .background(Color(0x40000000))
            ) {
                Box(Modifier.fillMaxHeight().fillMaxWidth(it).background(Pink))
            }
        }
        if (focused) {
            Box(Modifier.fillMaxSize().border(3.dp, Color.White, shape))
        }
    }
}