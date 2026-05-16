package com.styleshopuidemo.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.ui.theme.StyleShopColors
import com.styleshopuidemo.ui.theme.StyleShopDimens
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
fun GlassButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: ImageVector? = null,
    contentDescription: String? = text,
    isPrimary: Boolean = false,
    onClick: () -> Unit = {}
) {
    val shape = StyleShopShapes.pill
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.90f else 1f,
        animationSpec = spring(stiffness = 520f, dampingRatio = 0.62f),
        label = "glass-button-scale"
    )
    val translationY by animateFloatAsState(
        targetValue = if (pressed) 3.2f else 0f,
        animationSpec = spring(stiffness = 540f, dampingRatio = 0.68f),
        label = "glass-button-translation"
    )
    val elevation by animateDpAsState(
        targetValue = if (pressed) 2.dp else if (isPrimary) 10.dp else 6.dp,
        animationSpec = spring(stiffness = 520f, dampingRatio = 0.78f),
        label = "glass-button-elevation"
    )
    val touchGlowAlpha by animateFloatAsState(
        targetValue = if (pressed) 0.28f else 0f,
        animationSpec = spring(stiffness = 560f, dampingRatio = 0.78f),
        label = "glass-button-touch-glow"
    )

    LaunchedEffect(pressed) {
        if (pressed) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.translationY = translationY
            }
            .shadow(elevation, shape, clip = false)
            .clip(shape)
            .background(if (isPrimary) StyleShopColors.goldBrush else StyleShopColors.glassBrush)
            .then(if (isPrimary) Modifier else Modifier.background(StyleShopColors.glassSheen))
            .border(
                BorderStroke(1.dp, Color.White.copy(alpha = if (isPrimary) 0.62f else 0.50f)),
                shape
            )
            .clickable(
                interactionSource = interaction,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        if (touchGlowAlpha > 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.White.copy(alpha = touchGlowAlpha))
            )
        }
        Row(
            modifier = Modifier
                .defaultMinSize(minHeight = StyleShopDimens.iconButton)
                .padding(horizontal = if (text == null) 0.dp else 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = if (isPrimary) Color.White else StyleShopColors.gold,
                    modifier = Modifier.size(20.dp)
                )
            }
            if (text != null) {
                Text(
                    text = text,
                    color = if (isPrimary) Color.White else StyleShopColors.textPrimary,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = if (icon != null) Modifier.padding(start = 8.dp) else Modifier
                )
            }
        }
    }
}

@Composable
fun GlassIconButton(
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    badgeCount: Int = 0,
    tint: Color = StyleShopColors.gold,
    surfaceBrush: Brush? = null,
    borderColor: Color = Color.White.copy(alpha = 0.42f),
    onClick: () -> Unit = {}
) {
    Box(modifier = modifier.size(34.dp), contentAlignment = Alignment.Center) {
        GlassButtonShell(
            modifier = Modifier.matchParentSize(),
            isPrimary = false,
            onClick = onClick,
            transparent = true,
            surfaceBrush = surfaceBrush ?: defaultIconGlassBrush(),
            borderColor = borderColor
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = tint,
                modifier = Modifier.size(17.dp)
            )
        }
        if (badgeCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 3.dp, y = (-3).dp)
                    .size(17.dp)
                    .clip(StyleShopShapes.pill)
                    .background(StyleShopColors.goldBrush)
                    .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.72f)), StyleShopShapes.pill),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badgeCount.coerceAtMost(9).toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MiniGlassButton(
    text: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    isPrimary: Boolean = false,
    onClick: () -> Unit = {}
) {
    val shape = StyleShopShapes.pill
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.93f else 1f,
        animationSpec = spring(stiffness = 540f, dampingRatio = 0.66f),
        label = "mini-glass-scale"
    )
    val touchGlowAlpha by animateFloatAsState(
        targetValue = if (pressed) 0.14f else 0f,
        animationSpec = spring(stiffness = 560f, dampingRatio = 0.82f),
        label = "mini-glass-glow"
    )

    LaunchedEffect(pressed) {
        if (pressed) haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
    }

    Box(
        modifier = modifier
            .height(30.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(
                elevation = if (isPrimary) 4.dp else 1.5.dp,
                shape = shape,
                clip = false,
                ambientColor = StyleShopColors.textPrimary.copy(alpha = 0.035f),
                spotColor = StyleShopColors.textPrimary.copy(alpha = 0.05f)
            )
            .clip(shape)
            .background(if (isPrimary) StyleShopColors.goldBrush else compactGlassBrush())
            .border(
                BorderStroke(1.dp, Color.White.copy(alpha = if (isPrimary) 0.54f else 0.38f)),
                shape
            )
            .clickable(interactionSource = interaction, indication = null, onClick = onClick)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        if (touchGlowAlpha > 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.White.copy(alpha = touchGlowAlpha))
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = if (isPrimary) Color.White else StyleShopColors.gold,
                    modifier = Modifier.size(14.dp)
                )
            }
            Text(
                text = text,
                color = if (isPrimary) Color.White else StyleShopColors.textPrimary,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                modifier = if (icon != null) Modifier.padding(start = 6.dp) else Modifier
            )
        }
    }
}

