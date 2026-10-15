package com.mikdadstream.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------- Streamview dark palette ----------

val StreamBg = Color(0xFF02050F)
val StreamSurface = Color(0xFF0C1019)
val StreamSurfaceHigh = Color(0xFF141720)
val StreamSurfaceChip = Color(0xFF1B1D26)
val StreamOutline = Color(0xFF282837)
val StreamLine = Color(0xFF1B1D26)

val StreamPrimary = Color(0xFF685CF0)
val StreamPrimaryDark = Color(0xFF5647E8)
val StreamPrimarySoft = Color(0xFFBEB9FA)
val StreamPrimaryLite = Color(0xFFCCC8F5)
val StreamPrimaryContainer = Color(0xFF221F3D)

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
    listOf(StreamPrimary, Color(0xFF8A7BFF)),
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
    onSecondary = Color(0xFF1A1633),
    secondaryContainer = StreamSurfaceChip,
    onSecondaryContainer = StreamPrimaryLite,
    tertiary = StreamPrimarySoft,
    onTertiary = Color(0xFF1A1633),
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
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = StreamScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}
