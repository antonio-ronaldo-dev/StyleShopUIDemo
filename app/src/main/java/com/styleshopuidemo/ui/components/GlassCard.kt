package com.styleshopuidemo.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.R
import com.styleshopuidemo.ui.theme.Ivory
import com.styleshopuidemo.ui.theme.ShadowBrown
import com.styleshopuidemo.ui.theme.RoseMilk
import com.styleshopuidemo.ui.theme.StyleShopColors
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = StyleShopShapes.medium,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    elevation: Dp = 8.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current
    val scale by animateFloatAsState(
        targetValue = if (onClick != null && pressed) 0.955f else 1f,
        animationSpec = spring(stiffness = 520f, dampingRatio = 0.66f),
        label = "glass-card-scale"
    )
    val translationY by animateFloatAsState(
        targetValue = if (onClick != null && pressed) 3.4f else 0f,
        animationSpec = spring(stiffness = 540f, dampingRatio = 0.70f),
        label = "glass-card-translation"
    )
    val animatedElevation by animateDpAsState(
        targetValue = if (onClick != null && pressed) (elevation * 0.45f) else elevation,
        animationSpec = spring(stiffness = 520f, dampingRatio = 0.82f),
        label = "glass-card-elevation"
    )
    val borderColor by animateColorAsState(
        targetValue = if (onClick != null && pressed) {
            StyleShopColors.goldSoft.copy(alpha = 0.72f)
        } else {
            StyleShopColors.stroke
        },
        animationSpec = spring(stiffness = 520f, dampingRatio = 0.90f),
        label = "glass-card-border"
    )
    val touchGlowAlpha by animateFloatAsState(
        targetValue = if (onClick != null && pressed) 0.26f else 0f,
        animationSpec = spring(stiffness = 560f, dampingRatio = 0.82f),
        label = "glass-card-touch-glow"
    )

    LaunchedEffect(pressed) {
        if (onClick != null && pressed) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    val clickableModifier = if (onClick != null) {
        Modifier.clickable(
            interactionSource = interaction,
            indication = null,
            onClick = onClick
        )
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.translationY = translationY
            }
            .shadow(
                elevation = animatedElevation,
                shape = shape,
                clip = false,
                ambientColor = ShadowBrown.copy(alpha = 0.16f),
                spotColor = ShadowBrown.copy(alpha = 0.12f)
            )
            .clip(shape)
            .background(StyleShopColors.glassBrush)
            .background(StyleShopColors.glassSheen)
            .border(
                BorderStroke(
                    width = if (pressed) 1.15.dp else 0.9.dp,
                    brush = if (pressed) SolidColor(borderColor) else StyleShopColors.glassEdgeBrush
                ),
                shape
            )
            .then(clickableModifier)
            .padding(contentPadding),
    ) {
        content()
        if (touchGlowAlpha > 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.White.copy(alpha = touchGlowAlpha))
            )
        }
    }
}

@Composable
fun GoldGlassSurface(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = StyleShopShapes.pill,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .shadow(18.dp, shape, clip = false)
            .clip(shape)
            .background(StyleShopColors.goldBrush)
            .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.58f)), shape),
        content = content
    )
}

@Composable
fun AssetImage(
    @DrawableRes imageRes: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1f
) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = contentDescription,
        modifier = modifier.graphicsLayer { this.alpha = alpha },
        contentScale = contentScale
    )
}

@Composable
fun LiquidGlassBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Ivory)
    ) {
        AssetImage(
            imageRes = R.drawable.backgrounds_mobile_standard_background,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.42f
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.84f),
                            RoseMilk.copy(alpha = 0.12f),
                            Color.White.copy(alpha = 0.72f)
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(StyleShopColors.backgroundVeil)
        )
        content()
    }
}
