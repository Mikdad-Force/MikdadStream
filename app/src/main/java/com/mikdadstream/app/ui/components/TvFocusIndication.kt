package com.mikdadstream.app.ui.components

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Penanda fokus untuk remote / D-pad. Berbeda dengan ripple bawaan yang nyaris
 * tidak terlihat, indikator ini selalu menggambar bingkai biru yang jelas plus
 * latar tipis sehingga posisi pergerakan remote selalu kelihatan, di layar
 * Android TV maupun Android.
 */
object TvFocusIndication : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode =
        FocusNode(interactionSource)

    override fun equals(other: Any?): Boolean = other === this

    override fun hashCode(): Int = System.identityHashCode(this)

    private class FocusNode(
        private val source: InteractionSource,
    ) : Modifier.Node(), DrawModifierNode {

        private var focused = false
        private var pressed = false

        override fun onAttach() {
            coroutineScope.launch {
                source.interactions.collect { interaction ->
                    when (interaction) {
                        is FocusInteraction.Focus -> {
                            focused = true
                            invalidateDraw()
                        }
                        is FocusInteraction.Unfocus -> {
                            focused = false
                            invalidateDraw()
                        }
                        is PressInteraction.Press -> {
                            pressed = true
                            invalidateDraw()
                        }
                        is PressInteraction.Release, is PressInteraction.Cancel -> {
                            pressed = false
                            invalidateDraw()
                        }
                    }
                }
            }
        }

        override fun ContentDrawScope.draw() {
            val radius = 12.dp.toPx()
            val corner = CornerRadius(radius, radius)
            if (focused) {
                drawRoundRect(color = FocusPrimary.copy(alpha = 0.16f), cornerRadius = corner)
            }
            drawContent()
            if (pressed && !focused) {
                drawRoundRect(color = Color.White.copy(alpha = 0.12f), cornerRadius = corner)
            }
            if (focused || pressed) {
                val stroke = 2.dp.toPx()
                val inset = 1.5.dp.toPx()
                drawRoundRect(
                    color = if (focused) FocusPrimary else Color.White.copy(alpha = 0.65f),
                    topLeft = Offset(inset, inset),
                    size = Size(
                        (size.width - inset * 2f).coerceAtLeast(0f),
                        (size.height - inset * 2f).coerceAtLeast(0f),
                    ),
                    cornerRadius = corner,
                    style = Stroke(width = stroke),
                )
            }
        }
    }

    // Ring fokus D-pad: persis gaya mockup TV (border putih TextMain).
    private val FocusPrimary = Color(0xFFF4F4F8)
}
