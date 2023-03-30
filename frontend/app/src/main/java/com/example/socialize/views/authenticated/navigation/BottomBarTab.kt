package com.example.socialize.views.authenticated.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarTab(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarTab(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Locations : BottomBarTab(
        route = "locations",
        title = "Locations",
        icon = Icons.Default.LocationOn
    )

    object Meet : BottomBarTab(
        route = "meet",
        title = "Meet",
        icon = Icons.Default.AccountCircle
    )

    object Settings : BottomBarTab(
        route = "settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )
}