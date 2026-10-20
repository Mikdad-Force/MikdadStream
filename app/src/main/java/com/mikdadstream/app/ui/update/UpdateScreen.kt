package com.mikdadstream.app.ui.update

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import com.mikdadstream.app.R
import com.mikdadstream.app.data.update.UpdateInfo
import com.mikdadstream.app.ui.i18n.LocalStrings
import com.mikdadstream.app.ui.tv.isTelevision
import com.mikdadstream.app.ui.theme.StreamBg
import com.mikdadstream.app.ui.theme.StreamError

import com.mikdadstream.app.ui.theme.StreamSurface
import com.mikdadstream.app.ui.theme.StreamSurfaceChip
import com.mikdadstream.app.ui.theme.StreamTextMuted
import com.mikdadstream.app.ui.theme.StreamTextPrimary
import com.mikdadstream.app.ui.theme.brandBrush
import com.mikdadstream.app.BuildConfig

/**
 * Layar pembaruan wajib. Tidak bisa ditutup: pengguna harus memperbarui
 * dari rilis GitHub untuk melanjutkan.
 */
@Composable
fun UpdateScreen(
    info: UpdateInfo,
    downloading: Boolean,
    progress: Float,
    permissionNeeded: Boolean,
    installing: Boolean,
    failed: Boolean,
    onUpdate: () -> Unit,
    onOpenPermission: () -> Unit,
) {
    val s = LocalStrings.current
    BackHandler(enabled = true) { /* pembaruan wajib: blokir tombol kembali */ }

    val isTv = LocalContext.current.isTelevision()
    val primaryFocus = remember { FocusRequester() }
    val secondaryFocus = remember { FocusRequester() }
    LaunchedEffect(isTv, permissionNeeded) {
        if (!isTv) return@LaunchedEffect
        kotlinx.coroutines.delay(120)
        runCatching {
            if (permissionNeeded) secondaryFocus.requestFocus() else primaryFocus.requestFocus()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.radialGradient(listOf(com.mikdadstream.app.ui.theme.LocalAccent.current.copy(alpha = 0.22f), StreamBg))),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(brandBrush()),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_ghost),
                    contentDescription = null,
                    modifier = Modifier.size(66.dp),
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.16f))
                    .padding(horizontal = 14.dp, vertical = 6.dp),
            ) {
                Text(
                    text = s.updateBadge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelSmall,
                )
            }

            Text(
                text = s.updateTitle,
                style = MaterialTheme.typography.headlineSmall,
                color = StreamTextPrimary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )

            Text(
                text = s.updateBody,
                style = MaterialTheme.typography.bodyMedium,
                color = StreamTextMuted,
                textAlign = TextAlign.Center,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(StreamSurface)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                VersionRow(s.updateCurrent(BuildConfig.VERSION_NAME), StreamTextMuted)
                VersionRow(s.updateLatest(info.version), MaterialTheme.colorScheme.primary)
            }

            if (!info.notes.isNullOrBlank()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(StreamSurface)
                        .padding(16.dp),
                ) {
                    Text(
                        text = "GitHub · ${info.version}",
                        style = MaterialTheme.typography.labelMedium,
                        color = StreamTextMuted,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = info.notes.take(1200),
                        style = MaterialTheme.typography.bodySmall,
                        color = StreamTextPrimary,
                    )
                }
            }

            if (permissionNeeded) {
                Text(
                    text = s.updatePermission,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                )
                Button(
                    onClick = onOpenPermission,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .focusRequester(secondaryFocus),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = StreamSurfaceChip,
                        contentColor = StreamTextPrimary,
                    ),
                ) { Text(s.updatePermissionCta) }
            }

            if (failed) {
                Text(
                    text = s.updateFailed,
                    style = MaterialTheme.typography.bodySmall,
                    color = StreamError,
                    textAlign = TextAlign.Center,
                )
            }

            Button(
                onClick = onUpdate,
                enabled = !downloading && !installing,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .focusRequester(primaryFocus),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = StreamBg,
                ),
            ) {
                when {
                    installing -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color(0xFF04121F),
                            strokeWidth = 2.dp,
                        )
                        Spacer(Modifier.size(10.dp))
                        Text(s.updatePreparing, fontWeight = FontWeight.Bold)
                    }
                    downloading -> {
                        CircularProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.size(20.dp),
                            color = Color(0xFF04121F),
                            strokeWidth = 2.dp,
                        )
                        Spacer(Modifier.size(10.dp))
                        Text(
                            s.updateDownloading((progress * 100).toInt()),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    else -> Text(
                        if (failed) s.updateRetry else s.updateNow,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Text(
                text = "Mikdad-Force/MikdadStream · ${BuildConfig.UPDATE_REPO}",
                style = MaterialTheme.typography.labelSmall,
                color = StreamTextMuted.copy(alpha = 0.6f),
            )
        }
    }
}

@Composable
private fun VersionRow(text: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50))
            .background(StreamSurfaceChip.copy(alpha = 0.5f))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
