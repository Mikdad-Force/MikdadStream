@file:OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.mikdadstream.app.ui.kit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.outlined.Cast
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.DevicesOther
import androidx.compose.material.icons.outlined.DownloadForOffline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FamilyRestroom
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.LockReset
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.PlaylistPlay
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mikdadstream.app.ui.components.FilterChipPill
import com.mikdadstream.app.ui.components.StreamCard
import com.mikdadstream.app.ui.components.StreamIconCircle
import com.mikdadstream.app.ui.components.StreamNavRow
import com.mikdadstream.app.ui.components.StreamPrimaryButton
import com.mikdadstream.app.ui.components.StreamScreenHeader
import com.mikdadstream.app.ui.components.StreamSectionLabel
import com.mikdadstream.app.ui.components.StreamTextField

// ---------- Sampel data ----------

private val likedSample = listOf(
    "Interstellar" to "Sci-Fi · Petualangan",
    "Midsommar" to "Horror · Drama",
    "Brave" to "Animasi · Keluarga",
    "Nobody" to "Aksi · Thriller",
    "Parasite" to "Thriller · Drama",
)

private val topSearches = listOf(
    "Dune: Part Two" to "Sci-Fi",
    "Oppenheimer" to "Biografi",
    "Poor Things" to "Fantasi",
    "Anyone But You" to "Romansa",
    "Godzilla x Kong" to "Aksi",
    "The Batman" to "Kriminal",
)

private val seasonEpisodes = listOf(
    "E1 The Vanishing of Will Byers" to "49 menit",
    "E2 The Weirdo on Maple Street" to "48 menit",
    "E3 Holly, Jolly" to "52 menit",
    "E4 The Body" to "51 menit",
    "E5 The Flea and the Acrobat" to "47 menit",
)

private val popularChips = listOf("Dune", "Oppenheimer", "Anime", "Serial", "Horror", "Aksi")

private val designKitScreens = listOf(
    "Onboarding" to Icons.Outlined.PlayCircleOutline,
    "Masuk" to Icons.Outlined.Login,
    "Daftar" to Icons.Outlined.PersonAdd,
    "OTP" to Icons.Outlined.Password,
    "Lupa Sandi" to Icons.Outlined.LockReset,
    "Langganan" to Icons.Outlined.WorkspacePremium,
    "Tambah Kartu" to Icons.Outlined.CreditCard,
    "Pembayaran" to Icons.Outlined.Payments,
    "Film Disukai" to Icons.Outlined.FavoriteBorder,
    "Pencarian Populer" to Icons.Outlined.LocalFireDepartment,
    "Unduhan" to Icons.Outlined.DownloadForOffline,
    "Musim" to Icons.Outlined.PlaylistPlay,
    "Watch on Device" to Icons.Outlined.Cast,
    "Ubah Profil" to Icons.Outlined.Edit,
    "Ubah Sandi" to Icons.Outlined.Password,
    "Bahasa" to Icons.Outlined.Language,
    "Notifikasi" to Icons.Outlined.Notifications,
    "Keamanan" to Icons.Outlined.Security,
    "Bantuan" to Icons.Outlined.HelpOutline,
    "Legal" to Icons.Outlined.Gavel,
    "Keluar" to Icons.Outlined.Logout,
    "Kategori Favorit" to Icons.Outlined.Category,
    "Berbagi Keluarga" to Icons.Outlined.FamilyRestroom,
    "Putar Film" to Icons.Filled.PlayArrow,
)

// ---------- Helper ----------

@Composable
private fun KitRoot(content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        content()
    }
}

@Composable
private fun ThumbBox(widthDp: Int, heightDp: Int, icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(widthDp.dp, heightDp.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp),
        )
    }
}

// ---------- 1. LikedMoviesScreen ----------

@Composable
fun LikedMoviesScreen(onBack: () -> Unit, onOpen: (String) -> Unit) {
    KitRoot {
        StreamScreenHeader(title = "Film Disukai", onBack = onBack)
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(likedSample) { (title, genre) ->
                StreamCard(modifier = Modifier.clickable { onOpen(title) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        ThumbBox(widthDp = 120, heightDp = 72, icon = Icons.Outlined.Movie)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = genre,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        StreamIconCircle(
                            icon = Icons.Outlined.ChevronRight,
                            onClick = { onOpen(title) },
                            size = 36,
                        )
                    }
                }
            }
        }
    }
}

// ---------- 2. WatchOnDeviceScreen ----------

@Composable
fun WatchOnDeviceScreen(onBack: () -> Unit) {
    KitRoot {
        StreamScreenHeader(title = "Tonton di Perangkat", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            StreamCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    StreamIconCircle(icon = Icons.Outlined.Cast, onClick = {}, size = 64)
                    Text(
                        text = "Sambungkan ke TV",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = "Putar film di layar besar dengan sekali ketuk.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                    StreamPrimaryButton(text = "Hubungkan", onClick = {})
                }
            }

            StreamSectionLabel(text = "Perangkat Tersedia")

            StreamCard {
                Column {
                    StreamNavRow(
                        icon = Icons.Outlined.Tv,
                        title = "TV Samsung",
                        subtitle = "Terhubung sebelumnya",
                        onClick = {},
                    )
                    StreamNavRow(
                        icon = Icons.Outlined.DevicesOther,
                        title = "Chromecast",
                        subtitle = "Tersedia di jaringan ini",
                        onClick = {},
                    )
                }
            }
        }
    }
}

