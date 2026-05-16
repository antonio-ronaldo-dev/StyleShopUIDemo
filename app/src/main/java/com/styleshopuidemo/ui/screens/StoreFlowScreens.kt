package com.styleshopuidemo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Help
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Diamond
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.R
import com.styleshopuidemo.data.CartLine
import com.styleshopuidemo.data.DemoData
import com.styleshopuidemo.data.FakeOrder
import com.styleshopuidemo.data.Product
import com.styleshopuidemo.ui.components.AssetImage
import com.styleshopuidemo.ui.components.GlassButton
import com.styleshopuidemo.ui.components.GlassCard
import com.styleshopuidemo.ui.components.GlassChip
import com.styleshopuidemo.ui.components.GlassIconButton
import com.styleshopuidemo.ui.components.GlassIconSurface
import com.styleshopuidemo.ui.components.ImageSampleArea
import com.styleshopuidemo.ui.components.OrderProgress
import com.styleshopuidemo.ui.components.PlaceholderTextBlock
import com.styleshopuidemo.ui.components.ProductCard
import com.styleshopuidemo.ui.components.ScreenTitle
import com.styleshopuidemo.ui.components.SearchBar
import com.styleshopuidemo.ui.components.rememberAdaptiveOverlayStyle
import com.styleshopuidemo.ui.theme.StyleShopColors
import com.styleshopuidemo.ui.theme.StyleShopDimens
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
private fun FlowContent(
    innerPadding: PaddingValues,
    title: String,
    subtitle: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(
            start = StyleShopDimens.screenPadding,
            top = 10.dp,
            end = StyleShopDimens.screenPadding,
            bottom = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            FlowTopBar(
                title = title,
                subtitle = subtitle,
                onBack = onBack
            )
        }
        content()
    }
}

@Composable
private fun FlowTopBar(
    title: String,
    subtitle: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlassIconButton(
            icon = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = "Voltar",
            onClick = onBack
        )
        Spacer(modifier = Modifier.width(12.dp))
        ScreenTitle(title = title, subtitle = subtitle)
    }
}

@Composable
fun ProductDetailScreen(
    innerPadding: PaddingValues,
    productId: String,
    products: List<Product>,
    favoriteIds: Set<String>,
    cartCount: Int,
    onBack: () -> Unit,
    onBagClick: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    onAddToCart: (String, String) -> Unit,
    onBuyNow: (String, String) -> Unit
) {
    val product = products.firstOrNull { it.id == productId } ?: products.first()
    var selectedSize by rememberSaveable(product.id) { mutableStateOf("M") }
    val favorite = product.id in favoriteIds
    val topIconStyle = rememberAdaptiveOverlayStyle(
        imageRes = product.imageRes,
        sampleArea = ImageSampleArea.TopEnd
    )

    FlowContent(
        innerPadding = innerPadding,
        title = "Produto",
        subtitle = if (product.isPlaceholder) "Prévia em curadoria" else "Detalhes da peça",
        onBack = onBack
    ) {
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = StyleShopShapes.large,
                contentPadding = PaddingValues(8.dp),
                elevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.92f)
                        .clip(StyleShopShapes.large)
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
                                    listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.18f)
                                    )
                                )
                            )
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GlassIconButton(
                            icon = if (favorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                            contentDescription = "Favoritar",
                            tint = topIconStyle.tint,
                            surfaceBrush = topIconStyle.surfaceBrush,
                            borderColor = topIconStyle.borderColor,
                            onClick = { onToggleFavorite(product.id) }
                        )
                        GlassIconButton(
                            icon = Icons.Rounded.ShoppingBag,
                            contentDescription = "Sacola",
                            badgeCount = cartCount,
                            tint = topIconStyle.tint,
                            surfaceBrush = topIconStyle.surfaceBrush,
                            borderColor = topIconStyle.borderColor,
                            onClick = onBagClick
                        )
                    }
                    GlassChip(
                        label = product.tag.ifBlank { "StyleShop" },
                        selected = true,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                    )
                }
            }
        }
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = StyleShopShapes.large,
                contentPadding = PaddingValues(16.dp),
                elevation = 6.dp
            ) {
                Column {
                    if (product.isPlaceholder) {
                        PlaceholderTextBlock(
                            modifier = Modifier.fillMaxWidth(),
                            compact = false
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        PlaceholderTextBlock(
                            modifier = Modifier.fillMaxWidth(0.78f),
                            compact = true
                        )
                    } else {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.headlineMedium,
                            color = StyleShopColors.textPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = product.price,
                            style = MaterialTheme.typography.titleLarge,
                            color = StyleShopColors.gold,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = product.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = StyleShopColors.textSecondary,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 14.dp)
                    ) {
                        items(listOf("PP", "P", "M", "G", "GG")) { size ->
                            GlassChip(
                                label = size,
                                selected = size == selectedSize,
                                onClick = { selectedSize = size }
                            )
                        }
                    }
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GlassButton(
                    text = if (product.isPlaceholder) "Recarregando" else "Adicionar",
                    icon = Icons.Rounded.ShoppingBag,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (!product.isPlaceholder) onAddToCart(product.id, selectedSize)
                    }
                )
                GlassButton(
                    text = "Comprar",
                    icon = Icons.Rounded.Payments,
                    isPrimary = true,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (!product.isPlaceholder) onBuyNow(product.id, selectedSize)
                    }
                )
            }
        }
    }
}

