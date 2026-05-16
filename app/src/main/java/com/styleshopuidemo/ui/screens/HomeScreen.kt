package com.styleshopuidemo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.R
import com.styleshopuidemo.data.Category
import com.styleshopuidemo.data.DemoData
import com.styleshopuidemo.data.Product
import com.styleshopuidemo.ui.components.AssetImage
import com.styleshopuidemo.ui.components.BenefitItem
import com.styleshopuidemo.ui.components.GlassButton
import com.styleshopuidemo.ui.components.GlassCard
import com.styleshopuidemo.ui.components.GlassChip
import com.styleshopuidemo.ui.components.GlassIconButton
import com.styleshopuidemo.ui.components.ProductCard
import com.styleshopuidemo.ui.components.SearchBar
import com.styleshopuidemo.ui.components.SectionHeader
import com.styleshopuidemo.ui.theme.StyleShopDimens
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    products: List<Product>,
    favoriteIds: Set<String>,
    cartCount: Int,
    onSearchClick: () -> Unit,
    onBagClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onProductClick: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val highlights = products.take(10)
    val news = products.drop(10).take(10)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(
            start = StyleShopDimens.screenPadding,
            top = 10.dp,
            end = StyleShopDimens.screenPadding,
            bottom = 90.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            HomeTopBar(
                cartCount = cartCount,
                onFavoritesClick = onFavoritesClick,
                onBagClick = onBagClick
            )
        }
        item { SearchBar(onClick = onSearchClick) }
        item { CategoryCarousel(categories = DemoData.topCategories) }
        item {
            MainCampaignBanner(
                onBuyClick = {
                    products.firstOrNull { !it.isPlaceholder }?.id?.let(onAddToCart)
                }
            )
        }
        item { BenefitsRow() }
        item {
            SectionHeader(
                title = "Destaques",
                action = "Ver tudo"
            )
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(highlights, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        favorite = product.id in favoriteIds,
                        modifier = Modifier.width(StyleShopDimens.productCardWidth),
                        onClick = { onProductClick(product.id) }
                    )
                }
            }
        }
        item {
            SectionHeader(
                title = "Novidades",
                action = "Explorar"
            )
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(news, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        favorite = product.id in favoriteIds,
                        modifier = Modifier.width(StyleShopDimens.productCardWidth),
                        compact = true,
                        onClick = { onProductClick(product.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeTopBar(
    cartCount: Int,
    onFavoritesClick: () -> Unit,
    onBagClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AssetImage(
            imageRes = R.drawable.brand_logos_style_shop_home_light,
            contentDescription = "StyleShop",
            modifier = Modifier
                .width(136.dp)
                .height(38.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.weight(1f))
        GlassIconButton(
            icon = Icons.Rounded.FavoriteBorder,
            contentDescription = "Favoritos",
            onClick = onFavoritesClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        GlassIconButton(
            icon = Icons.Rounded.ShoppingBag,
            contentDescription = "Sacola",
            badgeCount = cartCount,
            onClick = onBagClick
        )
    }
}

@Composable
private fun CategoryCarousel(categories: List<Category>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories) { category ->
            GlassChip(
                label = category.name,
                icon = category.icon,
                selected = category.name == "Outono"
            )
        }
    }
}

@Composable
private fun MainCampaignBanner(onBuyClick: () -> Unit) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(174.dp),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(0.dp),
        elevation = 10.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AssetImage(
                imageRes = R.drawable.marketing_ad_cards_ad_card_04,
                contentDescription = "Coleção Outono Inverno",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.04f),
                                Color.Transparent,
                                Color.White.copy(alpha = 0.05f)
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 14.dp, bottom = 12.dp)
            ) {
                GlassButton(
                    text = "Comprar",
                    isPrimary = true,
                    onClick = onBuyClick
                )
            }
        }
    }
}

@Composable
private fun BenefitsRow() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(DemoData.benefits) { benefit ->
            BenefitItem(benefit = benefit)
        }
    }
}
