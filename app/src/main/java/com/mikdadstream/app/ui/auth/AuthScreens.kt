package com.mikdadstream.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikdadstream.app.ui.components.FilterChipPill
import com.mikdadstream.app.ui.components.StreamIconCircle
import com.mikdadstream.app.ui.components.StreamOutlineButton
import com.mikdadstream.app.ui.components.StreamPrimaryButton
import com.mikdadstream.app.ui.components.StreamScreenHeader
import com.mikdadstream.app.ui.components.StreamTextField
import com.mikdadstream.app.ui.theme.PlusJakarta
import com.mikdadstream.app.ui.theme.brandBrush

// ---------------------------------------------------------------------------
// Shared private building blocks
// ---------------------------------------------------------------------------

@Composable
private fun AuthContainer(
    onBack: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        StreamScreenHeader(title = "", onBack = onBack)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
        ) {
            content()
        }
    }
}

@Composable
private fun BrandMark() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.size(78.dp),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(23.dp))
                    .background(brandBrush())
                    .border(1.dp, Color.White.copy(alpha = 0.18f), RoundedCornerShape(23.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "M",
                    fontFamily = PlusJakarta,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 40.sp,
                    color = Color.White,
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp),
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Mikdad",
                fontFamily = PlusJakarta,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                letterSpacing = (-0.5).sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = "Streaming",
                style = TextStyle(
                    brush = brandBrush(),
                    fontFamily = PlusJakarta,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    letterSpacing = (-0.5).sp,
                ),
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Nonton film & serial tanpa batas",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun AuthTitle(title: String, subtitle: String) {
    Text(title, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onBackground)
    Spacer(Modifier.height(6.dp))
    Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
private fun BottomSwitch(lead: String, action: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(lead, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.width(4.dp))
        Text(
            text = action,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onClick)
                .padding(horizontal = 4.dp, vertical = 2.dp),
        )
    }
}