@Composable
fun SearchScreen(
    innerPadding: PaddingValues,
    products: List<Product>,
    favoriteIds: Set<String>,
    onBack: () -> Unit,
    onProductClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(140.dp),
        modifier = Modifier
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
            FlowTopBar("Busca", "Catálogo StyleShop", onBack)
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            SearchBar()
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listOf("Todos", "Casacos", "Bolsas", "Calçados", "Vestidos")) { chip ->
                    GlassChip(label = chip, selected = chip == "Todos")
                }
            }
        }
        items(products, key = { it.id }) { product ->
            ProductCard(
                product = product,
                favorite = product.id in favoriteIds,
                onClick = { onProductClick(product.id) }
            )
        }
    }
}

@Composable
fun CategoryProductsScreen(
    innerPadding: PaddingValues,
    categoryIndex: Int,
    products: List<Product>,
    favoriteIds: Set<String>,
    onBack: () -> Unit,
    onProductClick: (String) -> Unit
) {
    val category = DemoData.categoryGrid.getOrElse(categoryIndex) { DemoData.categoryGrid.first() }
    val filtered = products.filter { it.category == category.name }.ifEmpty {
        products.filter { !it.isPlaceholder }.take(10)
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(140.dp),
        modifier = Modifier
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
            FlowTopBar(category.name, "Peças selecionadas", onBack)
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = StyleShopShapes.large,
                contentPadding = PaddingValues(0.dp),
                elevation = 7.dp
            ) {
                AssetImage(
                    imageRes = category.imageRes,
                    contentDescription = category.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        items(filtered, key = { it.id }) { product ->
            ProductCard(
                product = product,
                favorite = product.id in favoriteIds,
                onClick = { onProductClick(product.id) }
            )
        }
    }
}

@Composable
fun BagScreen(
    innerPadding: PaddingValues,
    cart: List<CartLine>,
    products: List<Product>,
    subtotal: String,
    discount: String,
    total: String,
    onBack: () -> Unit,
    onCheckoutClick: () -> Unit,
    onProductClick: (String) -> Unit,
    onIncrease: (String, String) -> Unit,
    onDecrease: (String, String) -> Unit,
    onRemove: (String, String) -> Unit
) {
    val productById = products.associateBy { it.id }
    val itemCount = cart.sumOf { it.quantity }

    FlowContent(
        innerPadding = innerPadding,
        title = "Sacola",
        subtitle = if (itemCount == 1) "1 item selecionado" else "$itemCount itens selecionados",
        onBack = onBack
    ) {
        if (cart.isEmpty()) {
            item { EmptyBagCard(onBack) }
        } else {
            items(cart, key = { "${it.productId}-${it.size}" }) { line ->
                val product = productById[line.productId] ?: return@items
                BagItem(
                    product = product,
                    line = line,
                    onClick = { onProductClick(product.id) },
                    onIncrease = { onIncrease(product.id, line.size) },
                    onDecrease = { onDecrease(product.id, line.size) },
                    onRemove = { onRemove(product.id, line.size) }
                )
            }
            item {
                SummaryCard(
                    rows = listOf(
                        "Subtotal" to subtotal,
                        "Frete" to "Grátis",
                        "Cupom STYLE10" to discount
                    ),
                    total = total,
                    action = "Ir para checkout",
                    onActionClick = onCheckoutClick
                )
            }
        }
    }
}

@Composable
private fun EmptyBagCard(onExploreClick: () -> Unit) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(18.dp),
        elevation = 6.dp
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Rounded.ShoppingBag,
                contentDescription = null,
                tint = StyleShopColors.gold,
                modifier = Modifier.size(38.dp)
            )
            Text(
                text = "Sua sacola está vazia",
                style = MaterialTheme.typography.titleLarge,
                color = StyleShopColors.textPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = "Escolha uma peça e ela aparece aqui na hora.",
                style = MaterialTheme.typography.bodyMedium,
                color = StyleShopColors.textSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            GlassButton(text = "Explorar produtos", isPrimary = true, onClick = onExploreClick)
        }
    }
}

