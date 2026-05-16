package com.styleshopuidemo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.rounded.Diamond
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Style
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.R
import com.styleshopuidemo.data.DemoData
import com.styleshopuidemo.ui.components.AssetImage
import com.styleshopuidemo.ui.components.CategoryCard
import com.styleshopuidemo.ui.components.GlassButton
import com.styleshopuidemo.ui.components.GlassCard
import com.styleshopuidemo.ui.components.GlassChip
import com.styleshopuidemo.ui.components.ScreenTitle
import com.styleshopuidemo.ui.components.SearchBar
import com.styleshopuidemo.ui.components.SectionHeader
import com.styleshopuidemo.ui.theme.StyleShopColors
import com.styleshopuidemo.ui.theme.StyleShopDimens
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
fun CategoriesScreen(
    innerPadding: PaddingValues,
    onSearchClick: () -> Unit,
    onCategoryClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 144.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(
            start = StyleShopDimens.screenPadding,
            top = 12.dp,
            end = StyleShopDimens.screenPadding,
            bottom = 94.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            ScreenTitle(
                title = "Categorias",
                subtitle = "Explore por produto e estilo",
                modifier = Modifier.statusBarsPadding()
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) { SearchBar(onClick = onSearchClick) }
        item(span = { GridItemSpan(maxLineSpan) }) {
            val chips = listOf("Todos", "Roupas", "Calçados", "Acessórios", "Novidades")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(chips) { chip ->
                    GlassChip(label = chip, selected = chip == "Todos")
                }
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            EssentialBanner()
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            SectionHeader(title = "Vitrine")
        }
        items(DemoData.categoryGrid) { category ->
            val index = DemoData.categoryGrid.indexOf(category)
            CategoryCard(
                category = category,
                onClick = { onCategoryClick(index) }
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            SectionHeader(
                title = "Estilos",
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            ExploreStyleButtons()
        }
    }
}

@Composable
private fun EssentialBanner() {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(144.dp),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(0.dp),
        elevation = 9.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AssetImage(
                imageRes = R.drawable.marketing_ad_cards_ad_card_01,
                contentDescription = "Coleção Essenciais",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.08f),
                                Color.Transparent
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                GlassButton(text = "Ver coleção", isPrimary = true)
            }
        }
    }
}

@Composable
private fun ExploreStyleButtons() {
    val styles = listOf(
        "Casual" to Icons.Rounded.Star,
        "Social" to Icons.Rounded.Diamond,
        "Minimal" to Icons.Rounded.Style,
        "Festa" to Icons.Rounded.Event
    )
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(styles) { item ->
            GlassCard(
                modifier = Modifier.size(width = 96.dp, height = 82.dp),
                shape = StyleShopShapes.large,
                contentPadding = PaddingValues(10.dp),
                elevation = 5.dp
            ) {
                androidx.compose.foundation.layout.Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = item.second,
                        contentDescription = null,
                        tint = StyleShopColors.gold,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = item.first,
                        style = MaterialTheme.typography.labelLarge,
                        color = StyleShopColors.textPrimary,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
