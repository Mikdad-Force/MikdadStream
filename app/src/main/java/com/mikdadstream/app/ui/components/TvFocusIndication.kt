package com.mikdadstream.app.ui.components

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.invalidateDraw
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Penanda fokus untuk remote / D-pad TANPA ring putih.
 *
 * Fokus hanya ditandai dengan ZOOM (perbesaran) pada komponen yang sedang
 * disorot, sehingga tampilan TV bersih dan mengikuti warna background
 * masing-masing komponen. Umpan balik tekan (sentuh) memakai pengecilan
 * kecil, juga tanpa bingkai.
 */
object TvFocusIndication : IndicationNodeFactory {

    /** Zoom saat fokus: komponen sedikit membesar, tanpa bingkai/ring. */
    private const val FOCUS_ZOOM = 1.06f
    /** Zoom saat ditekan: sedikit mengecil sebagai umpan balik sentuh. */
    private const val PRESS_ZOOM = 0.97f

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
            if (abs(z - 1f) < 0.002f) {
                this@draw.drawContent()
            } else {
                // Zoom ditarik dari pusat komponen: layout tetap, hanya visual.
                // Receiver eksplisit: drawContent() berasal dari ContentDrawScope,
                // bukan dari lambda receiver scale{} (DrawScope).
                scale(z, z, center) { this@draw.drawContent() }
            }
        }
    }
}
