package com.styleshopuidemo.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.styleshopuidemo.data.StyleShopViewModel
import com.styleshopuidemo.ui.components.BottomNavBar
import com.styleshopuidemo.ui.components.LiquidGlassBackground
import com.styleshopuidemo.ui.components.bottomNavDestinations
import com.styleshopuidemo.ui.screens.AddressesScreen
import com.styleshopuidemo.ui.screens.BagScreen
import com.styleshopuidemo.ui.screens.CategoriesScreen
import com.styleshopuidemo.ui.screens.CategoryProductsScreen
import com.styleshopuidemo.ui.screens.CheckoutScreen
import com.styleshopuidemo.ui.screens.CouponsScreen
import com.styleshopuidemo.ui.screens.EditProfileScreen
import com.styleshopuidemo.ui.screens.FavoritesScreen
import com.styleshopuidemo.ui.screens.HelpScreen
import com.styleshopuidemo.ui.screens.HomeScreen
import com.styleshopuidemo.ui.screens.LoginScreen
import com.styleshopuidemo.ui.screens.NotificationsScreen
import com.styleshopuidemo.ui.screens.OrdersScreen
import com.styleshopuidemo.ui.screens.PaymentsScreen
import com.styleshopuidemo.ui.screens.PrimeScreen
import com.styleshopuidemo.ui.screens.PrivacyScreen
import com.styleshopuidemo.ui.screens.ProductDetailScreen
import com.styleshopuidemo.ui.screens.ProfileScreen
import com.styleshopuidemo.ui.screens.RegisterScreen
import com.styleshopuidemo.ui.screens.SearchScreen
import com.styleshopuidemo.ui.screens.TrackingScreen

