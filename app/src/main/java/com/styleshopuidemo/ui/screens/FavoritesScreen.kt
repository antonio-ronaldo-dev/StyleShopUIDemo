package com.styleshopuidemo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.data.Product
import com.styleshopuidemo.ui.components.AssetImage
import com.styleshopuidemo.ui.components.GlassCard
import com.styleshopuidemo.ui.components.GlassChip
import com.styleshopuidemo.ui.components.GlassIconSurface
import com.styleshopuidemo.ui.components.ProductCard
import com.styleshopuidemo.ui.components.ScreenTitle
import com.styleshopuidemo.ui.components.SectionHeader
import com.styleshopuidemo.ui.theme.StyleShopColors
import com.styleshopuidemo.ui.theme.StyleShopDimens
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
fun FavoritesScreen(
    innerPadding: PaddingValues,
    products: List<Product>,
    favoriteIds: Set<String>,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(
            start = StyleShopDimens.screenPadding,
            top = 10.dp,
            end = StyleShopDimens.screenPadding,
            bottom = 28.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            ScreenTitle(
                title = "Favoritos",
                subtitle = "Salvos para você",
                modifier = Modifier.statusBarsPadding()
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            val chips = listOf("Todos", "Roupas", "Calçados", "Bolsas", "Acessórios")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(chips) { chip ->
                    GlassChip(label = chip, selected = chip == "Todos")
                }
            }
        }
        if (products.isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                EmptyFavoritesCard()
            }
        } else {
            items(products, key = { it.id }) { product ->
                ProductCard(
                    product = product,
                    favorite = product.id in favoriteIds,
                    onClick = { onProductClick(product.id) }
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                SectionHeader(
                    title = "Lista de desejos",
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(products, key = { it.id }) { product ->
                        WishlistMiniCard(product = product)
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyFavoritesCard() {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(18.dp),
        elevation = 6.dp
    ) {
        androidx.compose.foundation.layout.Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = null,
                tint = StyleShopColors.gold,
                modifier = Modifier.size(34.dp)
            )
            Text(
                text = "Nenhum favorito ainda",
                style = MaterialTheme.typography.titleLarge,
                color = StyleShopColors.textPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = "Toque no coração de um produto para guardar.",
                style = MaterialTheme.typography.bodyMedium,
                color = StyleShopColors.textSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun WishlistMiniCard(product: Product) {
    GlassCard(
        modifier = Modifier.size(width = 124.dp, height = 144.dp),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(8.dp),
        elevation = 5.dp
    ) {
        androidx.compose.foundation.layout.Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.18f)
                    .clip(StyleShopShapes.medium)
            ) {
                AssetImage(
                    imageRes = product.imageRes,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                GlassIconSurface(
                    icon = Icons.Rounded.Favorite,
                    contentDescription = null,
                    tint = StyleShopColors.gold,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(26.dp),
                    iconSize = 14.dp
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.labelLarge,
                color = StyleShopColors.textPrimary,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = product.price,
                style = MaterialTheme.typography.bodyMedium,
                color = StyleShopColors.gold,
                maxLines = 1
            )
        }
    }
}
