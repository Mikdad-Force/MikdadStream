package com.mikdadstream.app.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mikdadstream.app.BuildConfig
import com.mikdadstream.app.data.local.PrefsStore
import com.mikdadstream.app.ui.components.FilterChipPill
import com.mikdadstream.app.ui.theme.brandBrush

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    contentPadding: PaddingValues,
    activeSourceName: String,
    onOpenSources: () -> Unit,
) {
    val profile by viewModel.profile.collectAsState()
    val themeMode by viewModel.themeMode.collectAsState()
    val account by viewModel.account.collectAsState()
    val busy by viewModel.busy.collectAsState()
    val error by viewModel.error.collectAsState()
    val serverStatus by viewModel.serverStatus.collectAsState()

    var editName by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }
    var confirmAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = contentPadding.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(bottom = contentPadding.calculateBottomPadding() + 32.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        // ---------- Kartu profil ----------
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(brandBrush())
                .padding(2.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(66.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(Color(0xFF5B3DF5), Color(0xFFB24BFF)))),
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
                            text = profile?.initials ?: "S",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = profile?.displayName ?: "Pengguna Mikdad",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        if (profile?.premium == true || account?.user?.premium == true) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.16f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp),
                            ) {
                                Text(
                                    text = "PREMIUM",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                    Text(
                        text = profile?.email ?: "—",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    profile?.userNumber?.let {
                        Text(
                            text = "ID Pengguna #$it",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable {
                                nameInput = profile?.displayName.orEmpty()
                                editName = true
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Ubah nama",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                    Text("Ubah", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { viewModel.refreshAccount() },
                        contentAlignment = Alignment.Center,
                    ) {
                        if (busy) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                        } else {
                            Icon(
                                Icons.Filled.Refresh,
                                contentDescription = "Muat ulang akun",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                    Text("Muat", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        if (!error.isNullOrBlank()) {
            Text(
                text = error.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
            )
        }

        // ---------- Tema ----------
        SettingsSection(title = "Tampilan", icon = { Icon(Icons.Filled.Palette, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp)) }) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    PrefsStore.THEME_SYSTEM to "Sistem",
                    PrefsStore.THEME_DARK to "Gelap",
                    PrefsStore.THEME_LIGHT to "Terang",
                ).forEach { (mode, label) ->
                    FilterChipPill(
                        label = label,
                        selected = themeMode == mode,
                        onClick = { viewModel.setThemeMode(mode) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        // ---------- Sumber video ----------
        SettingsSection(title = "Sumber Video", icon = { Icon(Icons.Filled.Movie, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp)) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable(onClick = onOpenSources)
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(Icons.Filled.Movie, null, tint = MaterialTheme.colorScheme.secondary)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = activeSourceName,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = "Ketuk untuk mengganti sumber dari API",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Text(
                    text = "Ganti",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        // ---------- Data ----------
        SettingsSection(title = "Data & Penyimpanan", icon = { Icon(Icons.Filled.Storage, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp)) }) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DataAction("Bersihkan riwayat tontonan") {
                    confirmAction = { viewModel.clearHistory() }
                }
                DataAction("Bersihkan riwayat pencarian") {
                    confirmAction = { viewModel.clearSearchHistory() }
                }
                DataAction("Kosongkan koleksi") {
                    confirmAction = { viewModel.clearWatchlist() }
                }
            }
        }

        // ---------- Informasi ----------
        SettingsSection(title = "Informasi", icon = { Icon(Icons.Filled.Info, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp)) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                InfoRow("Endpoint API", BuildConfig.API_BASE_URL)
                InfoRow("Status server", serverStatus ?: account?.serverTime ?: "Belum diketahui")
                InfoRow("Cache", "${account?.cacheTtlSeconds ?: "-"} detik")
                InfoRow("Versi aplikasi", "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})")
            }
        }

        SettingsSection(
            title = "Tentang",
            icon = { Icon(Icons.Filled.VerifiedUser, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp)) },
        ) {
            Text(
                text = "Mikdad Stream — aplikasi streaming native Android yang terhubung langsung ke Mikdad API. " +
                    "Nonton film, serial TV, anime, donghua, drama pendek, dan TV langsung dari satu tempat.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "Versi pemutar: pemutar lengkap dengan kecepatan, rasio layar, subtitle, " +
                    "gestur kecerahan/volume, Picture-in-Picture, dan timer tidur. " +
                    "Sumber berformat hoster (embed) diputar lewat WebView.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
            )
        }
    }

    // ---------- Dialog ubah nama ----------
    if (editName) {
        AlertDialog(
            onDismissRequest = { editName = false },
            containerColor = MaterialTheme.colorScheme.surface,
            title = { Text("Ubah Nama Tampilan", color = MaterialTheme.colorScheme.onBackground) },
            text = {
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    singleLine = true,
                    placeholder = { Text("Nama baru") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary,
                    ),
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        editName = false
                        viewModel.renameDisplay(nameInput)
                    }
                ) { Text("Simpan", color = MaterialTheme.colorScheme.primary) }
            },
            dismissButton = {
                TextButton(onClick = { editName = false }) { Text("Batal") }
            },
        )
    }

    // ---------- Dialog konfirmasi ----------
    confirmAction?.let { action ->
        AlertDialog(
            onDismissRequest = { confirmAction = null },
            containerColor = MaterialTheme.colorScheme.surface,
            title = { Text("Konfirmasi", color = MaterialTheme.colorScheme.onBackground) },
            text = { Text("Data yang dihapus tidak bisa dikembalikan. Lanjutkan?", color = MaterialTheme.colorScheme.onSurfaceVariant) },
            confirmButton = {
                TextButton(
                    onClick = {
                        action()
                        confirmAction = null
                    }
                ) { Text("Hapus", color = MaterialTheme.colorScheme.secondary) }
            },
            dismissButton = {
                TextButton(onClick = { confirmAction = null }) { Text("Batal") }
            },
        )
    }
}

@Composable
private fun SettingsSection(
    title: String,
    icon: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.20f),
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.20f),
                            )
                        )
                    ),
                contentAlignment = Alignment.Center,
            ) { icon() }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        content()
    }
}

@Composable
private fun DataAction(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "Bersihkan",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