@Composable
fun AppNavigation() {
    val viewModel: StyleShopViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val mainRoutes = bottomNavDestinations.map { it.route }.toSet()

    fun tabIndex(route: String?): Int {
        val plainRoute = route?.substringBefore("/")
        return bottomNavDestinations.indexOfFirst { it.route == plainRoute }.takeIf { it >= 0 } ?: 0
    }

    fun navigateTopLevel(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    LiquidGlassBackground {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentColor = Color.Unspecified,
            bottomBar = {
                if (currentRoute in mainRoutes) {
                    BottomNavBar(
                        currentRoute = currentRoute,
                        onDestinationClick = { route ->
                            if (route != currentRoute) {
                                navigateTopLevel(route)
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.fillMaxSize(),
                enterTransition = {
                    val forward = tabIndex(targetState.destination.route) >= tabIndex(initialState.destination.route)
                    slideInHorizontally(animationSpec = tween(330, easing = FastOutSlowInEasing)) {
                        if (forward) it / 2 else -it / 2
                    } + fadeIn(animationSpec = tween(180))
                },
                exitTransition = {
                    val forward = tabIndex(targetState.destination.route) >= tabIndex(initialState.destination.route)
                    slideOutHorizontally(animationSpec = tween(280, easing = FastOutSlowInEasing)) {
                        if (forward) -it / 3 else it / 3
                    } + fadeOut(animationSpec = tween(150))
                },
                popEnterTransition = {
                    slideInHorizontally(animationSpec = tween(310, easing = FastOutSlowInEasing)) { -it / 2 } +
                        fadeIn(animationSpec = tween(170))
                },
                popExitTransition = {
                    slideOutHorizontally(animationSpec = tween(260, easing = FastOutSlowInEasing)) { it / 3 } +
                        fadeOut(animationSpec = tween(130))
                }
            ) {
                composable("home") {
                    HomeScreen(
                        innerPadding = innerPadding,
                        products = uiState.products,
                        favoriteIds = uiState.favoriteIds,
                        cartCount = uiState.cartCount,
                        onSearchClick = { navController.navigate("search") },
                        onBagClick = { navController.navigate("bag") },
                        onFavoritesClick = { navController.navigate("favorites") },
                        onProductClick = { navController.navigate("product/$it") },
                        onAddToCart = { viewModel.addToCart(it) }
                    )
                }
                composable("categories") {
                    CategoriesScreen(
                        innerPadding = innerPadding,
                        onSearchClick = { navController.navigate("search") },
                        onCategoryClick = { navController.navigate("category/$it") }
                    )
                }
                composable("orders") {
                    OrdersScreen(
                        innerPadding = innerPadding,
                        createdOrders = uiState.createdOrders,
                        onTrackingClick = { navController.navigate("tracking") },
                        onHelpClick = { navController.navigate("help") }
                    )
                }
                composable("profile") {
                    ProfileScreen(
                        innerPadding = innerPadding,
                        onEditProfileClick = { navController.navigate("edit-profile") },
                        onOrdersClick = { navigateTopLevel("orders") },
                        onFavoritesClick = { navController.navigate("favorites") },
                        onAddressesClick = { navController.navigate("addresses") },
                        onPaymentsClick = { navController.navigate("payments") },
                        onCouponsClick = { navController.navigate("coupons") },
                        onNotificationsClick = { navController.navigate("notifications") },
                        onPrivacyClick = { navController.navigate("privacy") },
                        onHelpClick = { navController.navigate("help") },
                        onPrimeClick = { navController.navigate("prime") },
                        onLogoutClick = { navController.navigate("login") }
                    )
                }
                composable("favorites") {
                    FavoritesScreen(
                        innerPadding = innerPadding,
                        products = uiState.products.filter { it.id in uiState.favoriteIds },
                        favoriteIds = uiState.favoriteIds,
                        onProductClick = { navController.navigate("product/$it") }
                    )
                }
                composable("search") {
                    SearchScreen(
                        innerPadding = innerPadding,
                        products = uiState.products,
                        favoriteIds = uiState.favoriteIds,
                        onBack = { navController.popBackStack() },
                        onProductClick = { navController.navigate("product/$it") }
                    )
                }
                composable(
                    route = "product/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.StringType })
                ) { entry ->
                    ProductDetailScreen(
                        innerPadding = innerPadding,
                        productId = entry.arguments?.getString("productId").orEmpty(),
                        products = uiState.products,
                        favoriteIds = uiState.favoriteIds,
                        cartCount = uiState.cartCount,
                        onBack = { navController.popBackStack() },
                        onBagClick = { navController.navigate("bag") },
                        onToggleFavorite = viewModel::toggleFavorite,
                        onAddToCart = viewModel::addToCart,
                        onBuyNow = { productId, size ->
                            viewModel.addToCart(productId, size)
                            navController.navigate("bag")
                        }
                    )
                }
                composable(
                    route = "category/{categoryIndex}",
                    arguments = listOf(navArgument("categoryIndex") { type = NavType.IntType })
                ) { entry ->
                    CategoryProductsScreen(
                        innerPadding = innerPadding,
                        categoryIndex = entry.arguments?.getInt("categoryIndex") ?: 0,
                        products = uiState.products,
                        favoriteIds = uiState.favoriteIds,
                        onBack = { navController.popBackStack() },
                        onProductClick = { navController.navigate("product/$it") }
                    )
                }
                composable("bag") {
                    BagScreen(
                        innerPadding = innerPadding,
                        cart = uiState.cart,
                        products = uiState.products,
                        subtotal = uiState.subtotal,
                        discount = uiState.discount,
                        total = uiState.total,
                        onBack = { navController.popBackStack() },
                        onCheckoutClick = { if (uiState.cart.isNotEmpty()) navController.navigate("checkout") },
                        onProductClick = { navController.navigate("product/$it") },
                        onIncrease = viewModel::addToCart,
                        onDecrease = viewModel::decreaseCartLine,
                        onRemove = viewModel::removeCartLine
                    )
                }
                composable("checkout") {
                    CheckoutScreen(
                        innerPadding = innerPadding,
                        cartCount = uiState.cartCount,
                        subtotal = uiState.subtotal,
                        discount = uiState.discount,
                        total = uiState.total,
                        onBack = { navController.popBackStack() },
                        onFinishClick = {
                            if (viewModel.finishCheckout() != null) {
                                navController.navigate("tracking") {
                                    popUpTo("bag") { inclusive = true }
                                }
                            }
                        }
                    )
                }
                composable("tracking") {
                    TrackingScreen(
                        innerPadding = innerPadding,
                        createdOrders = uiState.createdOrders,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("edit-profile") { EditProfileScreen(innerPadding = innerPadding, onBack = { navController.popBackStack() }) }
                composable("addresses") { AddressesScreen(innerPadding = innerPadding, onBack = { navController.popBackStack() }) }
                composable("payments") { PaymentsScreen(innerPadding = innerPadding, onBack = { navController.popBackStack() }) }
                composable("coupons") { CouponsScreen(innerPadding = innerPadding, onBack = { navController.popBackStack() }) }
                composable("notifications") { NotificationsScreen(innerPadding = innerPadding, onBack = { navController.popBackStack() }) }
                composable("privacy") { PrivacyScreen(innerPadding = innerPadding, onBack = { navController.popBackStack() }) }
                composable("help") { HelpScreen(innerPadding = innerPadding, onBack = { navController.popBackStack() }) }
                composable("prime") { PrimeScreen(innerPadding = innerPadding, onBack = { navController.popBackStack() }) }
                composable("login") {
                    LoginScreen(
                        innerPadding = innerPadding,
                        onBack = { navController.popBackStack() },
                        onRegisterClick = { navController.navigate("register") }
                    )
                }
                composable("register") { RegisterScreen(innerPadding = innerPadding, onBack = { navController.popBackStack() }) }
            }
        }
    }
}
