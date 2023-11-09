package com.example.habittrackerapp.layout


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.habittrackerapp.LocalNavController
/**
 * Composable for the shared top bar.
 * There is a back button that brings the user to the previous page.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarScreen(title:String) {
    val navController = LocalNavController.current
    CenterAlignedTopAppBar(
        colors= TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        title={Text(title)},
        navigationIcon = {
            IconButton(onClick = {navController.navigateUp()}){
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go Back"
                )
            }
        }

    )

}