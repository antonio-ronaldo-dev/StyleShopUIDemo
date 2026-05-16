package com.styleshopuidemo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.data.Order
import com.styleshopuidemo.ui.theme.StyleShopColors
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
fun OrderCard(
    order: Order,
    modifier: Modifier = Modifier,
    featured: Boolean = false,
    onTrackClick: (() -> Unit)? = null
) {
    GlassCard(
        modifier = modifier.fillMaxWidth(),
        shape = StyleShopShapes.large,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(if (featured) 14.dp else 12.dp),
        elevation = if (featured) 8.dp else 5.dp
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(if (featured) 64.dp else 56.dp)
                        .clip(StyleShopShapes.medium)
                ) {
                    AssetImage(
                        imageRes = order.imageRes,
                        contentDescription = order.productName,
                        modifier = Modifier.matchParentSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = order.status.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = StyleShopColors.gold,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    Text(
                        text = order.productName,
                        style = MaterialTheme.typography.titleMedium,
                        color = StyleShopColors.textPrimary,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${order.orderNumber} • ${order.price}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = StyleShopColors.textSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (featured) {
                    Spacer(modifier = Modifier.width(8.dp))
                    MiniGlassButton(
                        text = "Rastrear",
                        icon = Icons.Rounded.LocalShipping,
                        onClick = onTrackClick ?: {}
                    )
                }
            }
            if (featured) {
                Spacer(modifier = Modifier.height(12.dp))
                OrderProgress(currentStep = order.progressStep)
            }
        }
    }
}

@Composable
fun OrderProgress(
    currentStep: Int,
    modifier: Modifier = Modifier
) {
    val steps = listOf("Confirmado", "Preparando", "Trânsito", "Entrega")
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        steps.forEachIndexed { index, label ->
            val active = index + 1 <= currentStep
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(StyleShopShapes.pill)
                        .background(if (active) StyleShopColors.gold else Color.White.copy(alpha = 0.70f))
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (active) StyleShopColors.textPrimary else StyleShopColors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
