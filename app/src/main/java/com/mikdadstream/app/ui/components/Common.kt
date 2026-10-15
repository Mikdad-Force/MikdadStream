package com.mikdadstream.app.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mikdadstream.app.data.remote.MediaItem
import com.mikdadstream.app.ui.theme.shimmerBrush

/** Placeholder berdenyut (shimmer sederhana). */
@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(14.dp),
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val alpha by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "shimmerAlpha",
    )
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha),
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha * 0.6f),
                    )
                )
            )
    )
}

@Composable
fun PosterImage(
    url: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        contentScale = contentScale,
    )
}

/** Kartu poster film / serial / anime. */
@Composable
fun VideoCard(
    item: MediaItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badge: String? = null,
    badgeColor: Color = MaterialTheme.colorScheme.secondary,
    selected: Boolean = false,
) {
    Column(
        modifier = modifier.pressScale(onClick),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .softShadow(9.dp, RoundedCornerShape(18.dp))
                .clip(RoundedCornerShape(18.dp))
                .border(
                    width = 1.dp,
                    color = if (selected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.45f),
                    shape = RoundedCornerShape(18.dp),
                )
        ) {
            PosterImage(url = item.cover, modifier = Modifier.fillMaxSize())
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.55f to Color(0x14000000),
                            1f to Color(0xE604060F),
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0x33FFFFFF), Color.Transparent)
                        )
                    )
            )

            val score = item.hotScore?.takeIf { it.isNotBlank() && !it.equals("N/A", true) }
            if (score != null) {
                val isNumeric = score.toFloatOrNull() != null
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(
                            if (isNumeric) Color(0xD905060F) else badgeColor.copy(alpha = 0.94f)
                        )
                        .border(
                            0.5.dp,
                            Color.White.copy(alpha = 0.18f),
                            RoundedCornerShape(999.dp),
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    if (isNumeric) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFB020),
                            modifier = Modifier.size(11.dp),
                        )
                    }
                    Text(
                        text = if (isNumeric) String.format("%.1f", score.toFloat()) else score,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isNumeric) Color.White else MaterialTheme.colorScheme.onSecondary,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            val tagText = badge ?: item.tag?.firstOrNull()
            if (!tagText.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xCC05060F))
                        .border(0.5.dp, Color.White.copy(alpha = 0.16f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = tagText,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.92f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }

        Text(
            text = item.safeTitle,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 18.sp,
        )

        val meta = buildList {
            item.source?.takeIf { it.isNotBlank() && it != "System" }?.let(::add)
            val ep = item.episodeCount ?: 0
            if (ep > 0) add("$ep Episode")
        }.joinToString(" • ")

        if (meta.isNotBlank()) {
            Text(
                text = meta,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(18.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                        )
                    )
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        if (actionLabel != null && onAction != null) {
            Text(
                text = actionLabel,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onAction)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            )
        }
    }
}

/** Chip kategori / filter. */
@Composable
fun FilterChipPill(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val background by androidx.compose.animation.animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = androidx.compose.animation.core.tween(220),
        label = "chipBg",
    )
    val foreground by androidx.compose.animation.animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = androidx.compose.animation.core.tween(220),
        label = "chipFg",
    )
    val borderColor by androidx.compose.animation.animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.outline.copy(alpha = 0.7f),
        animationSpec = androidx.compose.animation.core.tween(220),
        label = "chipBorder",
    )
    Box(
        modifier = modifier
            .graphicsLayer {
                val s = if (selected) 1.02f else 1f
                scaleX = s
                scaleY = s
            }
            .clip(RoundedCornerShape(999.dp))
            .background(background)
            .border(1.dp, borderColor, RoundedCornerShape(999.dp))
            .clickable(interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }, indication = androidx.compose.material3.ripple(), onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 9.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = foreground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/** Status layar penuh (kosong / galat). */
@Composable
fun FullScreenState(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.18f),
                        )
                    )
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(34.dp),
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        if (actionLabel != null && onAction != null) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = onAction)
                    .padding(horizontal = 22.dp, vertical = 11.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(18.dp),
                )
                Text(
                    text = actionLabel,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

/** Grid kerangka saat memuat. */
@Composable
fun LoadingGrid(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        repeat(2) { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                repeat(3) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        ShimmerBox(modifier = Modifier.fillMaxWidth().aspectRatio(2f / 3f))
                        ShimmerBox(
                            modifier = Modifier.fillMaxWidth().height(14.dp),
                            shape = RoundedCornerShape(6.dp),
                        )
                        ShimmerBox(
                            modifier = Modifier.fillMaxWidth(0.6f).height(10.dp),
                            shape = RoundedCornerShape(6.dp),
                        )
                    }
                }
            }
            if (row == 0) Spacer(Modifier.height(4.dp))
        }
    }
}
