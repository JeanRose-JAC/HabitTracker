package com.example.habittrackerapp.layout

import Routes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.habittrackerapp.LocalNavController

data class NavBarIcon(
    val route: String,
    val icon: ImageVector
)

val items = listOf(
    NavBarIcon(route= Routes.About.route, icon= Icons.Filled.Home),
    NavBarIcon(route= Routes.SignUp.route, icon= Icons.Filled.Create),)

/**
 * Composable for the shared bottom bar.
 * Allows user to navigate to the about and sign up page
 */
@Composable
fun BottomBarScreen() {
    val navController = LocalNavController.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEachIndexed{index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.route) },
                selected = currentDestination?.hierarchy?.any {
                    currentDestination.route?.substringBefore('/') ==
                            item.route.substringBefore('/') } == true,
                onClick = { navController.navigate(item.route)}

            )
        }
    }

}