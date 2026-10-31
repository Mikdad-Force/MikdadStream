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
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Penanda fokus D-pad Android TV yang jelas dan kontras tinggi.
 * Menampilkan zoom perbesaran, background highlight halus, serta ring/border putih
 * yang adaptif sehingga posisi dan perpindahan navigasi remote langsung terlihat jelas.
 */
object TvFocusIndication : IndicationNodeFactory {

    /** Zoom saat fokus: komponen membesar agar menonjol di layar TV. */
    private const val FOCUS_ZOOM = 1.06f
    /** Zoom saat ditekan: sedikit mengecil sebagai umpan balik sentuh/klik. */
    private const val PRESS_ZOOM = 0.97f

    private val FocusRingColor = Color(0xFFFFFFFF)
    private val FocusBgColor = Color(0x28FFFFFF)

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
                        is FocusInteraction.Focus -> focused = true
                        is FocusInteraction.Unfocus -> focused = false
                        is PressInteraction.Press -> pressed = true
                        is PressInteraction.Release, is PressInteraction.Cancel -> pressed = false
                    }
                    invalidateDraw()
                }
            }
        }

        private fun currentZoom(): Float = when {
            focused -> FOCUS_ZOOM
            pressed -> PRESS_ZOOM
            else -> 1f
        }

        override fun ContentDrawScope.draw() {
            val z = currentZoom()
            val minDim = minOf(size.width, size.height)
            // Adaptif: jika tombol lingkaran/persegi kecil, ikuti radius lingkaran; selain itu 12.dp
            val cornerRadius = if (abs(size.width - size.height) < 8.dp.toPx() && minDim < 80.dp.toPx()) {
                minDim / 2f
            } else {
                12.dp.toPx()
            }
            val corner = CornerRadius(cornerRadius, cornerRadius)

            if (abs(z - 1f) < 0.002f) {
                if (focused) {
                    drawRoundRect(color = FocusBgColor, cornerRadius = corner)
                }
                this@draw.drawContent()
            } else {
                scale(z, z, center) {
                    if (focused) {
                        drawRoundRect(color = FocusBgColor, cornerRadius = corner)
                    }
                    this@draw.drawContent()
                    if (focused || pressed) {
                        val stroke = 2.5.dp.toPx()
                        val inset = stroke / 2f
                        drawRoundRect(
                            color = if (focused) FocusRingColor else Color.White.copy(alpha = 0.65f),
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
        }
    }
}