@Composable
private fun BagItem(
    product: Product,
    line: CartLine,
    onClick: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(10.dp),
        elevation = 5.dp,
        onClick = onClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(StyleShopShapes.medium)
            ) {
                AssetImage(
                    imageRes = product.imageRes,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = StyleShopColors.textPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Tam. ${line.size} • Qtd. ${line.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = StyleShopColors.textSecondary
                )
                Text(
                    text = product.price,
                    style = MaterialTheme.typography.labelLarge,
                    color = StyleShopColors.gold,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                GlassIconButton(Icons.Rounded.Remove, "Diminuir", modifier = Modifier.size(34.dp), onClick = onDecrease)
                GlassIconButton(Icons.Rounded.Add, "Aumentar", modifier = Modifier.size(34.dp), onClick = onIncrease)
                GlassIconButton(Icons.Rounded.DeleteOutline, "Remover", modifier = Modifier.size(34.dp), onClick = onRemove)
            }
        }
    }
}

@Composable
fun CheckoutScreen(
    innerPadding: PaddingValues,
    cartCount: Int,
    subtotal: String,
    discount: String,
    total: String,
    onBack: () -> Unit,
    onFinishClick: () -> Unit
) {
    FlowContent(
        innerPadding = innerPadding,
        title = "Checkout",
        subtitle = "$cartCount itens para revisar",
        onBack = onBack
    ) {
        item { InfoPanel("Entrega", "Rua Alameda Clara, 120 • São Paulo", Icons.Rounded.Home) }
        item { InfoPanel("Pagamento", "Visa final 4820 • 6x sem juros", Icons.Rounded.CreditCard) }
        item { InfoPanel("Prazo", "Receba entre terça e quinta", Icons.Rounded.LocalShipping) }
        item {
            SummaryCard(
                rows = listOf(
                    "Produtos" to subtotal,
                    "Frete" to "Grátis",
                    "Cupom STYLE10" to discount
                ),
                total = total,
                action = "Finalizar pedido",
                onActionClick = onFinishClick
            )
        }
    }
}

@Composable
fun TrackingScreen(
    innerPadding: PaddingValues,
    createdOrders: List<FakeOrder>,
    onBack: () -> Unit
) {
    val fake = createdOrders.firstOrNull()
    val fallback = DemoData.orders.first()
    val title = fake?.productName ?: fallback.productName
    val number = fake?.id ?: fallback.orderNumber
    val status = fake?.status ?: fallback.status
    val progress = fake?.progressStep ?: fallback.progressStep

    FlowContent(
        innerPadding = innerPadding,
        title = "Rastreio",
        subtitle = number,
        onBack = onBack
    ) {
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = StyleShopShapes.large,
                contentPadding = PaddingValues(16.dp),
                elevation = 7.dp
            ) {
                Column {
                    Text(
                        text = status.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = StyleShopColors.gold,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = StyleShopColors.textPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    OrderProgress(currentStep = progress)
                }
            }
        }
        items(
            listOf(
                "Pedido confirmado" to "Pagamento aprovado agora",
                "Preparando" to "Separação no centro StyleShop",
                "Em trânsito" to "Atualização da transportadora",
                "Entrega" to "Previsão para os próximos dias"
            )
        ) { item ->
            InfoPanel(item.first, item.second, Icons.Rounded.CheckCircle)
        }
    }
}

