package com.mikdadstream.app.ui.recommend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikdadstream.app.data.local.HistoryEntity
import com.mikdadstream.app.data.remote.MediaItem
import com.mikdadstream.app.ui.components.FilterChipPill
import com.mikdadstream.app.ui.components.PosterImage
import com.mikdadstream.app.ui.components.SectionHeader
import com.mikdadstream.app.ui.components.VideoCard
import com.mikdadstream.app.ui.theme.brandBrush
import com.mikdadstream.app.ui.theme.StreamLine
import com.mikdadstream.app.ui.theme.StreamSurface
import com.mikdadstream.app.ui.theme.StreamTextPrimary
import com.mikdadstream.app.ui.tv.isTelevision

/** Kolom filter mood — persis PDF hal 2 (Semua/Santai/Seru/Misteri). */
private val Moods = listOf("Semua", "Santai", "Seru", "Misteri")

private fun moodIndex(title: String): Int {
    var h = 0
    for (ch in title) h = (h * 31 + ch.code) and Int.MAX_VALUE
    return h % 3 // 0=Santai, 1=Seru, 2=Misteri
}

/**
 * Layar Rekomendasi — layout persis folder Theme/PDF:
 * chips mood → hero "PILIHAN UTAMA" → "Karena kamu menonton X" → "Seru akhir pekan ini".
 * Data tetap dari API asli.
 */
@Composable
fun RecommendScreen(
    items: List<MediaItem>,
    history: List<HistoryEntity>,
    activeSourceName: String?,
    onSourceClick: () -> Unit,
    onOpenItem: (MediaItem) -> Unit,
    contentPadding: androidx.compose.foundation.layout.PaddingValues = androidx.compose.foundation.layout.PaddingValues(
        0.dp,
    ),
) {
    var mood by remember { mutableStateOf("Semua") }
    val accent = MaterialTheme.colorScheme.primary
    val onBg = MaterialTheme.colorScheme.onBackground
    val muted = MaterialTheme.colorScheme.onSurfaceVariant

    fun assign(): Pair<List<MediaItem>, List<MediaItem>> {
        val picked = items.filter { !it.isNews }.let {
            if (mood == "Semua") it else it.filter { m -> moodIndex(m.safeTitle) == Moods.indexOf(mood) - 1 }
        }
        val hero = picked.firstOrNull() ?: items.firstOrNull()
        val because = items.filter { it !== hero }.shuffled()
        val weekend = items.filter { it !== hero }.reversed()
        return because to weekend
    }
    val (becauseItems, weekendItems) = remember(items, mood) { assign() }
    val hero = items.firstOrNull() { m -> !m.isNews } ?: items.firstOrNull()
    val becauseTitle = history.firstOrNull()?.title ?: hero?.safeTitle

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = contentPadding.calculateTopPadding()),
    ) {
        if (!androidx.compose.ui.platform.LocalContext.current.isTelevision()) {
            // ---- Header ringkas: avatar + brand + chip sumber (persis PDF hal 1-2) ----
            HeaderBar(
                activeSourceName = activeSourceName,
                onSourceClick = onSourceClick,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = 4.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(Icons.Rounded.AutoAwesome, null, tint = accent, modifier = Modifier.size(20.dp))
                Text("Pilihan untukmu", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = onBg)
            }
            Text(
                "Dipilih khusus dari tontonanmu",
                fontSize = 12.sp,
                color = muted,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Moods.forEach { m ->
                    FilterChipPill(
                        label = m,
                        selected = mood == m,
                        onClick = { mood = m },
                    )
                }
            }

            // ---- Hero 160dp — persis mockup (lingkaran aksen pojok kanan atas, tombol Putar) ----
            if (hero != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(brandBrush())
                        .clickable { onOpenItem(hero) },
                ) {
                    PosterImage(
                        url = hero.cover,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(200.dp)
                            .align(Alignment.CenterEnd),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 50.dp, y = (-50).dp)
                            .size(150.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)),
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                            .fillMaxWidth(0.66f),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Text(
                            text = hero.safeTitle,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = StreamTextPrimary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .padding(horizontal = 12.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Icon(
                                Icons.Filled.PlayArrow,
                                null,
                                tint = accent,
                                modifier = Modifier.size(14.dp),
                            )
                            Text(
                                text = "Putar",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = accent,
                            )
                        }
                    }
                }
            }

            // ---- Karena kamu menonton X ----
            if (becauseItems.isNotEmpty()) {
                SectionHeader(
                    "Karena kamu menonton ${becauseTitle ?: "judul lain"}",
                    actionLabel = "Lihat semua",
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(becauseItems, key = { "because" + it.safeId }) { item ->
                        Box(modifier = Modifier.width(120.dp)) {
                            VideoCard(item = item, onClick = { onOpenItem(item) })
                        }
                    }
                }
            }

            // ---- Seru akhir pekan ini ----
            if (weekendItems.isNotEmpty()) {
                SectionHeader("Seru akhir pekan ini", actionLabel = "Lihat semua")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(weekendItems, key = { "weekend" + it.safeId }) { item ->
                        Box(modifier = Modifier.width(120.dp)) {
                            VideoCard(item = item, onClick = { onOpenItem(item) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderBar(
    activeSourceName: String?,
    onSourceClick: () -> Unit,
) {
    val accent = MaterialTheme.colorScheme.primary
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(2.dp, accent, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "M",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        Row {
            Text(text = "Akasha", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Text(text = "Stream", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = accent)
        }
        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(StreamSurface)
                .border(1.dp, StreamLine, RoundedCornerShape(999.dp))
                .clickable(onClick = onSourceClick)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = "Sumber Video",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = activeSourceName?.trim()?.takeIf { it.isNotBlank() } ?: "Server",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Icon(
                Icons.Filled.ExpandMore,
                null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}
