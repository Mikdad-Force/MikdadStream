package com.mikdadstream.app.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.Widgets
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mikdadstream.app.BuildConfig
import com.mikdadstream.app.data.local.PrefsStore
import com.mikdadstream.app.data.remote.SourceModel
import com.mikdadstream.app.ui.components.FilterChipPill
import com.mikdadstream.app.ui.theme.StreamPrimarySoft
import com.mikdadstream.app.ui.theme.StreamTextMuted
import com.mikdadstream.app.ui.theme.brandBrush

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    contentPadding: PaddingValues,
    activeSourceName: String,
    onOpenSources: () -> Unit,
    onPairDevice: () -> Unit = {},
    onOpenDesignKit: () -> Unit = {},
) {
    val profile by viewModel.profile.collectAsState()
    val themeMode by viewModel.themeMode.collectAsState()
    val account by viewModel.account.collectAsState()
    val busy by viewModel.busy.collectAsState()
    val error by viewModel.error.collectAsState()
    val serverStatus by viewModel.serverStatus.collectAsState()
    val allSources by viewModel.allSources.collectAsState()
    val hiddenSources by viewModel.hiddenSources.collectAsState()
    val healthRunning by viewModel.healthRunning.collectAsState()
    val healthResults by viewModel.healthResults.collectAsState()
    val healthProgress by viewModel.healthProgress.collectAsState()

    val displaySourceName = remember(activeSourceName, allSources) {
        allSources.firstOrNull { it.safeId.equals(activeSourceName, true) }?.safeName
            ?: allSources.firstOrNull { it.safeName.equals(activeSourceName, true) }?.safeName
            ?: activeSourceName.ifBlank { "Sumber" }
    }

    var editName by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }
    var confirmAction by remember { mutableStateOf<(() -> Unit)?>(null) }
    var showHealthDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(top = contentPadding.calculateTopPadding() + 12.dp)
            .padding(bottom = contentPadding.calculateBottomPadding() + 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        // ---------- Header ----------
        Text(
            text = "Profil",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        // ---------- Kartu profil ----------
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(brandBrush()),
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
                            style = MaterialTheme.typography.titleLarge,
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
                            modifier = Modifier.weight(1f, fill = false),
                        )
                        if (profile?.premium == true || account?.user?.premium == true) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.16f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp),
                            ) {
                                Text(
                                    text = "PREMIUM",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                    Text(
                        text = profile?.email ?: "—",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "ID Pengguna #${profile?.userNumber ?: "-"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ProfileAction(
                    label = "Ubah",
                    icon = Icons.Filled.Edit,
                    filled = false,
                    onClick = {
                        nameInput = profile?.displayName.orEmpty()
                        editName = true
                    },
                )
                ProfileAction(
                    label = "Muat Ulang",
                    icon = Icons.Filled.Refresh,
                    filled = true,
                    loading = busy,
                    onClick = { viewModel.refreshAccount() },
                )
            }
        }

        if (!error.isNullOrBlank()) {
            Text(
                text = error.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 4.dp),
            )
        }

        // ---------- Tema ----------
        SettingsSection(
            title = "Tampilan",
            icon = { Icon(Icons.Filled.Palette, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)) },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
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
        SettingsSection(
            title = "Sumber Video",
            icon = { Icon(Icons.Filled.Movie, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)) },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onOpenSources)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Icon(
                    Icons.Filled.Movie,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(22.dp),
                )
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = displaySourceName,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "Nama sumber diambil otomatis dari server",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Text(
                    text = "Ganti",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            SettingsDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !healthRunning) {
                        showHealthDialog = true
                        viewModel.checkSources()
                    }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                if (healthRunning) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                    )
                } else {
                    Icon(
                        Icons.Filled.HealthAndSafety,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp),
                    )
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = if (healthRunning) "Menguji sumber…" else "Cek Kesehatan Sumber",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = healthProgress.ifBlank { "Uji kategori, video, detail, sampai stream siap putar" },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Text(
                    text = if (healthRunning) "Berjalan" else "Uji",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            val hiddenList = allSources.filter { hiddenSources.contains(it.safeId.lowercase()) }
            if (hiddenList.isNotEmpty()) {
                SettingsDivider()
                Text(
                    text = "SUMBER TERSEMBUNYI (${hiddenList.size})",
                    style = MaterialTheme.typography.labelSmall,
                    color = StreamTextMuted,
                    letterSpacing = 0.6.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 4.dp),
                )
                hiddenList.forEach { model ->
                    HiddenSourceRow(model = model, onUnhide = { viewModel.unhideSource(model.safeId) })
                }
                Box(modifier = Modifier.height(4.dp))
            }
        }

        // ---------- Perangkat ----------
        SettingsSection(
            title = "Perangkat",
            icon = { Icon(Icons.Filled.Tv, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)) },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onPairDevice)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Icon(
                    Icons.Outlined.QrCodeScanner,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp),
                )
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Hubungkan Perangkat",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = "Sambungkan TV dengan memindai kode QR",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Text(
                    text = "Buka",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        // ---------- Lainnya ----------
        SettingsSection(
            title = "Lainnya",
            icon = { Icon(Icons.Filled.Movie, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)) },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onOpenDesignKit)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Icon(
                    Icons.Outlined.Widgets,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp),
                )
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Semua Layar",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = "Jelajahi seluruh fitur & halaman aplikasi",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Icon(
                    Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp),
                )
            }
        }

        // ---------- Data ----------
        SettingsSection(
            title = "Data & Penyimpanan",
            icon = { Icon(Icons.Filled.Storage, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)) },
        ) {
            DataAction(Icons.Filled.History, "Bersihkan riwayat tontonan") {
                confirmAction = { viewModel.clearHistory() }
            }
            SettingsDivider()
            DataAction(Icons.Filled.DeleteSweep, "Bersihkan riwayat pencarian") {
                confirmAction = { viewModel.clearSearchHistory() }
            }
            SettingsDivider()
            DataAction(Icons.Filled.Bookmark, "Kosongkan koleksi") {
                confirmAction = { viewModel.clearWatchlist() }
            }
        }

        // ---------- Informasi ----------
        SettingsSection(
            title = "Informasi",
            icon = { Icon(Icons.Filled.Info, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)) },
        ) {
            InfoRow("Versi aplikasi", "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})")
        }

        SettingsSection(
            title = "Tentang",
            icon = { Icon(Icons.Filled.VerifiedUser, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "Mikdad Stream — aplikasi streaming native Android. " +
                        "Nonton film, serial TV, anime, donghua, drama pendek, dan TV langsung dari satu tempat.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Pemutar lengkap dengan kecepatan, rasio layar, subtitle, " +
                        "gestur kecerahan/volume, Picture-in-Picture, dan timer tidur.",
                    style = MaterialTheme.typography.bodySmall,
                    color = StreamTextMuted,
                )
            }
        }
    }

    // ---------- Dialog hasil cek kesehatan ----------
    if (showHealthDialog) {
        AlertDialog(
            onDismissRequest = { showHealthDialog = false },
            containerColor = MaterialTheme.colorScheme.surface,
            title = {
                Text(
                    text = if (healthRunning) "Menguji Sumber…" else "Hasil Cek Sumber",
                    color = MaterialTheme.colorScheme.onBackground,
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (healthProgress.isNotBlank()) {
                        Text(
                            text = healthProgress,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    if (healthRunning) {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                    if (healthResults.isEmpty() && !healthRunning) {
                        Text(
                            text = "Belum ada hasil.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(healthResults, key = { it.model.safeId }) { health ->
                            HealthResultRow(health)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showHealthDialog = false }) {
                    Text("Tutup", color = MaterialTheme.colorScheme.primary)
                }
            },
            dismissButton = {
                if (healthRunning) {
                    TextButton(onClick = { viewModel.cancelHealthCheck() }) {
                        Text("Batal", color = MaterialTheme.colorScheme.error)
                    }
                } else if (healthResults.isNotEmpty()) {
                    TextButton(onClick = { viewModel.clearHealthResults() }) {
                        Text("Bersihkan", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            },
        )
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
                ) { Text("Hapus", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { confirmAction = null }) { Text("Batal") }
            },
        )
    }
}

@Composable
private fun ProfileAction(
    label: String,
    icon: ImageVector,
    filled: Boolean,
    onClick: () -> Unit,
    loading: Boolean = false,
) {
    val shape = RoundedCornerShape(999.dp)
    val background = if (filled) MaterialTheme.colorScheme.primary else Color.Transparent
    val content = if (filled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
    Row(
        modifier = Modifier
            .clip(shape)
            .background(background)
            .border(
                width = if (filled) 0.dp else 1.dp,
                color = if (filled) Color.Transparent else MaterialTheme.colorScheme.outline,
                shape = shape,
            )
            .clickable(enabled = !loading, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = content)
        } else {
            Icon(icon, contentDescription = null, tint = content, modifier = Modifier.size(16.dp))
        }
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = content)
    }
}

@Composable
private fun SettingsDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant),
    )
}

