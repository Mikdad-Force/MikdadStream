package com.mikdadstream.app.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Kartu terasa "ditekan" (mengecil halus) lalu kembali. */
@Composable
fun Modifier.pressScale(onClick: () -> Unit): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.955f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = "pressScale",
    )
    return this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
            // bayangan dinamis mengikuti tekanan
            shadowElevation = if (pressed) 2.dp.toPx() else 10.dp.toPx()
        }
        .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
}

/** Bayangan lembut konsisten untuk semua kartu. */
fun Modifier.softShadow(elevation: Dp = 10.dp, shape: androidx.compose.ui.graphics.Shape): Modifier =
    shadow(elevation = elevation, shape = shape, ambientColor = androidx.compose.ui.graphics.Color.Black, spotColor = androidx.compose.ui.graphics.Color.Black)
