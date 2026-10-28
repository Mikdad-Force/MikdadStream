package com.mikdadstream.app.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.mikdadstream.app.ui.components.TvFocusIndication
import com.mikdadstream.app.ui.tv.isTelevision

// ---------- Palet persis design folder Theme (MikdadStreamUI.kt) ----------

val StreamBg = Color(0xFF0B0B12)          // Bg
val StreamSurface = Color(0xFF171722)     // Surf
val StreamSurfaceHigh = Color(0xFF1E1E2C)
val StreamSurfaceChip = Color(0xFF1E1E2C)
val StreamOutline = Color(0xFF26263A)     // Line
val StreamLine = Color(0xFF26263A)        // Line

val StreamTextPrimary = Color(0xFFF4F4F8) // TextMain
val StreamTextSecondary = Color(0xFFA3A6BA)
val StreamTextMuted = Color(0xFFA3A6BA)   // Muted
val StreamTextFaint = Color(0xFF74768A)

val StreamError = Color(0xFFE53935)
val StreamSuccess = Color(0xFF4ADE80)     // Green (aksen mockup)
val StreamWarning = Color(0xFFFACC15)

// ---------- Sistem aksen: Pink / Amber / Teal / Violet / Hijau ----------

enum class AccentChoice(val label: String, val color: Color, val soft: Color) {
    Pink("Pink", Color(0xFFFF4D6D), Color(0xFFFF8FA5)),
    Amber("Amber", Color(0xFFFFB84D), Color(0xFFFFD68A)),
    Teal("Teal", Color(0xFF2DD4BF), Color(0xFF72E4D6)),
    Violet("Violet", Color(0xFF8B7CFF), Color(0xFFB3A9FF)),
    Green("Hijau", Color(0xFF4ADE80), Color(0xFF8BE8A8));

    companion object {
        fun from(value: Int): AccentChoice = entries.firstOrNull { it.ordinal == value } ?: Pink
    }
}

// Tipe statis harus tetap ProvidableCompositionLocal agar infix `provides` bisa dipakai
val LocalAccent = compositionLocalOf { AccentChoice.Pink.color }

// Legacy aliases — nilai default aksen Pink (dihitung dinamis di layar via colorScheme).
val StreamPrimary = AccentChoice.Pink.color
val StreamPrimaryDark = Color(0xFFD13A57)
val StreamPrimarySoft = AccentChoice.Pink.soft
val StreamPrimaryLite = Color(0xFFFFC2CF)
val StreamPrimaryContainer = Color(0xFF3A1B26)

// Legacy aliases kept for compatibility with existing call sites.
val Amber = Color(0xFFFACC15)
val Violet = AccentChoice.Violet.color
val Rose = AccentChoice.Pink.soft

val GradientPoster = listOf(Color.Transparent, Color(0xE60B0B12))

/** Brush gradasi yang ikut warna aksen aktif (avatar ring, splash, brand). */
@Composable
fun brandBrush(): Brush {
    val color = LocalAccent.current
    val soft = AccentChoice.entries.firstOrNull { it.color == color }?.soft ?: color.copy(alpha = 0.65f)
    return Brush.linearGradient(listOf(color, soft))
}

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

// Kit UI gelap, memakai warna aksen aktif sebagai primary.
private fun buildStreamScheme(accent: AccentChoice) = darkColorScheme(
    primary = accent.color,
    onPrimary = StreamBg,               // teks di atas tombol aksen = Bg, persis mockup
    primaryContainer = accent.soft,
    onPrimaryContainer = StreamBg,
    secondary = accent.soft,
    onSecondary = StreamBg,
    secondaryContainer = StreamSurfaceChip,
    onSecondaryContainer = StreamTextPrimary,
    tertiary = accent.soft,
    onTertiary = StreamBg,
    background = StreamBg,
    onBackground = StreamTextPrimary,
    surface = StreamSurface,
    onSurface = StreamTextPrimary,
    surfaceVariant = StreamSurfaceHigh,
    onSurfaceVariant = StreamTextMuted,
    surfaceTint = accent.color,
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
    accent: AccentChoice = AccentChoice.Pink,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = buildStreamScheme(accent),
        typography = AppTypography,
        shapes = AppShapes,
    ) {
        // Aksen aktif bisa dibaca komponen mana pun via LocalAccent.current.
        // Indikasi fokus TV memakai ZOOM halus tanpa ring putih (TvFocusIndication);
        // di ponsel sentuh pakai ripple bawaan supaya tidak ada bingkai saat ditekan.
        val context = LocalContext.current
        val tv = remember(context) { context.isTelevision() }
        val baseIndication = LocalIndication.current
        CompositionLocalProvider(
            LocalAccent provides accent.color,
            LocalIndication provides if (tv) TvFocusIndication else baseIndication,
        ) {
            content()
        }
    }
}
