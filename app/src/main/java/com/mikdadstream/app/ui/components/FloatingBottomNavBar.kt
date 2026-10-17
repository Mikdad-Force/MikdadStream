package com.mikdadstream.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikdadstream.app.ui.theme.LocalAccent

data class FloatingNavItem(
    val id: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector? = null,
)

@Composable
fun FloatingBottomNavBar(
    items: List<FloatingNavItem>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFF161722),
    borderColor: Color = Color(0xFF262839),
    mutedColor: Color = Color(0xFF9A9BB0),
    activeColor: Color? = null,
    activeContentColor: Color = Color(0xFF14101C),
) {
    if (items.isEmpty()) return
    val safeIndex = selectedIndex.coerceIn(0, items.lastIndex)
    val resolvedActive = activeColor ?: LocalAccent.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .height(64.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(containerColor)
            .border(1.dp, borderColor, RoundedCornerShape(32.dp))
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEachIndexed { index, item ->
            val selected = index == safeIndex
            if (selected) {
                Row(
                    modifier = Modifier
                        .height(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(resolvedActive)
                        .clickable { onSelect(index) }
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        imageVector = item.selectedIcon ?: item.icon,
                        contentDescription = item.label,
                        tint = activeContentColor,
                        modifier = Modifier.size(24.dp),
                    )
                    Text(
                        text = item.label,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = activeContentColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { onSelect(index) },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = mutedColor,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
    }
}
