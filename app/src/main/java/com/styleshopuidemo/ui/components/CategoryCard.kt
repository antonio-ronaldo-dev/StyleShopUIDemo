package com.styleshopuidemo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.data.Category
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
fun CategoryCard(
    category: Category,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val overlayStyle = rememberAdaptiveOverlayStyle(
        imageRes = category.imageRes,
        sampleArea = ImageSampleArea.TopStart
    )
    GlassCard(
        modifier = modifier,
        shape = StyleShopShapes.large,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp),
        elevation = 7.dp,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.94f)
                .clip(StyleShopShapes.medium)
        ) {
            AssetImage(
                imageRes = category.imageRes,
                contentDescription = category.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.24f)
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .size(30.dp),
                contentAlignment = Alignment.Center
            ) {
                GlassIconSurface(
                    icon = category.icon,
                    contentDescription = null,
                    tint = overlayStyle.tint,
                    iconSize = 16.dp,
                    surfaceBrush = overlayStyle.surfaceBrush,
                    borderColor = overlayStyle.borderColor,
                    modifier = Modifier.matchParentSize()
                )
            }
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
        }
    }
}
