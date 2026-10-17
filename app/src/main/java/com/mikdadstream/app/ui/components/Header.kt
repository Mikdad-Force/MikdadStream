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
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mikdadstream.app.data.local.Profile
import com.mikdadstream.app.data.remote.SourceModel
import com.mikdadstream.app.ui.theme.LocalAccent
import com.mikdadstream.app.ui.tv.TvRequestFocusOnShow

@Composable
fun AppHeader(
    profile: Profile?,
    activeSource: SourceModel?,
    onProfileClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSourceClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF241A3A))
                    .border(2.dp, LocalAccent.current, CircleShape)
                    .clickable(onClick = onProfileClick),
                contentAlignment = Alignment.Center,
            ) {
                val avatar = profile?.avatarUrl
                if (!avatar.isNullOrBlank()) {
                    AsyncImage(
                        model = avatar,
                        contentDescription = "Foto profil",
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Text(
                        text = "M",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text("Halo, Mikdad", fontSize = 12.sp, color = Color(0xFF9A9BB0))
                Row {
                    Text("Mikdad", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                    Text("Stream", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = LocalAccent.current)
                }
            }
            HeaderCircleBtn(Icons.Outlined.Search) { onSearchClick() }
            Spacer(Modifier.width(8.dp))
            HeaderCircleBtn(Icons.Outlined.Tune) { onSourceClick() }
        }
        SourceBar()
    }
}

@Composable
private fun HeaderCircleBtn(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color(0xFF161722))
            .border(1.dp, Color(0xFF262839), CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
    }
}

@Composable
private fun SourceBar(modifier: Modifier = Modifier.fillMaxWidth().padding(horizontal = 0.dp)) {
    var open by remember { mutableStateOf(false) }
    Box(modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(CircleShape)
                .background(Color(0xFF161722))
                .border(1.dp, Color(0xFF262839), CircleShape)
                .clickable { open = true }
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Filled.Movie, contentDescription = null, tint = LocalAccent.current, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text("Sumber Video", fontSize = 14.sp, color = Color(0xFF9A9BB0))
            Spacer(Modifier.width(8.dp))
            Text(
                "Server 1 HD",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
            Icon(Icons.Filled.ExpandMore, contentDescription = null, tint = Color(0xFF9A9BB0))
        }
    }
}

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
    val sheetFocus = remember { androidx.compose.ui.focus.FocusRequester() }
    TvRequestFocusOnShow(sheetFocus, models.isNotEmpty())
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
                    modifier = Modifier.fillMaxWidth().height(140.dp),
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
                    modifier = Modifier.fillMaxWidth().height(440.dp),
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
    val border = if (selected) LocalAccent.current
    else MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                if (selected) LocalAccent.current.copy(alpha = 0.12f)
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
                    tint = LocalAccent.current,
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
                tint = LocalAccent.current,
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
