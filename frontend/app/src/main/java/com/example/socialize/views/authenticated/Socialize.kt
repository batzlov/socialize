package com.example.socialize.views.authenticated

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.socialize.models.AuthTokenData
import com.example.socialize.views.authenticated.navigation.BottomBarTab

@Composable
fun Socialize(nc: NavController, userId: Int, authToken: String) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {
        BottomNavGraph(navController, nc, AuthTokenData(authToken, userId))
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val tabs = listOf(
        BottomBarTab.Home,
        BottomBarTab.Locations,
        BottomBarTab.Meet,
        BottomBarTab.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        tabs.forEach { tab ->
            AddItem(
                tab = tab,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    tab: BottomBarTab,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = tab.title)
        },
        icon = {
            Icon(
                imageVector = tab.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == tab.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(tab.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}