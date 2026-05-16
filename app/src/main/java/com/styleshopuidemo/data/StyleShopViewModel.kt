package com.styleshopuidemo.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class StyleShopUiState(
    val products: List<Product> = DemoData.products,
    val favoriteIds: Set<String> = DemoData.favorites.map { it.id }.toSet(),
    val cart: List<CartLine> = emptyList(),
    val createdOrders: List<FakeOrder> = emptyList()
) {
    val cartCount: Int get() = cart.sumOf { it.quantity }
    val subtotalCents: Int
        get() = cart.sumOf { line ->
            (products.firstOrNull { it.id == line.productId }?.priceCents ?: 0) * line.quantity
        }
    val discountCents: Int get() = if (subtotalCents >= 29900) subtotalCents / 10 else 0
    val totalCents: Int get() = (subtotalCents - discountCents).coerceAtLeast(0)
    val subtotal: String get() = formatPrice(subtotalCents)
    val discount: String get() = "-${formatPrice(discountCents)}"
    val total: String get() = formatPrice(totalCents)
}

class StyleShopViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(StyleShopUiState())
    val uiState: StateFlow<StyleShopUiState> = _uiState.asStateFlow()

    fun toggleFavorite(productId: String) {
        _uiState.update { state ->
            val next = if (productId in state.favoriteIds) {
                state.favoriteIds - productId
            } else {
                state.favoriteIds + productId
            }
            state.copy(favoriteIds = next)
        }
    }

    fun addToCart(productId: String, size: String = "M") {
        val product = _uiState.value.products.firstOrNull { it.id == productId } ?: return
        if (product.isPlaceholder) return
        _uiState.update { state ->
            val index = state.cart.indexOfFirst { it.productId == productId && it.size == size }
            val next = if (index >= 0) {
                state.cart.mapIndexed { i, line ->
                    if (i == index) line.copy(quantity = line.quantity + 1) else line
                }
            } else {
                state.cart + CartLine(productId = productId, quantity = 1, size = size)
            }
            state.copy(cart = next)
        }
    }

    fun decreaseCartLine(productId: String, size: String) {
        _uiState.update { state ->
            state.copy(
                cart = state.cart.mapNotNull { line ->
                    if (line.productId == productId && line.size == size) {
                        val nextQuantity = line.quantity - 1
                        if (nextQuantity > 0) line.copy(quantity = nextQuantity) else null
                    } else {
                        line
                    }
                }
            )
        }
    }

    fun removeCartLine(productId: String, size: String) {
        _uiState.update { state ->
            state.copy(cart = state.cart.filterNot { it.productId == productId && it.size == size })
        }
    }

    fun finishCheckout(): String? {
        val state = _uiState.value
        if (state.cart.isEmpty()) return null
        val firstProduct = state.products.firstOrNull { it.id == state.cart.first().productId }
        val orderId = "#SS-${(3000..9999).random()}"
        val order = FakeOrder(
            id = orderId,
            productName = firstProduct?.name ?: "Pedido StyleShop",
            totalCents = state.totalCents,
            status = "Confirmado",
            imageRes = firstProduct?.imageRes ?: com.styleshopuidemo.R.drawable.placeholders_product_image_unavailable,
            progressStep = 1
        )
        _uiState.update {
            it.copy(
                cart = emptyList(),
                createdOrders = listOf(order) + it.createdOrders
            )
        }
        return orderId
    }
}