@Composable
private fun HealthStat(label: String, count: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = color,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun HealthResultRow(health: SourceHealth) {
    val (statusIcon, statusColor, statusText) = when (health.status) {
        HealthStatus.Running -> Triple(Icons.Filled.Refresh, MaterialTheme.colorScheme.primary, "Menguji")
        HealthStatus.Ok -> Triple(Icons.Filled.CheckCircle, StreamPrimarySoft, "Siap putar")
        HealthStatus.Warning -> Triple(Icons.Filled.WarningAmber, MaterialTheme.colorScheme.secondary, health.message.ifBlank { "Perhatian" })
        HealthStatus.Failed -> Triple(Icons.Filled.ErrorOutline, MaterialTheme.colorScheme.error, health.message.ifBlank { "Gagal" })
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            imageVector = statusIcon,
            contentDescription = null,
            tint = statusColor,
            modifier = Modifier.size(18.dp),
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = health.model.safeName,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = statusText,
                style = MaterialTheme.typography.bodySmall,
                color = statusColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        health.latencyMs?.let { ms ->
            Text(
                text = "${ms}ms",
                style = MaterialTheme.typography.labelSmall,
                color = StreamTextMuted,
            )
        }
    }
}

@Composable
private fun HiddenSourceRow(model: SourceModel, onUnhide: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onUnhide)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Icon(
            Icons.Outlined.Movie,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp),
        )
        Text(
            text = model.safeName,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.16f))
                .padding(horizontal = 8.dp, vertical = 3.dp),
        ) {
            Text(
                text = "Tersembunyi",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Icon(
            Icons.Filled.Visibility,
            contentDescription = "Tampilkan",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(20.dp)
                .clickable(onClick = onUnhide),
        )
    }
}

@Composable
private fun SettingsSection(
    title: String,
    icon: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 4.dp),
        ) {
            icon()
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = StreamTextMuted,
                letterSpacing = 0.8.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            content = content,
        )
    }
}

@Composable
private fun DataAction(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(22.dp),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f),
        )
        Icon(
            Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
