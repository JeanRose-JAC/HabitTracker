package com.example.habittrackerapp.layout


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModel
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModelSavedFactory
import com.example.habittrackerapp.model.userViewModel.UserViewModel
import com.example.habittrackerapp.model.userViewModel.UserViewModelFactory
import com.example.habittrackerapp.settings.SignOutConfirmationDialog
import com.example.habittrackerapp.settings.SignOutUser

/**
 * Composable for the shared top bar.
 * There is a back button that brings the user to the previous page.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarScreen(title:String, savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory()),
                 MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory()),
                 authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())
) {
    val myUiState by savedUserViewModel.uiState.collectAsState()
    val navController = LocalNavController.current
    var expanded by rememberSaveable { mutableStateOf(false) }
    var clicked by rememberSaveable { mutableStateOf(false) }
    val userData = data.current;
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go Back"
                )
            }


        }, actions = {
            IconButton(onClick = { navController.navigate(Routes.About.route) }) {
                Icon(
                    painter = rememberAsyncImagePainter(
                        R.drawable.logo3,
                        contentScale = ContentScale.FillWidth
                    ),
                    contentDescription = "Logo",
                    tint = Color.Unspecified
                )
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More info"
                )

            }
        }

    )
    if (myUiState.email!="empty") {
        //see this drop down menu if logged in -->log out and setting
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopEnd)
                .padding(end=20.dp, top=50.dp)
        ) {
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = { Text("Profile") },
                    onClick = {navController.navigate(Routes.Profile.route)
                    expanded=false})
                DropdownMenuItem(text = { Text("Sign Out") },
                    onClick = {
                    expanded=false
                    clicked=true
                    })
                DropdownMenuItem(
                    text = { Text("Setting")},
                    onClick = {navController.navigate(Routes.Setting.route)
                        expanded=false})

            }

        }
    } else if(myUiState.email=="empty" ){
        //see this drop down menu if not logged in -->log in and setting
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopEnd)
                .padding(end=20.dp, top=50.dp)
        ) {
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = { Text("Sign in") },
                    onClick = {
                        navController.navigate(Routes.SignIn.route)
                        expanded=false}
                )
                DropdownMenuItem(
                    text = { Text("Setting")},
                    onClick = {
                        navController.navigate(Routes.Policy.route)
                        expanded=false}
                )
            }

        }
    }

    if(clicked){
        SignOutConfirmationDialog(  onSignOutConfirm = {

            clicked = false
            userData.Email="";
            savedUserViewModel.saveEmailAndPassword("empty", "empty")
            authViewModel.signOut();
            navController.navigate(Routes.About.route)

        },
            onSignOutCancel = { clicked = false },
        )}

}


