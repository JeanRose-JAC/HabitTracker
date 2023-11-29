package com.example.habittrackerapp.layout

import Routes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.habittrackerapp.LocalNavController

data class NavBarIcon(
    val route: String,
    val icon: ImageVector,
    val desc: String
)

val items = listOf(
    NavBarIcon(route= Routes.About.route, icon= Icons.Filled.Home, "Home"),
    NavBarIcon(route= Routes.HabitList.route, icon= Icons.Filled.List, "Habits"),
    NavBarIcon(route= Routes.Note.route, icon= Icons.Filled.AddCircle, "Notes"),
    NavBarIcon(route= Routes.SignUpSignIn.route, icon= Icons.Filled.Settings,"Settings")
)

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
                onClick = { navController.navigate(item.route)},
                label={ Text(text = item.desc)}

            )
        }
    }

}