// ---------- 3. DownloadEmptyScreen ----------

@Composable
fun DownloadEmptyScreen(onBack: () -> Unit) {
    KitRoot {
        StreamScreenHeader(title = "Unduhan", onBack = onBack)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                StreamIconCircle(
                    icon = Icons.Outlined.CloudDownload,
                    onClick = {},
                    size = 88,
                    background = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "Belum ada unduhan",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Film yang kamu unduh akan muncul di sini.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(4.dp))
                StreamPrimaryButton(text = "Jelajahi Film", onClick = {})
            }
        }
    }
}

// ---------- 4. TopSearchesScreen ----------

@Composable
fun TopSearchesScreen(onBack: () -> Unit, onOpen: (String) -> Unit) {
    KitRoot {
        StreamScreenHeader(title = "Pencarian Populer", onBack = onBack)
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(topSearches) { pair ->
                val rank = topSearches.indexOf(pair) + 1
                val (title, genre) = pair
                StreamCard(modifier = Modifier.clickable { onOpen(title) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            text = "$rank",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.width(28.dp),
                            textAlign = TextAlign.Center,
                        )
                        ThumbBox(widthDp = 64, heightDp = 64, icon = Icons.Outlined.Movie)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = genre,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }
    }
}

// ---------- 5. SearchNotFoundScreen ----------

@Composable
fun SearchNotFoundScreen(onBack: () -> Unit) {
    var query by remember { mutableStateOf("") }
    var selectedChip by remember { mutableStateOf<String?>(null) }

    KitRoot {
        StreamScreenHeader(title = "Pencarian", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp),
        ) {
            StreamTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = "Cari film…",
                leadingIcon = Icons.Outlined.Search,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    StreamIconCircle(
                        icon = Icons.Outlined.SearchOff,
                        onClick = {},
                        size = 88,
                    )
                    Text(
                        text = "Tidak ditemukan",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = "Coba kata kunci lain atau jelajahi pencarian populer.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Pencarian Populer",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        popularChips.forEach { chip ->
                            FilterChipPill(
                                label = chip,
                                selected = selectedChip == chip,
                                onClick = { selectedChip = if (selectedChip == chip) null else chip },
                            )
                        }
                    }
                }
            }
        }
    }
}

// ---------- 6. SeasonScreen ----------

@Composable
fun SeasonScreen(onBack: () -> Unit, onOpen: (String) -> Unit) {
    KitRoot {
        StreamScreenHeader(
            title = "Musim",
            onBack = onBack,
            trailing = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "Musim 1",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp),
                    )
                }
            },
        )
        StreamSectionLabel(text = "5 Episode")
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(seasonEpisodes) { (title, duration) ->
                StreamCard(modifier = Modifier.clickable { onOpen(title) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        ThumbBox(widthDp = 120, heightDp = 72, icon = Icons.Filled.PlayArrow)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = duration,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        StreamIconCircle(
                            icon = Icons.Filled.PlayArrow,
                            onClick = { onOpen(title) },
                            size = 40,
                        )
                    }
                }
            }
        }
    }
}

// ---------- 7. MoviePlayScreen ----------

@Composable
fun MoviePlayScreen(onBack: () -> Unit, title: String) {
    var selectedOption by remember { mutableStateOf(0) }
    val options = listOf("Sumber", "Episode", "Kecepatan", "Subtitle")

    KitRoot {
        StreamScreenHeader(
            title = title,
            onBack = onBack,
            trailing = {
                Icon(
                    Icons.Outlined.Cast,
                    contentDescription = "Cast",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(22.dp),
                )
            },
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center,
            ) {
                StreamIconCircle(icon = Icons.Filled.PlayArrow, onClick = {}, size = 64)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "24:09",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                LinearProgressIndicator(
                    progress = { 0.28f },
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(999.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.outline,
                )
                Text(
                    text = "-1:02:16",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                StreamIconCircle(
                    icon = Icons.Filled.Replay10,
                    onClick = {},
                    size = 52,
                )
                Spacer(Modifier.width(28.dp))
                StreamIconCircle(
                    icon = Icons.Filled.Pause,
                    onClick = {},
                    size = 64,
                )
                Spacer(Modifier.width(28.dp))
                StreamIconCircle(
                    icon = Icons.Filled.Forward10,
                    onClick = {},
                    size = 52,
                )
            }

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                options.forEachIndexed { index, option ->
                    FilterChipPill(
                        label = option,
                        selected = selectedOption == index,
                        onClick = { selectedOption = index },
                    )
                }
            }
        }
    }
}

// ---------- 8. DesignKitScreen ----------

@Composable
fun DesignKitScreen(onBack: () -> Unit, onOpen: (String) -> Unit) {
    KitRoot {
        StreamScreenHeader(title = "Semua Layar", onBack = onBack)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(designKitScreens) { (name, icon) ->
                StreamCard(modifier = Modifier.clickable { onOpen(name) }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        StreamIconCircle(icon = icon, onClick = { onOpen(name) }, size = 44)
                        Text(
                            text = name,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}
