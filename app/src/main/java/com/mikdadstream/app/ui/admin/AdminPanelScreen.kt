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

    fun load() {
        loading = true
        message = null
        scope.launch {
            users = AppGraph.users.listUsers()
            loading = false
            if (users.isEmpty()) message = AppGraph.users.lastError.value ?: "Belum ada pengguna."
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
                    color = MaterialTheme.colorScheme.error,
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
                                Text(
                                    text = u.displayName ?: u.email ?: u.uid,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Text(
                                    text = "${u.accountType ?: "normal"} • " +
                                        (formatUntil(u.activeUntil) ?: "tanpa batas"),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
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
    // Tipe eksplisit Long? karena opsi "Tanpa batas" mengisi null. Tanpa ini
    // Kotlin menyimpulkan Long (non-null) dan `untilMs = null` gagal kompilasi.
    var untilMs by remember(user.uid) {
        mutableStateOf<Long?>(user.activeUntil ?: inDays(30))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
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

        Text(
            text = "Masa aktif: ${formatUntil(untilMs) ?: "tanpa batas"}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DurationChip("7 hari", untilMs == inDays(7)) { untilMs = inDays(7) }
            DurationChip("30 hari", untilMs == inDays(30)) { untilMs = inDays(30) }
            DurationChip("1 tahun", untilMs == inDays(365)) { untilMs = inDays(365) }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DurationChip("Tanpa batas", untilMs == null) { untilMs = null }
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
                            if (ok) "Tersimpan: ${user.email ?: user.uid} -> ${selected.label}"
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
