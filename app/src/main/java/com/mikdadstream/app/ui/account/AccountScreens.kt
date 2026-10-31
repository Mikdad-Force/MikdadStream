package com.mikdadstream.app.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.material.icons.outlined.Recommend
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import android.content.Context
import android.content.res.Configuration
import java.util.Locale
import com.mikdadstream.app.AppGraph
import com.mikdadstream.app.data.local.CountryLocations
import com.mikdadstream.app.data.local.PrefsStore
import com.mikdadstream.app.data.moviebox.LocalMovieBoxClient
import com.mikdadstream.app.ui.components.FilterChipPill
import com.mikdadstream.app.ui.components.StreamCard
import com.mikdadstream.app.ui.components.StreamDivider
import com.mikdadstream.app.ui.components.StreamIconCircle
import com.mikdadstream.app.ui.components.StreamNavRow
import com.mikdadstream.app.ui.components.StreamOutlineButton
import com.mikdadstream.app.ui.components.StreamPrimaryButton
import com.mikdadstream.app.ui.components.StreamScreenHeader
import com.mikdadstream.app.ui.components.StreamTextField
import com.mikdadstream.app.ui.theme.StreamTextMuted
import com.mikdadstream.app.ui.theme.brandBrush

@Composable
private fun AccountScaffold(
    title: String,
    onBack: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        StreamScreenHeader(title = title, onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = content,
        )
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = StreamTextMuted,
        modifier = Modifier.padding(start = 2.dp),
    )
}

@Composable
fun EditProfileScreen(onBack: () -> Unit) {
    var name by remember { mutableStateOf("Mikdad") }
    var email by remember { mutableStateOf("mikdad@example.com") }
    var phone by remember { mutableStateOf("+62 812 3456 7890") }

    AccountScaffold(title = "Ubah Profil", onBack = onBack) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            StreamIconCircle(icon = Icons.Filled.Person, onClick = {}, size = 72)
            Text(
                text = "Ubah foto",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            FieldLabel("Nama")
            StreamTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "Nama lengkap",
                leadingIcon = Icons.Filled.Person,
            )
            FieldLabel("Email")
            StreamTextField(
                value = email,
                onValueChange = {},
                placeholder = "Email",
                leadingIcon = Icons.Filled.Email,
                trailing = {
                    Icon(
                        Icons.Filled.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp),
                    )
                },
            )
            FieldLabel("Nomor HP")
            StreamTextField(
                value = phone,
                onValueChange = { phone = it },
                placeholder = "Nomor HP",
                leadingIcon = Icons.Filled.Phone,
                imeAction = ImeAction.Done,
            )
        }

        StreamPrimaryButton(text = "Simpan", onClick = {})
    }
}

@Composable
fun ChangePasswordScreen(onBack: () -> Unit) {
    var current by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    AccountScaffold(title = "Ubah Sandi", onBack = onBack) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            FieldLabel("Sandi Saat Ini")
            StreamTextField(
                value = current,
                onValueChange = { current = it },
                placeholder = "Sandi saat ini",
                leadingIcon = Icons.Filled.Lock,
                isPassword = true,
            )
            FieldLabel("Sandi Baru")
            StreamTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = "Sandi baru",
                leadingIcon = Icons.Filled.Lock,
                isPassword = true,
            )
            FieldLabel("Konfirmasi Sandi")
            StreamTextField(
                value = confirm,
                onValueChange = { confirm = it },
                placeholder = "Ulangi sandi baru",
                leadingIcon = Icons.Filled.Lock,
                isPassword = true,
                imeAction = ImeAction.Done,
            )
        }

        StreamPrimaryButton(text = "Simpan Sandi", onClick = {})
    }
}

@Composable
fun LanguageScreen(onBack: () -> Unit) {
    val options = listOf(
        PrefsStore.LANG_ID to "Bahasa Indonesia",
        PrefsStore.LANG_EN to "Bahasa Inggris",
        PrefsStore.LANG_SU to "Bahasa Sunda",
    )
    val prefs = AppGraph.prefs
    val current by prefs.language.collectAsState()
    val context = LocalContext.current

    AccountScaffold(title = com.mikdadstream.app.ui.i18n.LocalStrings.current.languageTitle, onBack = onBack) {
        StreamCard {
            Column {
                options.forEachIndexed { index, (code, label) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                prefs.setLanguage(code)
                                applyAppLanguage(context, code)
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.weight(1f),
                        )
                        if (current == code) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                    if (index != options.lastIndex) StreamDivider()
                }
            }
        }
    }
}

