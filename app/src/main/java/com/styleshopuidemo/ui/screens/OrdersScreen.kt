package com.styleshopuidemo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.data.DemoData
import com.styleshopuidemo.data.FakeOrder
import com.styleshopuidemo.data.Order
import com.styleshopuidemo.ui.components.GlassButton
import com.styleshopuidemo.ui.components.GlassCard
import com.styleshopuidemo.ui.components.GlassChip
import com.styleshopuidemo.ui.components.OrderCard
import com.styleshopuidemo.ui.components.ScreenTitle
import com.styleshopuidemo.ui.components.SectionHeader
import com.styleshopuidemo.ui.theme.StyleShopColors
import com.styleshopuidemo.ui.theme.StyleShopDimens
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
fun OrdersScreen(
    innerPadding: PaddingValues,
    createdOrders: List<FakeOrder>,
    onTrackingClick: () -> Unit,
    onHelpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sessionOrders = createdOrders.map { fake ->
        Order(
            productName = fake.productName,
            orderNumber = fake.id,
            price = fake.total,
            status = fake.status,
            imageRes = fake.imageRes,
            progressStep = fake.progressStep
        )
    }
    val orders = sessionOrders + DemoData.orders

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(
            start = StyleShopDimens.screenPadding,
            top = 12.dp,
            end = StyleShopDimens.screenPadding,
            bottom = 94.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ScreenTitle(
                title = "Pedidos",
                subtitle = "Acompanhe suas compras",
                modifier = Modifier.statusBarsPadding()
            )
        }
        item {
            val chips = listOf("Todos", "Andamento", "Entregues", "Cancelados")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(chips) { chip ->
                    GlassChip(label = chip, selected = chip == "Todos")
                }
            }
        }
        item {
            OrderCard(
                order = orders.first(),
                featured = true,
                onTrackClick = onTrackingClick
            )
        }
        item {
            SectionHeader(
                title = "Histórico",
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        items(orders.drop(1)) { order ->
            OrderCard(order = order)
        }
        item { SupportOrderCard(onHelpClick = onHelpClick) }
    }
}

@Composable
private fun SupportOrderCard(onHelpClick: () -> Unit) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(14.dp),
        elevation = 6.dp
    ) {
        androidx.compose.foundation.layout.Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlassCard(
                    shape = StyleShopShapes.pill,
                    contentPadding = PaddingValues(10.dp),
                    elevation = 3.dp
                ) {
                    Icon(
                        imageVector = Icons.Rounded.SupportAgent,
                        contentDescription = null,
                        tint = StyleShopColors.gold
                    )
                }
                androidx.compose.foundation.layout.Column(
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(
                        text = "Precisa de ajuda?",
                        style = MaterialTheme.typography.titleMedium,
                        color = StyleShopColors.textPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Atendimento para pedidos e trocas.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = StyleShopColors.textSecondary
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            GlassButton(
                text = "Falar agora",
                modifier = Modifier.fillMaxWidth(),
                onClick = onHelpClick
            )
        }
    }
}
