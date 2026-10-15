package com.mikdadstream.app.ui.components

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mikdadstream.app.data.local.Profile
import com.mikdadstream.app.data.remote.SourceModel
import com.mikdadstream.app.ui.theme.brandBrush

/**
 * Header atas: foto profil, kolom pencarian, dan tombol Sumber Video.
 */
@Composable
fun AppHeader(
    profile: Profile?,
    activeSource: SourceModel?,
    onProfileClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSourceClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = androidx.compose.ui.graphics.Color(0xFF1B1D26),
                    start = androidx.compose.ui.geometry.Offset(0f, size.height - strokeWidth),
                    end = androidx.compose.ui.geometry.Offset(size.width, size.height - strokeWidth),
                    strokeWidth = strokeWidth,
                )
            }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        // ---- Foto profil ----
        Box(modifier = Modifier.size(46.dp), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(brandBrush())
                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    .clickable(onClick = onProfileClick),
                contentAlignment = Alignment.Center,
            ) {
            val avatar = profile?.avatarUrl
            if (!avatar.isNullOrBlank()) {
                AsyncImage(
                    model = avatar,
                    contentDescription = "Foto profil",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(Color(0xFF5B3DF5), Color(0xFF9B5CFF)))),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = profile?.initials ?: "S",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            }
            // titik "online"
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF22D3EE))
                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
            )
        }

        // ---- Pencarian ----
        Row(
            modifier = Modifier
                .weight(1f)
                .height(44.dp)
                .shadow(4.dp, RoundedCornerShape(999.dp), ambientColor = Color.Black.copy(alpha = 0.35f))
                .clip(RoundedCornerShape(999.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.7f),
                    RoundedCornerShape(999.dp),
                )
                .clickable(onClick = onSearchClick)
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Cari",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp),
            )
            Text(
                text = "Cari film, anime, drama…",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        // ---- Tombol Sumber Video (dari API) ----
        Box(
            modifier = Modifier
                .height(44.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(MaterialTheme.colorScheme.primary),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(999.dp))
                    .clickable(onClick = onSourceClick)
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    Icons.Filled.Movie,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(17.dp),
                )
                Text(
                    text = friendlySourceName(activeSource?.safeName),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

/** Bottom sheet pemilihan sumber video dari API. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourceSheet(
    models: List<SourceModel>,
    activeId: String,
    loading: Boolean,
    error: String?,
    onSelect: (SourceModel) -> Unit,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    onHide: ((SourceModel) -> Unit)? = null,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 6.dp)
                    .size(width = 44.dp, height = 5.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(MaterialTheme.colorScheme.outline)
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .padding(bottom = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text = "Sumber Video",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "Dipilih langsung dari server",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            when {
                loading -> Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 140.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(28.dp), strokeWidth = 3.dp)
                }

                error != null -> FullScreenState(
                    icon = Icons.Outlined.CloudOff,
                    title = "Gagal memuat sumber",
                    subtitle = error,
                    actionLabel = "Coba Lagi",
                    onAction = onRetry,
                )

                models.isEmpty() -> FullScreenState(
                    icon = Icons.Outlined.Movie,
                    title = "Belum ada sumber",
                    subtitle = "API tidak mengembalikan daftar sumber video.",
                )

                else -> LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 440.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(models, key = { it.safeId }) { model ->
                        SourceRow(
                            model = model,
                            selected = model.safeId.equals(activeId, true),
                            onClick = { onSelect(model) },
                            onHide = if (onHide == null) null else ({ onHide(model) }),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SourceRow(
    model: SourceModel,
    selected: Boolean,
    onClick: () -> Unit,
    onHide: (() -> Unit)? = null,
) {
    val border = if (selected) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
            )
            .border(1.dp, border, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center,
        ) {
            if (!model.iconUrl.isNullOrBlank()) {
                AsyncImage(
                    model = model.iconUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    contentScale = ContentScale.Fit,
                )
            } else {
                Icon(
                    Icons.Outlined.Movie,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp),
            ) {
                Text(
                    text = model.safeName,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                StatusPill(status = model.status)
            }
            Text(
                text = model.description.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = model.typeLabel,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary,
                maxLines = 1,
            )
        }

        if (selected) {
            Icon(
                Icons.Filled.CheckCircle,
                contentDescription = "Aktif",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp),
            )
        } else if (!model.isActive) {
            Icon(
                Icons.Filled.ErrorOutline,
                contentDescription = model.status,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}

@Composable
private fun StatusPill(status: String?) {
    val active = status.equals("active", true)
    val color = when {
        active -> MaterialTheme.colorScheme.tertiary
        status.equals("maintenance", true) -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(color.copy(alpha = 0.16f))
            .padding(horizontal = 7.dp, vertical = 2.dp),
    ) {
        Text(
            text = when {
                active -> "Aktif"
                status.equals("maintenance", true) -> "Rawat Jalan"
                else -> status.orEmpty().ifBlank { "-" }
            },
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
        )
    }
}

/** Nama sumber ramah tampilan: jangan tampilkan alamat/domain API. */
private fun friendlySourceName(raw: String?): String {
    val v = raw?.trim().orEmpty()
    if (v.isBlank()) return "Sumber"
    val looksLikeAddress = v.contains("://") ||
        v.contains("www.") ||
        Regex("^[A-Za-z0-9.-]+\\.[A-Za-z]{2,}(/.*)?$").matches(v)
    return if (looksLikeAddress) "Server Utama" else v.take(14)
}
