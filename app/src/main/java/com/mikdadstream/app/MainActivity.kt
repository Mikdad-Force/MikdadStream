package com.mikdadstream.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.mikdadstream.app.ui.nav.AppNavGraph
import com.mikdadstream.app.ui.theme.MikdadStreamTheme
import com.mikdadstream.app.ui.theme.brandBrush

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val themeMode by AppGraph.prefs.themeMode.collectAsState()
            val darkTheme = when (themeMode) {
                1 -> true
                2 -> false
                else -> isSystemInDarkTheme()
            }
            androidx.compose.runtime.SideEffect {
                val controller = WindowCompat.getInsetsController(window, window.decorView)
                controller.isAppearanceLightStatusBars = !darkTheme
                controller.isAppearanceLightNavigationBars = !darkTheme
            }
            MikdadStreamTheme(darkTheme = darkTheme) {
                AppRoot()
            }
        }
    }
}

@Composable
private fun AppRoot() {
    var ready by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        runCatching { AppGraph.apiClient.auth.ensureSession() }
        ready = true
    }

    if (ready) {
        AppNavGraph()
    } else {
        SplashScreen()
    }
}

@Composable
private fun SplashScreen() {
    val transition = rememberInfiniteTransition(label = "splash")
    val pulse by transition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "pulse",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.radialGradient(listOf(Color(0xFF141B33), Color(0xFF070A13)))),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .scale(pulse)
                    .clip(CircleShape)
                    .background(brandBrush()),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(86.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0B0F1C)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(44.dp),
                    )
                }
            }
            Text(
                text = "Mikdad Stream",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Menyiapkan sumber video…",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.65f),
                modifier = Modifier.padding(top = 2.dp),
            )
        }
    }
}
