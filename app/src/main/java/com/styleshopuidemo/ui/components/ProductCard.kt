package com.styleshopuidemo.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.data.Product
import com.styleshopuidemo.ui.theme.StyleShopColors
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    favorite: Boolean = false,
    compact: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val overlayStyle = rememberAdaptiveOverlayStyle(
        imageRes = product.imageRes,
        sampleArea = ImageSampleArea.TopEnd
    )
    var visible by remember(product.id) { mutableStateOf(false) }
    LaunchedEffect(product.id) { visible = true }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 260),
        label = "product-card-alpha"
    )
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.965f,
        animationSpec = spring(stiffness = 420f, dampingRatio = 0.86f),
        label = "product-card-entry-scale"
    )

    GlassCard(
        modifier = modifier.graphicsLayer {
            this.alpha = alpha
            scaleX = scale
            scaleY = scale
            translationY = (1f - alpha) * 12f
        },
        shape = StyleShopShapes.large,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp),
        elevation = 7.dp,
        onClick = onClick
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(StyleShopShapes.medium)
            ) {
                AssetImage(
                    imageRes = product.imageRes,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.08f)
                                )
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(7.dp)
                        .size(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    GlassIconSurface(
                        icon = if (favorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = null,
                        tint = overlayStyle.tint,
                        iconSize = 16.dp,
                        surfaceBrush = overlayStyle.surfaceBrush,
                        borderColor = overlayStyle.borderColor,
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
            Spacer(modifier = Modifier.height(9.dp))
            if (product.isPlaceholder) {
                PlaceholderTextBlock(compact = compact)
            } else {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = StyleShopColors.textPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = if (compact) 1 else 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.price,
                        style = MaterialTheme.typography.bodyMedium,
                        color = StyleShopColors.gold,
                        fontWeight = FontWeight.Bold
                    )
                    if (product.tag.isNotBlank()) {
                        Text(
                            text = product.tag,
                            style = MaterialTheme.typography.labelSmall,
                            color = StyleShopColors.textSecondary,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceholderTextBlock(
    modifier: Modifier = Modifier,
    compact: Boolean = false
) {
    Column(modifier = modifier) {
        ShimmerLine(
            modifier = Modifier
                .fillMaxWidth(0.84f)
                .height(16.dp)
        )
        if (!compact) {
            Spacer(modifier = Modifier.height(6.dp))
            ShimmerLine(
                modifier = Modifier
                    .fillMaxWidth(0.62f)
                    .height(14.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        ShimmerLine(
            modifier = Modifier
                .fillMaxWidth(0.42f)
                .height(12.dp)
        )
    }
}

@Composable
fun ShimmerLine(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "placeholder-shimmer")
    val shine by transition.animateFloat(
        initialValue = 0.24f,
        targetValue = 0.62f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 760),
            repeatMode = RepeatMode.Reverse
        ),
        label = "placeholder-shimmer-alpha"
    )
    Box(
        modifier = modifier
            .clip(StyleShopShapes.pill)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = shine * 0.42f),
                        StyleShopColors.goldSoft.copy(alpha = shine * 0.28f),
                        Color.White.copy(alpha = shine * 0.56f)
                    )
                )
            )
    )
}
