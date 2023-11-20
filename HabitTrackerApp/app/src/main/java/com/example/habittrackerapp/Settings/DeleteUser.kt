package com.example.habittrackerapp.Settings

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
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.UserViewModel
import com.example.habittrackerapp.model.UserViewModelFactory

@Composable
fun DeleteUser(MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())) {
    val userData = data.current
    var popupControl by rememberSaveable { mutableStateOf(false) }
    val navController = LocalNavController.current




    Button(onClick = {popupControl=true}) {
        Text("Delete Account")
    }

    if(popupControl){


        DeleteConfirmationDialog(
            onDeleteConfirm = {

                popupControl = false
                MyViewModel.clearProfile(userData.Email)
                userData.Email="";
                navController.navigate(Routes.SignUpSignIn.route)

            },
            onDeleteCancel = { popupControl = false },
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("Delete User") },
        text = { Text("Are you sure you want to delete this user?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("No")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes")
            }
        })
}
