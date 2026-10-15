package com.mikdadstream.app.ui.account

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.mikdadstream.app.AppGraph
import com.mikdadstream.app.ui.components.StreamOutlineButton
import com.mikdadstream.app.ui.components.StreamPrimaryButton
import com.mikdadstream.app.ui.components.StreamScreenHeader
import com.mikdadstream.app.ui.components.StreamTextField
import com.mikdadstream.app.ui.theme.PlusJakarta
import kotlinx.coroutines.launch

private enum class PairStatus { Idle, Working, Success, Failed }

@Composable
fun PairDeviceScreen(onBack: () -> Unit) {
    var code by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(PairStatus.Idle) }
    var message by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    fun approve(raw: String) {
        val userCode = extractUserCode(raw)
        if (userCode.isBlank()) {
            status = PairStatus.Failed
            message = "Kode tidak valid. Format: XXXX-XXXX."
            return
        }
        code = userCode
        status = PairStatus.Working
        scope.launch {
            val ok = runCatching { AppGraph.apiClient.auth.approveDeviceAsync(userCode) }.getOrDefault(false)
            status = if (ok) PairStatus.Success else PairStatus.Failed
            if (!ok) message = "Gagal menghubungkan. Pastikan kode benar & belum kedaluwarsa."
        }
    }

    val scanner = rememberLauncherForActivityResult(ScanContract()) { result ->
        result.contents?.let { approve(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        StreamScreenHeader(title = "Hubungkan Perangkat", onBack = onBack)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Buka layar login di TV, lalu pindai QR yang muncul atau ketik kodenya di bawah.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(
                    Icons.Outlined.QrCodeScanner,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(56.dp),
                )
                Text(
                    text = "Pindai QR di layar TV",
                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = PlusJakarta),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                )
                StreamPrimaryButton(
                    text = "Pindai QR",
                    onClick = {
                        val options = ScanOptions().apply {
                            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                            setPrompt("Pindai QR di layar TV")
                            setBeepEnabled(false)
                            setOrientationLocked(false)
                            setBarcodeImageEnabled(false)
                        }
                        scanner.launch(options)
                    },
                )
            }

            Text(
                text = "Atau ketik kode manual",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            StreamTextField(
                value = code,
                onValueChange = { code = it.uppercase() },
                placeholder = "XXXX-XXXX",
                imeAction = ImeAction.Done,
                onDone = { approve(code) },
            )
            StreamPrimaryButton(
                text = "Hubungkan",
                onClick = { approve(code) },
                enabled = code.isNotBlank() && status != PairStatus.Working,
            )

            when (status) {
                PairStatus.Working -> Text(
                    text = "Menghubungkan…",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                PairStatus.Success -> Text(
                    text = "Berhasil! Perangkat TV sudah terhubung. Kembali ke layar TV.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF00C566),
                    fontWeight = FontWeight.SemiBold,
                )
                PairStatus.Failed -> Text(
                    text = message.ifBlank { "Gagal menghubungkan." },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                )
                PairStatus.Idle -> Unit
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

private fun extractUserCode(raw: String): String {
    val text = raw.trim()
    val fromQuery = Regex("[?&]user_code=([A-Za-z0-9-]+)").find(text)?.groupValues?.get(1)
    val candidate = (fromQuery ?: text).uppercase().trim()
    return if (Regex("^[A-Z0-9]{4}-[A-Z0-9]{4}$").matches(candidate)) candidate else candidate
}