@Composable
fun EditProfileScreen(innerPadding: PaddingValues, onBack: () -> Unit) {
    FlowContent(innerPadding, "Editar perfil", "Dados da conta", onBack) {
        item { InfoPanel("Nome", "Antonio Silva", Icons.Rounded.Edit) }
        item { InfoPanel("Email", "antonio.silva@email.com", Icons.Rounded.Edit) }
        item { InfoPanel("Telefone", "+55 11 99999-2049", Icons.Rounded.Edit) }
        item { GlassButton(text = "Salvar alterações", isPrimary = true, modifier = Modifier.fillMaxWidth()) }
    }
}

@Composable
fun AddressesScreen(innerPadding: PaddingValues, onBack: () -> Unit) {
    FlowContent(innerPadding, "Endereços", "Locais de entrega", onBack) {
        item { InfoPanel("Casa", "Alameda Clara, 120 • São Paulo", Icons.Rounded.Home) }
        item { InfoPanel("Trabalho", "Av. Paulista, 900 • 14º andar", Icons.Rounded.LocalShipping) }
        item { GlassButton(text = "Novo endereço", icon = Icons.Rounded.Add, modifier = Modifier.fillMaxWidth()) }
    }
}

@Composable
fun PaymentsScreen(innerPadding: PaddingValues, onBack: () -> Unit) {
    FlowContent(innerPadding, "Pagamentos", "Cartões e carteiras", onBack) {
        item { InfoPanel("Visa Platinum", "Final 4820 • crédito", Icons.Rounded.CreditCard) }
        item { InfoPanel("Mastercard Gold", "Final 9132 • crédito", Icons.Rounded.CreditCard) }
        item { GlassButton(text = "Adicionar cartão", icon = Icons.Rounded.Add, modifier = Modifier.fillMaxWidth()) }
    }
}

@Composable
fun CouponsScreen(innerPadding: PaddingValues, onBack: () -> Unit) {
    FlowContent(innerPadding, "Cupons", "Descontos disponíveis", onBack) {
        item { CouponCard("STYLE10", "10% OFF na primeira compra") }
        item { CouponCard("FRETEGRATIS", "Frete grátis acima de R$ 299") }
        item { CouponCard("PRIMEVIP", "Benefício exclusivo Style Prime") }
    }
}

@Composable
fun NotificationsScreen(innerPadding: PaddingValues, onBack: () -> Unit) {
    FlowContent(innerPadding, "Notificações", "Preferências do app", onBack) {
        item { InfoPanel("Ofertas especiais", "Ativado", Icons.Rounded.Notifications) }
        item { InfoPanel("Status de pedido", "Ativado", Icons.Rounded.LocalShipping) }
        item { InfoPanel("Novidades", "Ativado", Icons.Rounded.ShoppingBag) }
    }
}

@Composable
fun PrivacyScreen(innerPadding: PaddingValues, onBack: () -> Unit) {
    FlowContent(innerPadding, "Privacidade", "Segurança da conta", onBack) {
        item { InfoPanel("Login seguro", "Biometria e senha protegida", Icons.Rounded.Lock) }
        item { InfoPanel("Dados pessoais", "Controle do compartilhamento", Icons.Rounded.Lock) }
        item { InfoPanel("Sessões ativas", "1 dispositivo conectado", Icons.Rounded.CheckCircle) }
    }
}

@Composable
fun HelpScreen(innerPadding: PaddingValues, onBack: () -> Unit) {
    FlowContent(innerPadding, "Ajuda", "Suporte StyleShop", onBack) {
        item { InfoPanel("Trocas e devoluções", "Prazos e condições", Icons.AutoMirrored.Rounded.Help) }
        item { InfoPanel("Falar com atendente", "Resposta em poucos minutos", Icons.Rounded.SupportAgent) }
        item { InfoPanel("Perguntas frequentes", "Pedidos, pagamentos e entregas", Icons.Rounded.Search) }
        item { GlassButton(text = "Abrir chat", icon = Icons.Rounded.SupportAgent, isPrimary = true, modifier = Modifier.fillMaxWidth()) }
    }
}

