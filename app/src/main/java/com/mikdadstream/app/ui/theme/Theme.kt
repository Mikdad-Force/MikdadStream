package com.mikdadstream.app.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikdadstream.app.ui.components.TvFocusIndication

// ---------- Streamview dark palette ----------

val StreamBg = Color(0xFF02050F)
val StreamSurface = Color(0xFF0C1019)
val StreamSurfaceHigh = Color(0xFF141720)
val StreamSurfaceChip = Color(0xFF1B1D26)
val StreamOutline = Color(0xFF282837)
val StreamLine = Color(0xFF1B1D26)

val StreamPrimary = Color(0xFF24A0ED)
val StreamPrimaryDark = Color(0xFF1B82C4)
val StreamPrimarySoft = Color(0xFFA9D9F8)
val StreamPrimaryLite = Color(0xFFC6E7FB)
val StreamPrimaryContainer = Color(0xFF0E2E45)

val StreamTextPrimary = Color(0xFFE1E1E2)
val StreamTextSecondary = Color(0xFFB0B0B3)
val StreamTextMuted = Color(0xFF7D7E83)
val StreamTextFaint = Color(0xFF65676D)

val StreamError = Color(0xFFE53935)
val StreamSuccess = Color(0xFF00C566)
val StreamWarning = Color(0xFFFACC15)

// Legacy aliases kept for compatibility with existing call sites.
val Amber = Color(0xFFFACC15)
val Violet = StreamPrimary
val Rose = StreamPrimarySoft

val GradientPoster = listOf(Color.Transparent, Color(0xE602050F))

@Composable
fun brandBrush(): Brush = Brush.linearGradient(
    listOf(StreamPrimary, Color(0xFF6BC1F5)),
)

@Composable
fun posterBrush(): Brush = Brush.verticalGradient(GradientPoster)

@Composable
fun shimmerBrush(): Brush = Brush.linearGradient(
    listOf(
        Color.White.copy(alpha = 0.04f),
        Color.White.copy(alpha = 0.10f),
        Color.White.copy(alpha = 0.04f),
    ),
)

// The Streamview kit is a dark UI; the app ships dark-only.
private val StreamScheme = darkColorScheme(
    primary = StreamPrimary,
    onPrimary = Color.White,
    primaryContainer = StreamPrimaryContainer,
    onPrimaryContainer = StreamPrimarySoft,
    secondary = StreamPrimarySoft,
    onSecondary = Color(0xFF06283A),
    secondaryContainer = StreamSurfaceChip,
    onSecondaryContainer = StreamPrimaryLite,
    tertiary = StreamPrimarySoft,
    onTertiary = Color(0xFF06283A),
    background = StreamBg,
    onBackground = StreamTextPrimary,
    surface = StreamBg,
    onSurface = StreamTextPrimary,
    surfaceVariant = StreamSurfaceHigh,
    onSurfaceVariant = StreamTextMuted,
    surfaceTint = StreamPrimary,
    outline = StreamOutline,
    outlineVariant = StreamLine,
    error = StreamError,
    onError = Color.White,
)

// Varian TV: aksen pink sesuai desain dashboard, dipakai hanya di perangkat TV.
private val TvAccent = Color(0xFFFA4776)
private val StreamTvScheme = StreamScheme.copy(
    primary = TvAccent,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF3A1020),
    onPrimaryContainer = Color(0xFFFFD7E2),
    secondary = Color(0xFFFF8FB0),
    onSecondary = Color(0xFF3A1020),
    onSecondaryContainer = Color(0xFFFFD7E2),
    tertiary = Color(0xFFFF8FB0),
    onTertiary = Color(0xFF3A1020),
    surfaceTint = TvAccent,
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(18.dp),
    extraLarge = RoundedCornerShape(24.dp),
)

private val AppTypography = Typography(
    displaySmall = TextStyle(
        fontFamily = PlusJakarta, fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp, lineHeight = 38.sp, letterSpacing = (-0.4).sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = PlusJakarta, fontWeight = FontWeight.Bold,
        fontSize = 24.sp, lineHeight = 31.sp, letterSpacing = (-0.2).sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = PlusJakarta, fontWeight = FontWeight.Bold,
        fontSize = 20.sp, lineHeight = 26.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = Mulish, fontWeight = FontWeight.Bold, fontSize = 20.sp, lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Mulish, fontWeight = FontWeight.Bold, fontSize = 16.sp, lineHeight = 22.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = Mulish, fontWeight = FontWeight.Bold, fontSize = 14.sp, lineHeight = 22.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Mulish, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 19.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Mulish, fontWeight = FontWeight.Normal, fontSize = 10.sp, lineHeight = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Mulish, fontWeight = FontWeight.Normal, fontSize = 10.sp, lineHeight = 14.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Mulish, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, lineHeight = 22.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Mulish, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 20.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Mulish, fontWeight = FontWeight.Normal, fontSize = 10.sp, lineHeight = 18.sp,
    ),
)

enum class ThemeMode(val value: Int) {
    SYSTEM(0), DARK(1), LIGHT(2);

    companion object {
        fun from(value: Int): ThemeMode = entries.firstOrNull { it.value == value } ?: SYSTEM
    }
}

@Composable
fun MikdadStreamTheme(
    darkTheme: Boolean,
    isTv: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (isTv) StreamTvScheme else StreamScheme,
        typography = AppTypography,
        shapes = AppShapes,
    ) {
        // Penanda fokus global: terlihat jelas saat dinavigasi pakai remote (D-pad).
        CompositionLocalProvider(LocalIndication provides TvFocusIndication) {
            content()
        }
    }
}
