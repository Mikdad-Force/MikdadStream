package com.mikdadstream.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Satu slot pada FloatingBottomNavBar.
 *
 * @param id              identifier unik slot (boleh dipakai sebagai key navigasi)
 * @param icon            ikon default (kondisi tidak aktif, gaya tipis)
 * @param contentDescription label aksesibilitas
 * @param selectedIcon    ikon opsional saat slot aktif (mis. varian Filled)
 */
data class FloatingNavItem(
    val id: String,
    val icon: ImageVector,
    val contentDescription: String,
    val selectedIcon: ImageVector? = null,
)

/**
 * Bottom navigation berbentuk kapsul melayang (floating pill).
 *
 * Slots dihubungkan garis tipis di tengah. Slot aktif ditandai "orb" gradasi
 * ungu menyala + glow yang berpindah mulus saat seleksi berubah.
 *
 * Komponen ini murni presentasional: pemanggil yang menentukan perilaku
 * [onSelect]. Cocok untuk slot navigasi maupun slot aksi (mis. tombol "+").
 */
@Composable
fun FloatingBottomNavBar(
    items: List<FloatingNavItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFF121212),
    contentColor: Color = Color.White,
    orbColor: Color = Color(0xFF7C3AED),
    orbColorLight: Color = Color(0xFF9D5CF6),
    orbColorDark: Color = Color(0xFF5B21B6),
    shape: Shape = CircleShape,
    height: Dp = 72.dp,
    orbSize: Dp = 52.dp,
    iconSize: Dp = 24.dp,
) {
    if (items.isEmpty()) return
    val safeIndex = selectedIndex.coerceIn(0, items.lastIndex)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp),
    ) {
        val slotWidth = maxWidth / items.size
        val glowSize = orbSize * 2

        val targetOffset = slotWidth * safeIndex + (slotWidth - orbSize) / 2
        val orbOffset by animateDpAsState(
            targetValue = targetOffset,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow,
            ),
            label = "orbOffset",
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .shadow(
                    elevation = 20.dp,
                    shape = shape,
                    clip = false,
                    ambientColor = Color.Black,
                    spotColor = orbColor.copy(alpha = 0.35f),
                )
                .clip(shape)
                .background(containerColor)
                .border(1.dp, Color.White.copy(alpha = 0.06f), shape),
        ) {
            // Garis tipis penghubung antar slot (di belakang ikon).
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth((items.size - 1) / items.size.toFloat())
                    .height(1.dp)
                    .background(contentColor.copy(alpha = 0.10f)),
            )

            // Glow + bola orb aktif (berpindah mulus mengikuti seleksi).
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = orbOffset)
                    .size(glowSize),
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    orbColor.copy(alpha = 0.45f),
                                    orbColor.copy(alpha = 0.14f),
                                    Color.Transparent,
                                ),
                            ),
                        ),
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(orbSize)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(orbColorLight, orbColor, orbColorDark),
                            ),
                            CircleShape,
                        ),
                )
            }

            // Deretan ikon + area tap.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items.forEachIndexed { index, item ->
                    val selected = index == safeIndex
                    val tint by animateColorAsState(
                        targetValue = if (selected) contentColor else contentColor.copy(alpha = 0.85f),
                        animationSpec = tween(200),
                        label = "navTint",
                    )
                    val iconScale by animateFloatAsState(
                        targetValue = if (selected) 1.06f else 1f,
                        animationSpec = tween(200),
                        label = "iconScale",
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) { onSelect(index) },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = if (selected) (item.selectedIcon ?: item.icon) else item.icon,
                            contentDescription = item.contentDescription,
                            tint = tint,
                            modifier = Modifier
                                .size(iconSize)
                                .scale(iconScale),
                        )
                    }
                }
            }
        }
    }
}
