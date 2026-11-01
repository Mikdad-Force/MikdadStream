package com.mikdadstream.app.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mikdadstream.app.R

/** Kontak dukungan resmi. */
object SupportContact {
    const val NAME = "Mikdad Nurfauzi"
    const val TELEGRAM_HANDLE = "@whymikdad"
    const val TELEGRAM_URL = "https://t.me/whymikdad"
    const val FACEBOOK_URL = "https://www.facebook.com/mikdad404/"
}

/**
 * Pop-up saat masa aktif akun sudah habis.
 *
 * Menampilkan kontak pengembang dengan logo Telegram & Facebook yang dapat
 * diklik untuk langsung membuka aplikasinya. Ditampilkan setelah pengguna
 * menutup pop-up agar tidak menghalangi pemakaian aplikasi sepenuhnya —
 * akses premium tetap dicabut (role turun ke Normal).
 */
@Composable
fun ExpiredAccessDialog(
    onDismiss: () -> Unit,
    expiredSince: String? = null,
) {
    val context = LocalContext.current

    fun open(url: String) {
        runCatching {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                    RoundedCornerShape(22.dp),
                )
                .padding(horizontal = 22.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Masa Aktif Berakhir",
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = buildString {
                    append("Masa aktif akun Anda sudah berakhir")
                    if (!expiredSince.isNullOrBlank()) append(" sejak $expiredSince")
                    append(". Akses kembali ke Normal. Hubungi pengembang untuk ")
                    append("memperpanjang.")
                },
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(18.dp))
            Text(
                text = SupportContact.NAME,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(Modifier.height(14.dp))

            // ---- Tombol kontak dengan logo ----
            ContactButton(
                logoRes = R.drawable.ic_telegram,
                title = "Telegram",
                subtitle = SupportContact.TELEGRAM_HANDLE,
                accent = Color(0xFF29A9EB),
                onClick = { open(SupportContact.TELEGRAM_URL) },
            )
            Spacer(Modifier.height(10.dp))
            ContactButton(
                logoRes = R.drawable.ic_facebook,
                title = "Facebook",
                subtitle = "mikdad404",
                accent = Color(0xFF1877F2),
                onClick = { open(SupportContact.FACEBOOK_URL) },
            )

            Spacer(Modifier.height(18.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = onDismiss),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Tutup",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

/** Satu baris kontak: logo + judul + detail, seluruh baris bisa diklik. */
@Composable
private fun ContactButton(
    logoRes: Int,
    title: String,
    subtitle: String,
    accent: Color,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(accent.copy(alpha = 0.12f))
            .border(1.dp, accent.copy(alpha = 0.45f), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Image(
            painter = painterResource(logoRes),
            contentDescription = title,
            modifier = Modifier.size(30.dp),
        )
        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = accent,
            )
        }
        Text(
            text = "Buka",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = accent,
        )
    }
}
