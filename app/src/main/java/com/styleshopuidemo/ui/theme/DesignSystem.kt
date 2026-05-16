package com.styleshopuidemo.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object StyleShopColors {
    val background = Ivory
    val surface = Color.White.copy(alpha = 0.46f)
    val surfaceStrong = Color.White.copy(alpha = 0.62f)
    val stroke = Color.White.copy(alpha = 0.58f)
    val gold = AntiqueGold
    val goldSoft = SoftGold
    val textPrimary = Espresso
    val textSecondary = Cocoa.copy(alpha = 0.76f)
    val muted = Taupe
    val glow = Champagne.copy(alpha = 0.30f)

    val glassBrush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.34f),
            Porcelain.copy(alpha = 0.18f),
            RoseMilk.copy(alpha = 0.10f),
            Champagne.copy(alpha = 0.08f),
            Color.White.copy(alpha = 0.20f)
        )
    )

    val glassSheen = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.28f),
            Color.White.copy(alpha = 0.04f),
            Champagne.copy(alpha = 0.06f),
            Color.White.copy(alpha = 0.13f)
        )
    )

    val glassEdgeBrush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.80f),
            Color.White.copy(alpha = 0.30f),
            Champagne.copy(alpha = 0.20f),
            Color.White.copy(alpha = 0.52f)
        )
    )

    val goldBrush = Brush.linearGradient(
        colors = listOf(
            SoftGold,
            AntiqueGold,
            Champagne
        )
    )

    val backgroundVeil = Brush.radialGradient(
        colors = listOf(
            Champagne.copy(alpha = 0.18f),
            Color.Transparent
        )
    )
}

object StyleShopDimens {
    val screenPadding = 14.dp
    val sectionGap = 14.dp
    val cardGap = 10.dp
    val bottomBarHeight = 60.dp
    val iconButton = 38.dp
    val productCardWidth = 144.dp
}

object StyleShopShapes {
    val tiny = RoundedCornerShape(10.dp)
    val small = RoundedCornerShape(14.dp)
    val medium = RoundedCornerShape(20.dp)
    val large = RoundedCornerShape(26.dp)
    val pill = RoundedCornerShape(50)
}
