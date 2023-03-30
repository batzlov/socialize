package com.example.socialize.views.authenticated

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.socialize.models.AuthTokenData
import com.example.socialize.views.authenticated.navigation.BottomBarTab

@Composable
fun BottomNavGraph(navController: NavHostController, nc: NavController? = null, authTokenData: AuthTokenData) {
    NavHost(
        navController = navController,
        startDestination = BottomBarTab.Home.route
    ) {
        composable(route = BottomBarTab.Home.route) {
            Home(navController)
        }
        composable(route = BottomBarTab.Locations.route) {
            if(nc !== null) {
                Locations(navController = nc)
            } else {
                Locations(navController)
            }
        }
        composable(route = BottomBarTab.Meet.route) {
            if(nc !== null) {
                Meet(navController = nc)
            } else {
                Meet(navController)
            }
        }
        composable(route = BottomBarTab.Settings.route) {
            if(nc !== null) {
                Settings(navController = nc)
            } else {
                Settings(navController)
            }
        }
    }
}