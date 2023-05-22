package com.hanisoft.bestar.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hanisoft.bestar.data.BeStarDataStore
import com.hanisoft.bestar.ui.home.HomeScreen
import com.hanisoft.bestar.ui.login.LoginScreen
import com.hanisoft.bestar.ui.profile.ProfileScreen
import com.hanisoft.bestar.ui.shop.ShopScreen

@Composable
fun BeStarNavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = BeStarDataStore(context)
    val authToken by dataStore.getAuthToken.collectAsState(initial = "")

    NavHost(
        navController = navController,
        startDestination =  if(authToken != "") NavigationScreen.Home.route else NavigationScreen.Login.route
    ) {
        composable(route = NavigationScreen.Home.route) {
            HomeScreen()
        }
        composable(route = NavigationScreen.Profile.route) {
            ProfileScreen(onPopBackStack = { navController.popBackStack()})
        }

        composable(route = NavigationScreen.Login.route) {
            LoginScreen()
        }
        composable(route = NavigationScreen.Shop.route) {
            ShopScreen(onPopBackStack = {navController.popBackStack()})
        }
    }
}