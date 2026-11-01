package com.mikdadstream.app.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mikdadstream.app.AppGraph
import com.mikdadstream.app.data.firebase.AccountType
import com.mikdadstream.app.data.firebase.UserRepository
import com.mikdadstream.app.ui.components.StreamCard
import com.mikdadstream.app.ui.components.StreamDivider
import com.mikdadstream.app.ui.components.StreamScreenHeader
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Admin Panel: mengatur role (Developers/Premium/Normal) dan **masa aktif**
 * pengguna.
 *
 * Hanya bisa dibuka oleh role Developers. Perubahan ditulis ke Firestore dan
 * akan ditolak server bila aturan keamanan belum mengizinkan admin menulis
 * dokumen pengguna lain — pesan penolakan ditampilkan apa adanya agar tidak
 * disangka bug aplikasi.
 */
@Composable
fun AdminPanelScreen(onBack: () -> Unit) {
    val scope = rememberCoroutineScope()
    var users by remember { mutableStateOf<List<UserRepository.AdminUser>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }
    var openUid by remember { mutableStateOf<String?>(null) }
    var emailQuery by remember { mutableStateOf("") }

    fun addUser(u: UserRepository.AdminUser) {
        users = (listOf(u) + users.filterNot { it.uid == u.uid })
        openUid = u.uid
    }

    // Memuat seluruh koleksi `users`. Bila Firestore menolak, tampilkan pesan
    // asli dari UserRepository (berisi petunjuk Rules) alih-alih pesan generik.
    fun load() {
        loading = true
        message = null
        scope.launch {
            users = AppGraph.users.listUsers()
            loading = false
            if (users.isEmpty()) {
                message = AppGraph.users.lastError.value
                    ?: "Belum ada pengguna terdaftar. Pengguna muncul setelah login sekali."
            }
        }
    }

    // Muat sekali saat layar dibuka.
    androidx.compose.runtime.LaunchedEffect(Unit) { load() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        StreamScreenHeader(title = "Admin Panel", onBack = onBack)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "Atur role dan masa aktif pengguna. Role Developers dapat " +
                    "membuka panel ini; masa aktif habis membuat akses kembali Normal.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            // ---- Cari pengguna berdasarkan email ----
            // Jalur ini hanya butuh query pada field `email`, sehingga tetap
            // bekerja walau Rules melarang membaca seluruh koleksi `users`.
            com.mikdadstream.app.ui.components.StreamTextField(
                value = emailQuery,
                onValueChange = { emailQuery = it },
                placeholder = "Cari pengguna lewat email",
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(999.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(enabled = !loading) {
                        val q = emailQuery.trim()
                        if (q.isBlank()) {
                            message = "Isi email pengguna lebih dulu."
                            return@clickable
                        }
                        loading = true
                        message = null
                        scope.launch {
                            val found = AppGraph.users.findUserByEmail(q)
                            loading = false
                            if (found != null) {
                                message = "Ditemukan: ${found.email ?: found.uid}"
                                addUser(found)
                            } else {
                                message = AppGraph.users.lastError.value
                                    ?: "Pengguna tidak ditemukan."
                            }
                        }
                    }
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = if (loading) "Mencari…" else "Cari Pengguna",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }

            if (loading) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                ) { CircularProgressIndicator(modifier = Modifier.size(22.dp)) }
            }

            message?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (it.contains("ditemukan", true) || it.contains("Ditemukan"))
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.error,
                )
            }

            users.forEach { u ->
                StreamCard {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { openUid = if (openUid == u.uid) null else u.uid }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                // Email ditampilkan sebagai identitas utama:
                                // admin perlu memastikan akun yang diatur benar.
                                Text(
                                    text = u.email ?: "(email tidak tersedia)",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                if (!u.displayName.isNullOrBlank()) {
                                    Text(
                                        text = u.displayName,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                }
                                Text(
                                    text = "${u.accountType ?: "normal"} • " +
                                        (formatUntil(u.activeUntil) ?: "permanen"),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (u.activeUntil != null &&
                                        u.activeUntil <= System.currentTimeMillis()
                                    ) MaterialTheme.colorScheme.error
                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            Text(
                                text = if (openUid == u.uid) "Tutup" else "Atur",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                        if (openUid == u.uid) {
                            StreamDivider()
                            UserAccessEditor(
                                user = u,
                                onSaved = { msg -> message = msg; load() },
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/** Pengatur role + masa aktif untuk satu pengguna. */
@Composable
private fun UserAccessEditor(
    user: UserRepository.AdminUser,
    onSaved: (String?) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var saving by remember { mutableStateOf(false) }
    // Default: role saat ini, masa aktif 30 hari dari sekarang bila belum ada.
    var selected by remember(user.uid) {
        mutableStateOf(
            when (user.accountType) {
                AccountType.DEVELOPERS.key -> AccountType.DEVELOPERS
                AccountType.PREMIUM.key -> AccountType.PREMIUM
                else -> AccountType.NORMAL
            },
        )
    }
    // Tipe eksplisit Long? karena "Permanen" memakai null.
    // Nilai awal mengikuti data tersimpan apa adanya (null = permanen).
    var untilMs by remember(user.uid) {
        mutableStateOf<Long?>(user.activeUntil)
    }
    // Mode masa aktif terpilih. "Custom" membuka pemilih tanggal.
    var duration by remember(user.uid) { mutableStateOf(durationFor(user.activeUntil)) }
    var showCustomPicker by remember(user.uid) { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        // ---- Identitas pengguna: email ditampilkan jelas ----
        // Sebelumnya email tidak terlihat saat mengatur, sehingga admin bisa
        // salah orang ketika mengubah role/masa aktif.
        Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Text(
                text = "Akun",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = user.email ?: "(email tidak tersedia)",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "UID: ${user.uid}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        StreamDivider()

        Text(
            text = "Role",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        AccountType.entries.forEach { t ->
            RoleRow(
                label = t.label,
                selected = selected == t,
                onClick = { selected = t },
            )
        }

        StreamDivider()

        Text(
            text = "Masa aktif",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        // Dua baris chip: pilihan cepat yang umum dipakai admin.
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DurationChip("Expired", duration == Duration.EXPIRED) {
                duration = Duration.EXPIRED
                untilMs = System.currentTimeMillis() - 60_000L
            }
            DurationChip("1 Hari", duration == Duration.DAY) {
                duration = Duration.DAY
                untilMs = inDays(1)
            }
            DurationChip("1 Minggu", duration == Duration.WEEK) {
                duration = Duration.WEEK
                untilMs = inDays(7)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DurationChip("1 Bulan", duration == Duration.MONTH) {
                duration = Duration.MONTH
                untilMs = inDays(30)
            }
            DurationChip("Permanen", duration == Duration.FOREVER) {
                duration = Duration.FOREVER
                untilMs = null
            }
            DurationChip("Custom", duration == Duration.CUSTOM) {
                duration = Duration.CUSTOM
                showCustomPicker = true
            }
        }

        Text(
            text = when (duration) {
                Duration.EXPIRED -> "Status: kedaluwarsa sekarang (akses Normal)"
                Duration.FOREVER -> "Status: permanen, tanpa batas waktu"
                Duration.CUSTOM -> "Status: sampai ${formatUntil(untilMs) ?: "?"}"
                else -> "Status: aktif sampai ${formatUntil(untilMs) ?: "?"}"
            },
            style = MaterialTheme.typography.bodySmall,
            color = if (duration == Duration.EXPIRED) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.onSurfaceVariant,
        )

        if (showCustomPicker) {
            CustomDatePicker(
                initialMs = untilMs ?: System.currentTimeMillis(),
                onCancel = { showCustomPicker = false },
                onPick = { picked ->
                    untilMs = picked
                    duration = Duration.CUSTOM
                    showCustomPicker = false
                },
            )
        }

        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(999.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable(enabled = !saving) {
                    saving = true
                    scope.launch {
                        val ok = AppGraph.users.updateUserAccess(user.uid, selected, untilMs)
                        saving = false
                        onSaved(
                            if (ok) "Tersimpan: ${user.email ?: user.uid} • ${selected.label} • " +
                                (formatUntil(untilMs)?.let { "s.d. $it" } ?: "permanen/expired")
                            else AppGraph.users.lastError.value ?: "Gagal menyimpan",
                        )
                    }
                }
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (saving) "Menyimpan…" else "Simpan",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

/** Pilihan cepat masa aktif. */
private enum class Duration { EXPIRED, DAY, WEEK, MONTH, FOREVER, CUSTOM }

/** Tebak mode dari nilai tersimpan agar chip yang aktif mencerminkan data. */
private fun durationFor(activeUntil: Long?): Duration {
    if (activeUntil == null) return Duration.FOREVER
    val now = System.currentTimeMillis()
    if (activeUntil <= now) return Duration.EXPIRED
    val days = ((activeUntil - now) / 86_400_000L).toInt()
    return when (days) {
        in 0..1 -> Duration.DAY
        in 2..8 -> Duration.WEEK
        in 9..31 -> Duration.MONTH
        else -> Duration.CUSTOM
    }
}

/**
 * Pemilih tanggal sederhana untuk mode Custom: geser hari/bulan/tahun.
 * Sengaja tanpa DatePicker Material3 agar tetap ringan dan cocok untuk TV
 * (navigasi D-pad cukup tombol +/-).
 */
@Composable
private fun CustomDatePicker(
    initialMs: Long,
    onCancel: () -> Unit,
    onPick: (Long) -> Unit,
) {
    val cal = remember(initialMs) {
        Calendar.getInstance().apply { timeInMillis = initialMs }
    }
    var day by remember(initialMs) { mutableStateOf(cal.get(Calendar.DAY_OF_MONTH)) }
    var month by remember(initialMs) { mutableStateOf(cal.get(Calendar.MONTH) + 1) }
    var year by remember(initialMs) { mutableStateOf(cal.get(Calendar.YEAR)) }

    fun buildMs(): Long = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1)
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
    }.timeInMillis

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Pilih tanggal berakhir",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        StepperRow("Hari", day, 1, 31) { day = it }
        StepperRow("Bulan", month, 1, 12) { month = it }
        StepperRow("Tahun", year, 2024, 2100) { year = it }
        Text(
            text = "Berakhir: ${formatUntil(buildMs())}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DurationChip("Pakai", true) { onPick(buildMs()) }
            DurationChip("Batal", false, onClick = onCancel)
        }
    }
}

/** Baris +/- untuk satu komponen tanggal. */
@Composable
private fun StepperRow(label: String, value: Int, min: Int, max: Int, onChange: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f),
        )
        DurationChip("−", false) { onChange((value - 1).coerceIn(min, max)) }
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
        )
        DurationChip("+", false) { onChange((value + 1).coerceIn(min, max)) }
    }
}

@Composable
private fun RoleRow(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
        )
    }
}

@Composable
private fun DurationChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = if (selected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(
                if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceVariant,
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
    )
}

private fun inDays(days: Int): Long {
    val c = Calendar.getInstance()
    c.add(Calendar.DAY_OF_YEAR, days)
    return c.timeInMillis
}

private fun formatUntil(ms: Long?): String? {
    if (ms == null || ms <= 0L) return null
    return SimpleDateFormat("dd MMM yyyy", Locale("id", "ID")).format(Date(ms))
}
