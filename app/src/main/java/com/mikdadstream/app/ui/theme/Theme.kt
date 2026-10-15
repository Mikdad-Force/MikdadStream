package com.mikdadstream.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------- Palet ----------

val DeepBg = Color(0xFF070A13)
val DeepSurface = Color(0xFF101628)
val DeepSurfaceHigh = Color(0xFF18203A)
val DeepOutline = Color(0xFF28314F)

val Violet = Color(0xFF7C5CFF)
val VioletDark = Color(0xFF5B3DF5)
val Rose = Color(0xFFFF5C8A)
val Aqua = Color(0xFF22D3EE)
val Amber = Color(0xFFFFB020)

val TextMutedDark = Color(0xFF8791B0)
val TextPrimaryDark = Color(0xFFECF0FF)

val LightBg = Color(0xFFF5F6FB)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceHigh = Color(0xFFECEFF9)
val LightOutline = Color(0xFFDCE1F0)
val TextMutedLight = Color(0xFF5C6688)
val TextPrimaryLight = Color(0xFF121731)

val GradientBrand = listOf(Violet, Rose)
val GradientAqua = listOf(Aqua, Violet)
val GradientPoster = listOf(Color.Transparent, Color(0xE605070F))

@Composable
fun brandBrush(): Brush = Brush.horizontalGradient(GradientBrand)
@Composable
fun posterBrush(): Brush = Brush.verticalGradient(GradientPoster)
@Composable
fun shimmerBrush(): Brush = Brush.linearGradient(
    listOf(
        Color.White.copy(alpha = 0.05f),
        Color.White.copy(alpha = 0.12f),
        Color.White.copy(alpha = 0.05f),
    )
)

private val DarkScheme = darkColorScheme(
    primary = Violet,
    onPrimary = Color.White,
    primaryContainer = VioletDark,
    onPrimaryContainer = Color.White,
    secondary = Rose,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF4A1D33),
    onSecondaryContainer = Color(0xFFFFD9E4),
    tertiary = Aqua,
    onTertiary = Color(0xFF00232C),
    background = DeepBg,
    onBackground = TextPrimaryDark,
    surface = DeepSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = DeepSurfaceHigh,
    onSurfaceVariant = TextMutedDark,
    surfaceTint = Violet,
    outline = DeepOutline,
    outlineVariant = DeepOutline,
    error = Color(0xFFFF6B6B),
    onError = Color.White,
)

private val LightScheme = lightColorScheme(
    primary = VioletDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE7E0FF),
    onPrimaryContainer = Color(0xFF24106E),
    secondary = Color(0xFFE23E76),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFDCE7),
    onSecondaryContainer = Color(0xFF620029),
    tertiary = Color(0xFF007A91),
    onTertiary = Color.White,
    background = LightBg,
    onBackground = TextPrimaryLight,
    surface = LightSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = LightSurfaceHigh,
    onSurfaceVariant = TextMutedLight,
    outline = LightOutline,
    outlineVariant = LightOutline,
    error = Color(0xFFD32F2F),
    onError = Color.White,
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

private val AppTypography = Typography(
    displaySmall = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 34.sp, letterSpacing = (-0.5).sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 26.sp, letterSpacing = (-0.3).sp),
    headlineSmall = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 19.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
    titleSmall = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 15.sp, lineHeight = 22.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 12.sp),
    labelSmall = TextStyle(fontWeight = FontWeight.Medium, fontSize = 11.sp),
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
        colorScheme = if (darkTheme) DarkScheme else LightScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}
