package com.hanisoft.bestar.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hanisoft.bestar.R
import com.hanisoft.bestar.data.BeStarDataStore
import com.hanisoft.bestar.util.BeStarNavGraph
import com.hanisoft.bestar.util.NavigationScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backStackEntry by
    navController.currentBackStackEntryAsState()

    var showBar by remember {
        mutableStateOf(true)
    }
    showBar = when (backStackEntry?.destination?.route) {
        NavigationScreen.Login.route -> false
        else -> true
    }

    Scaffold(
        bottomBar = { if (showBar)BottomBar(navController = navController) }
    ) {
        BeStarNavGraph(navController = navController)
    }
}


@Composable
fun BottomBar(navController: NavHostController) {


    val screens = listOf(
        NavigationScreen.Home,
        NavigationScreen.Shop,
        NavigationScreen.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    BottomNavigation(
        backgroundColor =   Color(0XFFF94C84)
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }

    }
}



@Composable
fun RowScope.AddItem(
    screen: NavigationScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val context = LocalContext.current
    val mainBold = ResourcesCompat.getFont(context, R.font.lilitaone_regular)
    BottomNavigationItem(
        label = {
            Text(text = screen.title )
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon",
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = Color.LightGray,
        selectedContentColor = Color.White,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}