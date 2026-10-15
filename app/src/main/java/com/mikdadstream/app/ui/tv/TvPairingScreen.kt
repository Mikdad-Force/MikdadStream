package com.mikdadstream.app.ui.tv

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.mikdadstream.app.AppGraph
import com.mikdadstream.app.BuildConfig
import com.mikdadstream.app.data.remote.PairingData
import com.mikdadstream.app.ui.components.StreamPrimaryButton
import com.mikdadstream.app.ui.theme.PlusJakarta
import kotlinx.coroutines.delay

private sealed interface PairingState {
    data object Loading : PairingState
    data class Active(val data: PairingData) : PairingState
    data class Failed(val message: String) : PairingState
}

@Composable
fun TvPairingScreen(onPaired: () -> Unit) {
    var state by remember { mutableStateOf<PairingState>(PairingState.Loading) }
    var secondsLeft by remember { mutableIntStateOf(900) }
    var expired by remember { mutableStateOf(false) }
    var startedAt by remember { mutableIntStateOf(0) }

    LaunchedEffect(startedAt) {
        state = PairingState.Loading
        expired = false
        val data = AppGraph.apiClient.auth.startDevicePairingAsync()
        if (data?.deviceCode.isNullOrBlank()) {
            state = PairingState.Failed("Gagal membuat kode pairing. Periksa koneksi ke server.")
        } else {
            secondsLeft = data.expiresIn ?: 900
            state = PairingState.Active(data)
        }
    }

    val active = state as? PairingState.Active
    LaunchedEffect(active?.data?.deviceCode) {
        if (active == null) return@LaunchedEffect
        val interval = (active.data.interval ?: 5).coerceAtLeast(3)
        while (true) {
            delay(1000L)
            if (secondsLeft > 0) secondsLeft -= 1
            if (secondsLeft <= 0) {
                expired = true
                break
            }
            if (secondsLeft % interval == 0) {
                val status = AppGraph.apiClient.auth.pollDevicePairingAsync(active.data.deviceCode!!)
                when {
                    status?.isAuthenticated == true -> {
                        val ok = AppGraph.apiClient.auth.exchangeDevicePairingAsync(active.data.deviceCode!!)
                        if (ok) {
                            AppGraph.prefs.setLoggedIn(true)
                            onPaired()
                        } else {
                            state = PairingState.Failed("Gagal mengambil sesi dari server.")
                        }
                        break
                    }
                    status?.status == "expired" -> {
                        expired = true
                        break
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0B12))
            .padding(horizontal = 48.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "MIKDAD STREAM",
            style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 4.sp),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Hubungkan perangkat ini",
            style = MaterialTheme.typography.displaySmall.copy(fontFamily = PlusJakarta, fontSize = 34.sp),
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Buka aplikasi Mikdad Stream di ponsel, lalu pilih Pengaturan > Hubungkan Perangkat, dan pindai kode di bawah.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFFB9BAC4),
            textAlign = TextAlign.Center,
            modifier = Modifier.width(620.dp),
        )
        Spacer(Modifier.height(28.dp))

        when (val s = state) {
            is PairingState.Loading -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(14.dp))
                Text("Menyiapkan kode…", color = Color(0xFFB9BAC4))
            }

            is PairingState.Failed -> TvPairingError(s.message) { startedAt++ }

            is PairingState.Active -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(18.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        val qrText = qrPayload(s.data)
                        val bitmap = remember(qrText) { qrImageBitmap(qrText, 560) }
                        Image(bitmap = bitmap, contentDescription = "Kode QR pairing", modifier = Modifier.fillMaxSize())
                    }
                    Spacer(Modifier.width(40.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Kode masuk",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFFB9BAC4),
                        )
                        Text(
                            text = s.data.userCode ?: "----",
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 44.sp,
                                letterSpacing = 6.sp,
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(Modifier.height(4.dp))
                        if (expired) {
                            Text("Kode kedaluwarsa", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.height(6.dp))
                            StreamPrimaryButton(text = "Buat kode baru", onClick = { startedAt++ })
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    text = "Menunggu persetujuan…",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color(0xFFB9BAC4),
                                )
                            }
                            Text(
                                text = "Kedaluwarsa dalam ${secondsLeft / 60}:${(secondsLeft % 60).toString().padStart(2, '0')}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF7D7E83),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TvPairingError(message: String, onRetry: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0x22E53935))
                .border(1.dp, MaterialTheme.colorScheme.error, RoundedCornerShape(12.dp))
                .padding(16.dp),
        ) {
            Text(message, color = Color(0xFFF5B7B7), textAlign = TextAlign.Center, modifier = Modifier.width(520.dp))
        }
        StreamPrimaryButton(text = "Coba lagi", onClick = onRetry)
    }
}

private fun qrPayload(data: PairingData): String {
    val base = BuildConfig.API_BASE_URL.trimEnd('/')
    val path = data.verificationUriComplete
        ?: "/auth/device?user_code=${data.userCode.orEmpty()}"
    return if (path.startsWith("http")) path else base + path
}

private fun qrImageBitmap(text: String, size: Int): androidx.compose.ui.graphics.ImageBitmap {
    val matrix = runCatching {
        QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size)
    }.getOrNull() ?: return Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888).asImageBitmap()

    val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    for (x in 0 until size) {
        for (y in 0 until size) {
            bmp.setPixel(x, y, if (matrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
        }
    }
    return bmp.asImageBitmap()
}
