package com.hanisoft.bestar.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationScreen(
        val route: String,
        val title: String,
        val icon: ImageVector
    ) {
        object Home : NavigationScreen(
            route = "home",
            title = "Home",
            icon = Icons.Default.Home
        )

        object Profile : NavigationScreen(
            route = "profile",
            title = "Profile",
            icon = Icons.Default.Person
        )

        object Login : NavigationScreen(
            route = "login",
            title = "login",
            icon = Icons.Default.Notifications
        )
        object Shop : NavigationScreen(
            route = "shop",
            title = "Coins",
            icon = Icons.Default.Star
        )

    }

