package com.styleshopuidemo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.data.Benefit
import com.styleshopuidemo.ui.theme.StyleShopColors
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
fun BenefitItem(
    benefit: Benefit,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier.width(150.dp),
        shape = StyleShopShapes.large,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(11.dp),
        elevation = 5.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GlassIconSurface(
                icon = benefit.icon,
                contentDescription = null,
                modifier = Modifier.size(34.dp),
                iconSize = 17.dp
            )
            Spacer(modifier = Modifier.width(9.dp))
            Column {
                Text(
                    text = benefit.title,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = StyleShopColors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = benefit.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = StyleShopColors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