@Composable
private fun PasswordTrailing(visible: Boolean, onToggle: () -> Unit) {
    Icon(
        imageVector = if (visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
        contentDescription = if (visible) "Sembunyikan sandi" else "Tampilkan sandi",
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .size(20.dp)
            .clickable(onClick = onToggle),
    )
}

// ---------------------------------------------------------------------------
// Public screens
// ---------------------------------------------------------------------------

@Composable
fun SignInScreen(
    onBack: () -> Unit,
    onGoogleSignIn: () -> Unit,
) {
    AuthContainer(onBack = onBack) {
        Spacer(Modifier.height(36.dp))
        BrandMark()
        Spacer(Modifier.height(30.dp))
        AuthTitle(
            title = "Masuk dengan Google",
            subtitle = "Gunakan akun Google kamu untuk masuk dan mulai menonton favoritmu.",
        )
        Spacer(Modifier.height(30.dp))
        GoogleSignInButton(onClick = onGoogleSignIn)
        Spacer(Modifier.height(18.dp))
        Text(
            text = "Dengan melanjutkan, kamu menyetujui Ketentuan Layanan dan Kebijakan Privasi Mikdad Stream.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun GoogleSignInButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GoogleGlyph()
        Spacer(Modifier.width(10.dp))
        Text(
            text = "Masuk dengan Google",
            style = MaterialTheme.typography.labelLarge,
            color = Color(0xFF1F1F1F),
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun GoogleGlyph() {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(Color(0xFF4285F4)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "G",
            fontFamily = PlusJakarta,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 13.sp,
            color = Color.White,
        )
    }
}

@Composable
fun SignUpScreen(
    onBack: () -> Unit,
    onSignUp: () -> Unit,
    onSignIn: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var acceptedTerms by remember { mutableStateOf(false) }

    AuthContainer(onBack = onBack) {
        Spacer(Modifier.height(20.dp))
        AuthTitle("Buat akun baru", "Daftar gratis dan mulai streaming sekarang.")
        Spacer(Modifier.height(22.dp))
        StreamTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = "Nama lengkap",
            leadingIcon = Icons.Outlined.Person,
            imeAction = ImeAction.Next,
        )
        Spacer(Modifier.height(14.dp))
        StreamTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Email",
            leadingIcon = Icons.Outlined.Email,
            imeAction = ImeAction.Next,
        )
        Spacer(Modifier.height(14.dp))
        StreamTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Sandi",
            leadingIcon = Icons.Outlined.Lock,
            trailing = { PasswordTrailing(showPassword) { showPassword = !showPassword } },
            isPassword = true,
            passwordVisible = showPassword,
            imeAction = ImeAction.Next,
        )
        Spacer(Modifier.height(14.dp))
        StreamTextField(
            value = confirm,
            onValueChange = { confirm = it },
            placeholder = "Konfirmasi sandi",
            leadingIcon = Icons.Outlined.Lock,
            isPassword = true,
            passwordVisible = showPassword,
            imeAction = ImeAction.Done,
            onDone = onSignUp,
        )
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = acceptedTerms,
                onCheckedChange = { acceptedTerms = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.outline,
                    checkmarkColor = Color.White,
                ),
            )
            Text(
                text = "Saya menyetujui Syarat & Ketentuan",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        Spacer(Modifier.height(18.dp))
        StreamPrimaryButton(text = "Daftar", onClick = onSignUp)
        Spacer(Modifier.height(28.dp))
        BottomSwitch("Sudah punya akun?", "Masuk", onSignIn)
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit,
    onSend: () -> Unit,
    onSignIn: () -> Unit,
) {
    var email by remember { mutableStateOf("") }

    AuthContainer(onBack = onBack) {
        Spacer(Modifier.height(20.dp))
        AuthTitle("Lupa Sandi?", "Masukkan emailmu dan kami akan mengirim kode untuk mengatur ulang sandi.")
        Spacer(Modifier.height(24.dp))
        StreamTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Email",
            leadingIcon = Icons.Outlined.Email,
            imeAction = ImeAction.Done,
            onDone = onSend,
        )
        Spacer(Modifier.height(20.dp))
        StreamPrimaryButton(text = "Kirim Kode", onClick = onSend)
        Spacer(Modifier.height(28.dp))
        BottomSwitch("Ingat sandimu?", "Masuk", onSignIn)
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun OtpVerificationScreen(
    onBack: () -> Unit,
    onVerify: () -> Unit,
) {
    var code by remember { mutableStateOf("") }

    AuthContainer(onBack = onBack) {
        Spacer(Modifier.height(20.dp))
        AuthTitle("Verifikasi Kode", "Masukkan 6 digit kode yang dikirim ke emailmu.")
        Spacer(Modifier.height(28.dp))
        OtpBoxes(code = code, onCodeChange = { code = it })
        Spacer(Modifier.height(28.dp))
        StreamPrimaryButton(
            text = "Verifikasi",
            onClick = onVerify,
            enabled = code.length == 6,
        )
        Spacer(Modifier.height(18.dp))
        Text(
            text = "Kirim ulang kode",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
        )
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun OtpBoxes(code: String, onCodeChange: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        BasicTextField(
            value = code,
            onValueChange = { raw -> onCodeChange(raw.filter { it.isDigit() }.take(6)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done,
            ),
            cursorBrush = SolidColor(Color.Transparent),
            modifier = Modifier
                .matchParentSize()
                .alpha(0f),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            repeat(6) { i ->
                val filled = i < code.length
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(
                            width = 1.dp,
                            color = if (filled) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(12.dp),
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (filled) code[i].toString() else "",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
fun CreateNewPasswordScreen(
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    AuthContainer(onBack = onBack) {
        Spacer(Modifier.height(20.dp))
        AuthTitle("Buat Sandi Baru", "Gunakan sandi yang kuat dan mudah kamu ingat.")
        Spacer(Modifier.height(24.dp))
        StreamTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Sandi baru",
            leadingIcon = Icons.Outlined.Lock,
            trailing = { PasswordTrailing(showPassword) { showPassword = !showPassword } },
            isPassword = true,
            passwordVisible = showPassword,
            imeAction = ImeAction.Next,
        )
        Spacer(Modifier.height(14.dp))
        StreamTextField(
            value = confirm,
            onValueChange = { confirm = it },
            placeholder = "Konfirmasi sandi",
            leadingIcon = Icons.Outlined.Lock,
            isPassword = true,
            passwordVisible = showPassword,
            imeAction = ImeAction.Done,
            onDone = onDone,
        )
        Spacer(Modifier.height(20.dp))
        StreamPrimaryButton(text = "Simpan", onClick = onDone)
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun CreatePinScreen(
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    var pin by remember { mutableStateOf("") }

    AuthContainer(onBack = onBack) {
        Spacer(Modifier.height(20.dp))
        AuthTitle("Buat PIN", "Buat 4 digit PIN untuk mengamankan akunmu.")
        Spacer(Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(4) { i ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(
                            if (i < pin.length) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant,
                        ),
                )
            }
        }
        Spacer(Modifier.height(36.dp))
        PinPad(
            onDigit = { d -> if (pin.length < 4) pin += d },
            onDelete = { if (pin.isNotEmpty()) pin = pin.dropLast(1) },
        )
        Spacer(Modifier.height(28.dp))
        StreamPrimaryButton(
            text = "Lanjut",
            onClick = onDone,
            enabled = pin.length == 4,
        )
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun PinPad(
    onDigit: (Char) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9").chunked(3).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                row.forEach { digit ->
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center,
                    ) {
                        PinKey(label = digit, onClick = { onDigit(digit[0]) })
                    }
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                PinKey(label = "0", onClick = { onDigit('0') })
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                StreamIconCircle(
                    icon = Icons.Outlined.Backspace,
                    onClick = onDelete,
                    background = MaterialTheme.colorScheme.surfaceVariant,
                    tint = MaterialTheme.colorScheme.onBackground,
                    size = 64,
                )
            }
        }
    }
}

@Composable
private fun PinKey(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun CreateProfileScreen(
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    val categories = listOf("Film", "Serial", "Anime", "Drama", "Korea", "Kartun", "Dokumenter")
    var name by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf(setOf<String>()) }

    AuthContainer(onBack = onBack) {
        Spacer(Modifier.height(20.dp))
        AuthTitle("Lengkapi Profil", "Pilih foto dan minatmu agar rekomendasi lebih pas.")
        Spacer(Modifier.height(24.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            StreamIconCircle(
                icon = Icons.Outlined.Person,
                onClick = { },
                size = 96,
            )
        }
        Spacer(Modifier.height(24.dp))
        StreamTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = "Nama tampilan",
            leadingIcon = Icons.Outlined.Person,
            imeAction = ImeAction.Done,
            onDone = onDone,
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Pilih kategori favorit",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categories) { category ->
                FilterChipPill(
                    label = category,
                    selected = selected.contains(category),
                    onClick = {
                        selected = if (selected.contains(category)) selected - category
                        else selected + category
                    },
                )
            }
        }
        Spacer(Modifier.height(28.dp))
        StreamPrimaryButton(text = "Simpan", onClick = onDone)
        Spacer(Modifier.height(32.dp))
    }
}
