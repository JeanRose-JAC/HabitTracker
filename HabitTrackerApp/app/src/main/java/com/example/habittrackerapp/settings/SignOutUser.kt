package com.example.habittrackerapp.settings

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModel
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModelSavedFactory


/**
 * This function allows the user to signOut, and we have a dialog to make sure that they
 * want to signOut.
 * @param authViewModel this is used to signOut the user of the app and remove their information
 * @param savedUserViewModel this remove the user information and when they run the app again they wont be signIn
 */
@Composable
fun SignOutUser(authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory()),
                savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory())
) {
    val navController = LocalNavController.current
    val userData = data.current;
    var popupControl by rememberSaveable { mutableStateOf(false) }

    Button(onClick = {popupControl=true}) {
        Text("SignOut Account")
    }

    if(popupControl){


        SignOutConfirmationDialog(
            onSignOutConfirm = {

                popupControl = false
                userData.Email="";
                savedUserViewModel.saveEmailAndPassword("empty", "empty")
                authViewModel.signOut();
                navController.navigate(Routes.About.route)

            },
            onSignOutCancel = { popupControl = false },
        )
    }

}

/**
 * This is the confirmation dialog if to make sure that the user
 * wants to signOut.
 * @param onSignOutCancel what will happen if the user cancel the confirmation dialog, aka go back to were they were
 * @param onSignOutConfirm what will happen if the user sign out, aka remove all their information
 */
@Composable
private fun SignOutConfirmationDialog(
    onSignOutConfirm: () -> Unit, onSignOutCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("SignOut User") },
        text = { Text("Are you sure you want to SignOut?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onSignOutCancel) {
                Text("No")
            }
        },
        confirmButton = {
            TextButton(onClick = onSignOutConfirm) {
                Text("Yes")
            }
        })
}