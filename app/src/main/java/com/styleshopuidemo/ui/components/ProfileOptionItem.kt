package com.styleshopuidemo.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.data.ProfileOption
import com.styleshopuidemo.ui.theme.StyleShopColors
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
fun ProfileOptionItem(
    option: ProfileOption,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    GlassCard(
        modifier = modifier.fillMaxWidth(),
        shape = StyleShopShapes.large,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(12.dp),
        elevation = 4.dp,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlassIconSurface(
                icon = option.icon,
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                iconSize = 18.dp
            )
            Spacer(modifier = Modifier.width(10.dp))
            androidx.compose.foundation.layout.Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = option.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = StyleShopColors.textPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = option.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = StyleShopColors.textSecondary
                )
            }
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = StyleShopColors.textSecondary
            )
        }
    }
}