@Composable
fun LocationScreen(onBack: () -> Unit) {
    val prefs = AppGraph.prefs
    val current by prefs.region.collectAsState()

    AccountScaffold(title = "Lokasi Negara", onBack = onBack) {
        Text(
            text = "Lokasi ini dipakai untuk Moviebox, Live TV, dan Sport TV.",
            style = MaterialTheme.typography.bodySmall,
            color = StreamTextMuted,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
        StreamCard {
            Column {
                CountryLocations.all.forEachIndexed { index, location ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                prefs.setRegion(location.code)
                                LocalMovieBoxClient.onRegionChanged()
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            text = location.flag,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = location.name,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Text(
                                text = location.code,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        if (current == location.code) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                    if (index != CountryLocations.all.lastIndex) StreamDivider()
                }
            }
        }
    }
}

@Suppress("DEPRECATION")
private fun applyAppLanguage(context: Context, code: String) {
    val locale = Locale.forLanguageTag(code)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}

@Composable
private fun NotificationToggle(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp),
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun NotificationsScreen(onBack: () -> Unit) {
    var film by remember { mutableStateOf(true) }
    var rekomendasi by remember { mutableStateOf(true) }
    var pengingat by remember { mutableStateOf(false) }
    var promo by remember { mutableStateOf(false) }

    AccountScaffold(title = "Notifikasi", onBack = onBack) {
        StreamCard {
            Column {
                NotificationToggle("Film Baru", Icons.Outlined.Movie, film) { film = it }
                StreamDivider()
                NotificationToggle("Rekomendasi", Icons.Outlined.Recommend, rekomendasi) { rekomendasi = it }
                StreamDivider()
                NotificationToggle("Pengingat Tonton", Icons.Outlined.Schedule, pengingat) { pengingat = it }
                StreamDivider()
                NotificationToggle("Promo", Icons.Outlined.LocalOffer, promo) { promo = it }
            }
        }
    }
}

@Composable
fun SecurityScreen(onBack: () -> Unit) {
    AccountScaffold(title = "Keamanan", onBack = onBack) {
        StreamCard {
            Column {
                StreamNavRow(
                    icon = Icons.Outlined.Lock,
                    title = "Ubah Sandi",
                    onClick = {},
                    trailing = { Chevron() },
                )
                StreamDivider()
                StreamNavRow(
                    icon = Icons.Outlined.Pin,
                    title = "PIN",
                    onClick = {},
                    trailing = { Chevron() },
                )
                StreamDivider()
                StreamNavRow(
                    icon = Icons.Outlined.VerifiedUser,
                    title = "Autentikasi 2 Langkah",
                    onClick = {},
                    trailing = { Chevron() },
                )
                StreamDivider()
                StreamNavRow(
                    icon = Icons.Outlined.Devices,
                    title = "Perangkat Aktif",
                    onClick = {},
                    trailing = { Chevron() },
                )
            }
        }
    }
}

@Composable
private fun Chevron() {
    Icon(
        Icons.Outlined.ChevronRight,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.size(20.dp),
    )
}

@Composable
fun HelpSupportScreen(onBack: () -> Unit) {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(-1) }
    val faqs = listOf(
        "Bagaimana cara mengunduh film?" to "Buka halaman detail film, lalu ketuk tombol Unduh agar konten tersimpan untuk ditonton offline.",
        "Mengapa video tidak bisa diputar?" to "Pastikan koneksi internet stabil dan sumber video aktif. Coba ganti sumber pada halaman detail bila masalah berlanjut.",
        "Bagaimana cara membatalkan langganan?" to "Masuk ke menu Langganan, lalu pilih kelola langganan dan ikuti langkah pembatalan.",
        "Apakah bisa menonton di beberapa perangkat?" to "Akun Premium dapat digunakan pada hingga 4 perangkat secara bersamaan.",
    )

    AccountScaffold(title = "Bantuan & Dukungan", onBack = onBack) {
        StreamTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = "Cari bantuan",
            leadingIcon = Icons.Filled.Search,
        )

        faqs.forEachIndexed { index, (question, answer) ->
            StreamCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = if (expanded == index) -1 else index }
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            text = question,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.weight(1f),
                        )
                        Icon(
                            imageVector = if (expanded == index) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                    if (expanded == index) {
                        Text(
                            text = answer,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }

        StreamOutlineButton(text = "Hubungi Kami", onClick = {})
    }
}

@Composable
fun LegalPoliciesScreen(onBack: () -> Unit) {
    val sections = listOf(
        "Syarat & Ketentuan" to "Dengan menggunakan AkashaStream, Anda setuju untuk mematuhi seluruh ketentuan layanan. " +
            "Akun bersifat pribadi dan tidak boleh dibagikan tanpa izin. Konten yang tersedia hanya untuk penggunaan " +
            "pribadi dan tidak boleh diperjualbelikan. Kami berhak menangguhkan akun yang melanggar ketentuan.",
        "Kebijakan Privasi" to "Kami menghargai privasi Anda. Data yang dikumpulkan terbatas pada informasi akun, " +
            "riwayat tontonan, dan preferensi untuk meningkatkan pengalaman. Kami tidak menjual data pribadi kepada " +
            "pihak ketiga. Anda dapat meminta penghapusan data kapan saja melalui pengaturan akun.",
    )

    AccountScaffold(title = "Kebijakan & Ketentuan", onBack = onBack) {
        sections.forEach { (title, body) ->
            StreamCard {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = body,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
fun LogoutScreen(onBack: () -> Unit, onConfirm: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        StreamScreenHeader(title = "Keluar", onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            StreamIconCircle(
                icon = Icons.Outlined.Logout,
                onClick = {},
                background = MaterialTheme.colorScheme.error.copy(alpha = 0.15f),
                tint = MaterialTheme.colorScheme.error,
                size = 72,
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Keluar dari Akun?",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Anda harus masuk kembali untuk mengakses koleksi dan riwayat tontonan Anda.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            StreamPrimaryButton(text = "Keluar", onClick = onConfirm)
            StreamOutlineButton(text = "Batal", onClick = onBack)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FaveCategoriesScreen(onBack: () -> Unit) {
    val categories = listOf(
        "Action", "Comedy", "Drama", "Horror", "Romance", "Sci-Fi",
        "Thriller", "Animation", "Adventure", "Mystery", "Crime", "Documentary",
    )
    var selected by remember { mutableStateOf(setOf("Action", "Drama")) }

    AccountScaffold(title = "Kategori Favorit", onBack = onBack) {
        Text(
            text = "Pilih kategori yang ingin Anda lihat lebih sering di beranda.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            categories.forEach { category ->
                FilterChipPill(
                    label = category,
                    selected = category in selected,
                    onClick = {
                        selected = if (category in selected) selected - category else selected + category
                    },
                )
            }
        }

        StreamPrimaryButton(text = "Simpan", onClick = {})
    }
}

@Composable
fun FamilySharingScreen(onBack: () -> Unit) {
    val members = listOf(
        Triple("Mikdad", "Kepala Keluarga", "M"),
        Triple("Aisyah", "Anggota", "A"),
        Triple("Yusuf", "Anak", "Y"),
        Triple("Khadijah", "Anak", "K"),
    )

    AccountScaffold(title = "Berbagi Keluarga", onBack = onBack) {
        Text(
            text = "Bagikan langganan Premium Anda dengan hingga 4 anggota keluarga.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        StreamCard {
            Column {
                members.forEachIndexed { index, (name, role, initial) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(brandBrush()),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = initial,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = name,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Text(
                                text = role,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                    if (index != members.lastIndex) StreamDivider()
                }
            }
        }

        StreamOutlineButton(text = "Tambah Anggota", onClick = {})
    }
}

/**
 * Catatan subtitle: menampilkan track yang dikirim API, yang terpasang, dan
 * hasil pemilihan. Berguna memastikan apakah masalah subtitle berasal dari API
 * atau dari aplikasi, tanpa perlu `adb logcat`.
 */
@Composable
fun SubtitleLogScreen(onBack: () -> Unit) {
    val lines by com.mikdadstream.app.data.local.SubtitleLog.lines.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current

    AccountScaffold(title = "Log Subtitle", onBack = onBack) {
        Text(
            text = "Catatan ${lines.size} baris terakhir. Salin lalu kirimkan bila " +
                "subtitle tidak muncul.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(10.dp))

        if (lines.isEmpty()) {
            Text(
                text = "Belum ada catatan. Putar film lalu pilih subtitle.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            StreamCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp)
                        .verticalScroll(rememberScrollState())
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    // Terbaru di atas agar kejadian terakhir langsung terlihat.
                    lines.asReversed().forEach { line ->
                        Text(
                            text = line,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        StreamPrimaryButton(
            text = "Salin ke Clipboard",
            onClick = {
                val cm = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)
                    as? android.content.ClipboardManager
                cm?.setPrimaryClip(
                    android.content.ClipData.newPlainText(
                        "Log Subtitle",
                        com.mikdadstream.app.data.local.SubtitleLog.asText(),
                    ),
                )
                android.widget.Toast
                    .makeText(context, "Log disalin", android.widget.Toast.LENGTH_SHORT)
                    .show()
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        StreamOutlineButton(
            text = "Hapus Log",
            onClick = { com.mikdadstream.app.data.local.SubtitleLog.clear() },
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
