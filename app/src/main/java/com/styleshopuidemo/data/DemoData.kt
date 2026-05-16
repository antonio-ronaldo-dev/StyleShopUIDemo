package com.styleshopuidemo.data

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Help
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.AcUnit
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material.icons.rounded.Checkroom
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Diamond
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Female
import androidx.compose.material.icons.rounded.LocalMall
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Male
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import com.styleshopuidemo.R

data class Product(
    val id: String,
    val name: String,
    val priceCents: Int,
    @param:DrawableRes val imageRes: Int,
    val category: String,
    val tag: String = "",
    val description: String = "Peça premium com acabamento elegante, textura confortável e visual versátil para compor looks sofisticados.",
    val isPlaceholder: Boolean = false
) {
    val price: String get() = formatPrice(priceCents)
}

data class Category(
    val name: String,
    @param:DrawableRes val imageRes: Int,
    val icon: ImageVector = Icons.Rounded.Checkroom
)

data class Benefit(
    val title: String,
    val subtitle: String,
    val icon: ImageVector
)

data class Order(
    val productName: String,
    val orderNumber: String,
    val price: String,
    val status: String,
    @param:DrawableRes val imageRes: Int,
    val progressStep: Int
)

data class ProfileOption(
    val title: String,
    val subtitle: String,
    val icon: ImageVector
)

data class CartLine(
    val productId: String,
    val quantity: Int,
    val size: String
)

data class FakeOrder(
    val id: String,
    val productName: String,
    val totalCents: Int,
    val status: String,
    @param:DrawableRes val imageRes: Int,
    val progressStep: Int = 1
) {
    val total: String get() = formatPrice(totalCents)
}

fun formatPrice(cents: Int): String {
    val reais = cents / 100
    val centavos = cents % 100
    val grouped = reais.toString().reversed().chunked(3).joinToString(".").reversed()
    return "R$ $grouped,${centavos.toString().padStart(2, '0')}"
}

object DemoData {
    val topCategories = listOf(
        Category("Verão", R.drawable.home_collections_summer, Icons.Rounded.WbSunny),
        Category("Outono", R.drawable.home_collections_fall, Icons.Rounded.Spa),
        Category("Inverno", R.drawable.home_collections_winter, Icons.Rounded.AcUnit),
        Category("Primavera", R.drawable.home_collections_spring, Icons.Rounded.Spa),
        Category("Tendências", R.drawable.home_sections_trends, Icons.AutoMirrored.Rounded.TrendingUp),
        Category("Masculino", R.drawable.home_categories_men, Icons.Rounded.Male),
        Category("Feminino", R.drawable.home_categories_women, Icons.Rounded.Female),
        Category("Calçados", R.drawable.home_categories_shoes, Icons.Rounded.Checkroom),
        Category("Bolsas", R.drawable.home_categories_accessories, Icons.Rounded.LocalMall)
    )

    val categoryGrid = listOf(
        Category("Vestidos", R.drawable.store_news_news_05, Icons.Rounded.Checkroom),
        Category("Blazers", R.drawable.store_highlights_highlight_01, Icons.Rounded.Diamond),
        Category("Camisas", R.drawable.store_news_news_06, Icons.Rounded.Checkroom),
        Category("Jeans", R.drawable.store_highlights_highlight_03, Icons.Rounded.Checkroom),
        Category("Calçados", R.drawable.home_categories_shoes, Icons.Rounded.Checkroom),
        Category("Bolsas", R.drawable.store_highlights_highlight_05, Icons.Rounded.LocalMall),
        Category("Acessórios", R.drawable.store_news_news_04, Icons.Rounded.Diamond),
        Category("Verão", R.drawable.home_collections_summer, Icons.Rounded.WbSunny),
        Category("Inverno", R.drawable.home_collections_winter, Icons.Rounded.AcUnit)
    )