@Composable
fun GlassIconSurface(
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = StyleShopColors.gold,
    iconSize: Dp = 18.dp,
    surfaceBrush: Brush = defaultIconGlassBrush(),
    borderColor: Color = Color.White.copy(alpha = 0.42f)
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = StyleShopShapes.pill,
                clip = false,
                ambientColor = StyleShopColors.textPrimary.copy(alpha = 0.055f),
                spotColor = StyleShopColors.gold.copy(alpha = 0.08f)
            )
            .clip(StyleShopShapes.pill)
            .background(surfaceBrush)
            .border(BorderStroke(1.dp, borderColor), StyleShopShapes.pill),
        contentAlignment = Alignment.Center
    ) {
        FullGlassIconLayers()
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
private fun GlassButtonShell(
    modifier: Modifier,
    isPrimary: Boolean,
    transparent: Boolean = false,
    surfaceBrush: Brush? = null,
    borderColor: Color? = null,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = StyleShopShapes.pill
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.88f else 1f,
        animationSpec = spring(stiffness = 500f, dampingRatio = 0.58f),
        label = "icon-button-scale"
    )
    val translationY by animateFloatAsState(
        targetValue = if (pressed) 3.2f else 0f,
        animationSpec = spring(stiffness = 540f, dampingRatio = 0.66f),
        label = "icon-button-translation"
    )
    val elevation by animateDpAsState(
        targetValue = if (pressed) 0.5.dp else 1.8.dp,
        animationSpec = spring(stiffness = 520f, dampingRatio = 0.78f),
        label = "icon-button-elevation"
    )
    val touchGlowAlpha by animateFloatAsState(
        targetValue = if (pressed) 0.18f else 0f,
        animationSpec = spring(stiffness = 560f, dampingRatio = 0.76f),
        label = "icon-button-glow"
    )

    LaunchedEffect(pressed) {
        if (pressed) haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.translationY = translationY
            }
            .shadow(elevation, shape, clip = false)
            .clip(shape)
            .background(
                when {
                    isPrimary -> StyleShopColors.goldBrush
                    transparent -> surfaceBrush ?: defaultIconGlassBrush()
                    else -> StyleShopColors.glassBrush
                }
            )
            .border(
                BorderStroke(
                    1.dp,
                    borderColor ?: Color.White.copy(alpha = if (transparent) 0.55f else 0.78f)
                ),
                shape
            )
            .clickable(interactionSource = interaction, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (transparent && !isPrimary) {
            FullGlassIconLayers(pressed = pressed)
        }
        if (touchGlowAlpha > 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.White.copy(alpha = touchGlowAlpha))
            )
        }
        content()
    }
}

@Composable
private fun BoxScope.FullGlassIconLayers(pressed: Boolean = false) {
    Box(
        modifier = Modifier
            .matchParentSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = if (pressed) 0.42f else 0.34f),
                        Color.White.copy(alpha = 0.08f),
                        Color.Transparent,
                        StyleShopColors.goldSoft.copy(alpha = 0.08f),
                        Color.White.copy(alpha = if (pressed) 0.18f else 0.12f)
                    )
                )
            )
    )
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth(0.72f)
            .fillMaxHeight(0.30f)
            .padding(top = 2.dp)
            .clip(StyleShopShapes.pill)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.38f),
                        Color.White.copy(alpha = 0.04f),
                        Color.Transparent
                    )
                )
            )
    )
    Box(
        modifier = Modifier
            .matchParentSize()
            .padding(1.5.dp)
            .border(
                BorderStroke(
                    0.7.dp,
                    Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.70f),
                            Color.White.copy(alpha = 0.15f),
                            StyleShopColors.goldSoft.copy(alpha = 0.18f),
                            Color.White.copy(alpha = 0.44f)
                        )
                    )
                ),
                StyleShopShapes.pill
            )
    )
}

private fun defaultIconGlassBrush(): Brush = Brush.linearGradient(
    colors = listOf(
        Color.White.copy(alpha = 0.30f),
        Color.White.copy(alpha = 0.12f),
        StyleShopColors.goldSoft.copy(alpha = 0.08f),
        Color.White.copy(alpha = 0.17f)
    )
)

private fun compactGlassBrush(): Brush = Brush.linearGradient(
    colors = listOf(
        Color.White.copy(alpha = 0.22f),
        Color.White.copy(alpha = 0.08f),
        StyleShopColors.goldSoft.copy(alpha = 0.04f)
    )
)