@Composable
fun PrimeScreen(innerPadding: PaddingValues, onBack: () -> Unit) {
    FlowContent(innerPadding, "Style Prime", "Experiência VIP", onBack) {
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = StyleShopShapes.large,
                contentPadding = PaddingValues(18.dp),
                elevation = 9.dp
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Rounded.Diamond,
                        contentDescription = null,
                        tint = StyleShopColors.gold,
                        modifier = Modifier.size(42.dp)
                    )
                    Text(
                        text = "Style Prime VIP",
                        style = MaterialTheme.typography.headlineMedium,
                        color = StyleShopColors.textPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(
                        text = "Frete grátis, ofertas exclusivas e acesso antecipado.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = StyleShopColors.textSecondary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
        item { InfoPanel("Frete grátis", "Sem mínimo para membros", Icons.Rounded.LocalShipping) }
        item { InfoPanel("Drops antecipados", "Acesso antes da vitrine geral", Icons.Rounded.CardGiftcard) }
        item { GlassButton(text = "Assinar Prime", isPrimary = true, modifier = Modifier.fillMaxWidth()) }
    }
}

@Composable
fun LoginScreen(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    onRegisterClick: () -> Unit
) {
    FlowContent(innerPadding, "Entrar", "Acesse sua conta", onBack) {
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = StyleShopShapes.large,
                contentPadding = PaddingValues(18.dp),
                elevation = 8.dp
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AssetImage(
                        imageRes = R.drawable.brand_logos_style_shop_home_light,
                        contentDescription = "StyleShop",
                        modifier = Modifier
                            .width(150.dp)
                            .height(44.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    InfoPanel("Email", "antonio.silva@email.com", Icons.Rounded.Edit)
                    Spacer(modifier = Modifier.height(10.dp))
                    InfoPanel("Senha", "••••••••", Icons.Rounded.Lock)
                    Spacer(modifier = Modifier.height(16.dp))
                    GlassButton(text = "Entrar", isPrimary = true, modifier = Modifier.fillMaxWidth(), onClick = onBack)
                    Spacer(modifier = Modifier.height(10.dp))
                    GlassButton(text = "Criar conta", modifier = Modifier.fillMaxWidth(), onClick = onRegisterClick)
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(innerPadding: PaddingValues, onBack: () -> Unit) {
    FlowContent(innerPadding, "Criar conta", "Cadastro visual", onBack) {
        item { InfoPanel("Nome completo", "Antonio Silva", Icons.Rounded.Edit) }
        item { InfoPanel("Email", "antonio.silva@email.com", Icons.Rounded.Edit) }
        item { InfoPanel("Senha", "Mínimo 8 caracteres", Icons.Rounded.Lock) }
        item { GlassButton(text = "Finalizar cadastro", isPrimary = true, modifier = Modifier.fillMaxWidth(), onClick = onBack) }
    }
}

@Composable
private fun InfoPanel(title: String, subtitle: String, icon: ImageVector) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(14.dp),
        elevation = 5.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GlassIconSurface(icon = icon, contentDescription = null, modifier = Modifier.size(38.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = StyleShopColors.textPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = StyleShopColors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun CouponCard(code: String, description: String) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(16.dp),
        elevation = 6.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Rounded.CardGiftcard,
                contentDescription = null,
                tint = StyleShopColors.gold,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = code,
                    style = MaterialTheme.typography.titleMedium,
                    color = StyleShopColors.textPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = StyleShopColors.textSecondary
                )
            }
            GlassChip(label = "Usar", selected = true)
        }
    }
}

@Composable
private fun SummaryCard(
    rows: List<Pair<String, String>>,
    total: String,
    action: String,
    onActionClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(16.dp),
        elevation = 7.dp
    ) {
        Column {
            rows.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(row.first, style = MaterialTheme.typography.bodyMedium, color = StyleShopColors.textSecondary)
                    Text(row.second, style = MaterialTheme.typography.bodyMedium, color = StyleShopColors.textPrimary)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total", style = MaterialTheme.typography.titleMedium, color = StyleShopColors.textPrimary, fontWeight = FontWeight.Bold)
                Text(total, style = MaterialTheme.typography.titleMedium, color = StyleShopColors.gold, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(14.dp))
            GlassButton(
                text = action,
                isPrimary = true,
                modifier = Modifier.fillMaxWidth(),
                onClick = onActionClick
            )
        }
    }
}