    val products = listOf(
        Product("p001", "Trench Coat Clássico", 68990, R.drawable.store_highlights_highlight_01, "Blazers", "Essencial"),
        Product("p002", "Tricô Verde Soft", 32990, R.drawable.store_highlights_highlight_02, "Inverno", "Novo"),
        Product("p003", "Wide Leg Denim", 38990, R.drawable.store_highlights_highlight_03, "Jeans", "Trend"),
        Product("p004", "Camiseta Premium", 17990, R.drawable.store_highlights_highlight_04, "Masculino", "Básico"),
        Product("p005", "Bolsa Aurora Couro", 57990, R.drawable.store_highlights_highlight_05, "Bolsas", "Luxo"),
        Product("p006", "Blusa Pistache", 28990, R.drawable.store_highlights_highlight_06, "Feminino", "Leve"),
        Product("p007", "Tênis Minimal Nude", 39990, R.drawable.store_highlights_highlight_07, "Calçados", "Favorito"),
        Product("p008", "Vestido Off White", 42990, R.drawable.store_highlights_highlight_08, "Vestidos", "Elegante"),
        Product("p009", "Camisa Seda Terracota", 34990, R.drawable.store_news_news_01, "Camisas", "Chegou"),
        Product("p010", "Bota Viena Black", 49990, R.drawable.store_news_news_02, "Calçados", "Novo"),
        Product("p011", "Jaqueta Areia", 45990, R.drawable.store_news_news_03, "Masculino", "Drop"),
        Product("p012", "Colar Dourado Luz", 21990, R.drawable.store_news_news_04, "Acessórios", "Acessório"),
        Product("p013", "Vestido Midi Fluido", 36990, R.drawable.store_news_news_05, "Vestidos", "Romântico"),
        Product("p014", "Jaqueta Denim Clara", 42990, R.drawable.store_news_news_06, "Jeans", "Urbano"),
        Product("p015", "Óculos Vienna", 24990, R.drawable.store_news_news_07, "Acessórios", "Solar"),
        Product("p016", "Vestido Floral Garden", 39990, R.drawable.store_news_news_08, "Vestidos", "Fresh"),
        Product("p017", "Bolsa Tote Creme", 45990, R.drawable.home_categories_accessories, "Bolsas", "Office"),
        Product("p018", "Sneaker Branco Clean", 35990, R.drawable.home_categories_shoes, "Calçados", "Clean"),
        Product("p019", "Look Masculino Olive", 52990, R.drawable.home_categories_men, "Masculino", "Casual"),
        Product("p020", "Tailleur Feminino", 69990, R.drawable.home_categories_women, "Feminino", "Alfaiataria"),
        Product("p021", "Coleção Verão Linen", 44990, R.drawable.home_collections_summer, "Verão", "Linen"),
        Product("p022", "Casaco Outono Soft", 54990, R.drawable.home_collections_fall, "Outono", "Soft"),
        Product("p023", "Conjunto Tricô Elegance", 45990, R.drawable.home_collections_winter, "Inverno", "Warm"),
        Product("p024", "Blusa Primavera Pistache", 31990, R.drawable.home_collections_spring, "Primavera", "Fresh"),
        Product("p025", "Kit Best Sellers", 78990, R.drawable.home_sections_best_sellers, "Tendências", "Top"),
        Product("p026", "T-shirt New York", 19990, R.drawable.home_sections_new_arrivals, "Camisas", "Drop"),
        Product("p027", "Combo Casual Creme", 62990, R.drawable.home_sections_recommended, "Feminino", "Stylist"),
        Product("p028", "Bolsa Trend Champagne", 48990, R.drawable.home_sections_trends, "Bolsas", "Trend"),
        Product("p029", "Produto em curadoria", 0, R.drawable.placeholders_product_image_unavailable, "Preview", "Carregando", isPlaceholder = true),
        Product("p030", "Drop secreto StyleShop", 0, R.drawable.placeholders_product_image_unavailable, "Preview", "Soon", isPlaceholder = true)
    )

    val highlights = products.take(8)
    val news = products.drop(8).take(8)
    val favorites = listOf(products[0], products[22], products[4], products[17], products[14], products[12])

    val benefits = listOf(
        Benefit("Frete grátis", "acima de R$ 299", Icons.Rounded.LocalShipping),
        Benefit("Até 6x", "sem juros", Icons.Rounded.CreditCard),
        Benefit("Troca fácil", "30 dias", Icons.Rounded.Replay),
        Benefit("10% OFF", "primeira compra", Icons.Rounded.Payments)
    )

    val orders = listOf(
        Order("Trench Coat Clássico", "#SS-2049", "R$ 689,90", "Em trânsito", R.drawable.store_highlights_highlight_01, 3),
        Order("Tricot Gola Alta", "#SS-2037", "R$ 329,90", "Preparando", R.drawable.home_collections_winter, 2),
        Order("Bolsa Aurora Couro", "#SS-2018", "R$ 579,90", "Entregue", R.drawable.store_highlights_highlight_05, 4),
        Order("Tênis Minimal Nude", "#SS-1995", "R$ 399,90", "Saiu para entrega", R.drawable.store_highlights_highlight_07, 3)
    )

    val profileOptions = listOf(
        ProfileOption("Dados pessoais", "Nome, email e telefone", Icons.Rounded.FavoriteBorder),
        ProfileOption("Cupons e descontos", "Benefícios disponíveis", Icons.Rounded.CardGiftcard),
        ProfileOption("Notificações", "Preferências do app", Icons.Rounded.Notifications),
        ProfileOption("Privacidade", "Segurança da conta", Icons.Rounded.Lock),
        ProfileOption("Ajuda e suporte", "Atendimento StyleShop", Icons.AutoMirrored.Rounded.Help),
        ProfileOption("Style Prime", "Benefícios VIP", Icons.Rounded.ShoppingBag)
    )
}
