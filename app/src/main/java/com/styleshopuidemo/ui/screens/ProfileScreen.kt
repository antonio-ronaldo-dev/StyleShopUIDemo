package com.styleshopuidemo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Diamond
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.styleshopuidemo.R
import com.styleshopuidemo.data.DemoData
import com.styleshopuidemo.ui.components.AssetImage
import com.styleshopuidemo.ui.components.GlassCard
import com.styleshopuidemo.ui.components.GlassIconSurface
import com.styleshopuidemo.ui.components.MiniGlassButton
import com.styleshopuidemo.ui.components.ProfileOptionItem
import com.styleshopuidemo.ui.components.ScreenTitle
import com.styleshopuidemo.ui.components.SectionHeader
import com.styleshopuidemo.ui.theme.StyleShopColors
import com.styleshopuidemo.ui.theme.StyleShopDimens
import com.styleshopuidemo.ui.theme.StyleShopShapes

@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
    onEditProfileClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onAddressesClick: () -> Unit,
    onPaymentsClick: () -> Unit,
    onCouponsClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onHelpClick: () -> Unit,
    onPrimeClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                title = "Perfil",
                subtitle = "Conta e preferências",
                modifier = Modifier.statusBarsPadding()
            )
        }
        item { UserProfileCard(onEditProfileClick = onEditProfileClick) }
        item {
            SectionHeader(
                title = "Atalhos",
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        item {
            ProfileShortcuts(
                onOrdersClick = onOrdersClick,
                onFavoritesClick = onFavoritesClick,
                onAddressesClick = onAddressesClick,
                onPaymentsClick = onPaymentsClick
            )
        }
        items(DemoData.profileOptions.filterNot { it.title == "Style Prime" }) { option ->
            val click = when (option.title) {
                "Cupons e descontos" -> onCouponsClick
                "Notificações" -> onNotificationsClick
                "Privacidade" -> onPrivacyClick
                "Ajuda e suporte" -> onHelpClick
                "Style Prime" -> onPrimeClick
                else -> onEditProfileClick
            }
            ProfileOptionItem(option = option, modifier = Modifier, onClick = click)
        }
        item { PrimeVipCard(onPrimeClick = onPrimeClick) }
        item { LogoutCard(onLogoutClick = onLogoutClick) }
    }
}

@Composable
private fun UserProfileCard(onEditProfileClick: () -> Unit) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(14.dp),
        elevation = 7.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(66.dp)
                    .clip(StyleShopShapes.large)
            ) {
                AssetImage(
                    imageRes = R.drawable.home_categories_men,
                    contentDescription = "Antonio Silva",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            androidx.compose.foundation.layout.Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Antonio Silva",
                    style = MaterialTheme.typography.titleLarge,
                    color = StyleShopColors.textPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = "antonio.silva@email.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = StyleShopColors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                MiniGlassButton(
                    text = "Editar",
                    modifier = Modifier.width(74.dp),
                    onClick = onEditProfileClick
                )
            }
        }
    }
}

@Composable
private fun ProfileShortcuts(
    onOrdersClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onAddressesClick: () -> Unit,
    onPaymentsClick: () -> Unit
) {
    val shortcuts = listOf(
        Triple("Pedidos", Icons.AutoMirrored.Rounded.ReceiptLong, onOrdersClick),
        Triple("Favoritos", Icons.Rounded.Favorite, onFavoritesClick),
        Triple("Endereço", Icons.Rounded.Home, onAddressesClick),
        Triple("Cartões", Icons.Rounded.CreditCard, onPaymentsClick)
    )
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(shortcuts) { shortcut ->
            GlassCard(
                modifier = Modifier.size(width = 92.dp, height = 78.dp),
                shape = StyleShopShapes.large,
                contentPadding = PaddingValues(9.dp),
                elevation = 5.dp,
                onClick = shortcut.third
            ) {
                androidx.compose.foundation.layout.Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = shortcut.second,
                        contentDescription = null,
                        tint = StyleShopColors.gold,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = shortcut.first,
                        style = MaterialTheme.typography.labelLarge,
                        color = StyleShopColors.textPrimary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun PrimeVipCard(onPrimeClick: () -> Unit) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(14.dp),
        elevation = 7.dp,
        onClick = onPrimeClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            GlassIconSurface(
                icon = Icons.Rounded.Diamond,
                contentDescription = null,
                modifier = Modifier.size(44.dp),
                iconSize = 23.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
            androidx.compose.foundation.layout.Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Style Prime VIP",
                    style = MaterialTheme.typography.titleMedium,
                    color = StyleShopColors.textPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Frete grátis e acesso antecipado.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = StyleShopColors.textSecondary
                )
            }
        }
    }
}

@Composable
private fun LogoutCard(onLogoutClick: () -> Unit) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = StyleShopShapes.large,
        contentPadding = PaddingValues(13.dp),
        elevation = 4.dp,
        onClick = onLogoutClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.Logout,
                contentDescription = null,
                tint = StyleShopColors.textSecondary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Sair da conta",
                style = MaterialTheme.typography.titleMedium,
                color = StyleShopColors.textSecondary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
