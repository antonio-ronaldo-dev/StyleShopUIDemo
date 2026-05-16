package com.styleshopuidemo.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.ui.theme.StyleShopColors
import com.styleshopuidemo.ui.theme.StyleShopShapes

data class BottomNavDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavDestinations = listOf(
    BottomNavDestination("home", "Início", Icons.Rounded.Home),
    BottomNavDestination("categories", "Categorias", Icons.Rounded.Category),
    BottomNavDestination("orders", "Pedidos", Icons.AutoMirrored.Rounded.ReceiptLong),
    BottomNavDestination("profile", "Perfil", Icons.Rounded.Person)
)

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onDestinationClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = StyleShopShapes.large,
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 5.dp),
            elevation = 10.dp
        ) {
            BoxWithConstraints(modifier = Modifier.matchParentSize()) {
                val activeIndex = bottomNavDestinations
                    .indexOfFirst { it.route == currentRoute }
                    .coerceAtLeast(0)
                val itemWidth = maxWidth / bottomNavDestinations.size
                val bubbleOffset by animateDpAsState(
                    targetValue = itemWidth * activeIndex.toFloat(),
                    animationSpec = spring(stiffness = 260f, dampingRatio = 0.76f),
                    label = "bottom-nav-bubble-offset"
                )

                Box(
                    modifier = Modifier
                        .offset(x = bubbleOffset)
                        .width(itemWidth)
                        .fillMaxHeight()
                        .padding(horizontal = 3.dp)
                        .shadow(
                            elevation = 11.dp,
                            shape = StyleShopShapes.pill,
                            clip = false,
                            ambientColor = StyleShopColors.gold.copy(alpha = 0.18f),
                            spotColor = StyleShopColors.gold.copy(alpha = 0.20f)
                        )
                        .clip(StyleShopShapes.pill)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    StyleShopColors.glow.copy(alpha = 0.78f),
                                    Color.White.copy(alpha = 0.18f),
                                    Color.White.copy(alpha = 0.05f)
                                )
                            )
                        )
                        .border(
                            BorderStroke(1.dp, Color.White.copy(alpha = 0.54f)),
                            StyleShopShapes.pill
                        )
                )

                Row(
                    modifier = Modifier.matchParentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    bottomNavDestinations.forEach { item ->
                        BottomNavItem(
                            item = item,
                            active = currentRoute == item.route,
                            onClick = { onDestinationClick(item.route) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    item: BottomNavDestination,
    active: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current
    val scale by animateFloatAsState(
        targetValue = when {
            pressed -> 0.88f
            active -> 1.07f
            else -> 1f
        },
        animationSpec = spring(stiffness = 500f, dampingRatio = 0.62f),
        label = "bottom-nav-scale"
    )
    val containerScale by animateFloatAsState(
        targetValue = if (pressed) 0.94f else 1f,
        animationSpec = spring(stiffness = 520f, dampingRatio = 0.66f),
        label = "bottom-nav-container-scale"
    )
    val color by animateColorAsState(
        targetValue = if (active) StyleShopColors.gold else StyleShopColors.textSecondary,
        label = "bottom-nav-color"
    )

    LaunchedEffect(pressed) {
        if (pressed) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    Column(
        modifier = modifier
            .height(50.dp)
            .graphicsLayer {
                scaleX = containerScale
                scaleY = containerScale
            }
            .clickable(
                interactionSource = interaction,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 1.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(25.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = color,
                modifier = Modifier.size((19 * scale).dp)
            )
        }
        Text(
            text = item.label,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
