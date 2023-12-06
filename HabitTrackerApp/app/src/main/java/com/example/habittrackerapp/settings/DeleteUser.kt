package com.example.habittrackerapp.settings

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.habittrackerapp.model.noteViewModel.NotesViewModel
import com.example.habittrackerapp.model.noteViewModel.NotesViewModelFactory
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModel
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModelSavedFactory
import com.example.habittrackerapp.model.userViewModel.UserViewModel
import com.example.habittrackerapp.model.userViewModel.UserViewModelFactory


/**
 * This function allows the user to delete their account, and we have a dialog to make sure that they
 * want to signOut.
 * @param authViewModel this is used to delete the user of the app and remove their information on firebase
 * @param savedUserViewModel this remove the user information and when they run the app again they wont be signIn or
 * be able to sign In
 * @param notesViewModel delete all the notes related to the user when the user is deleted
 * @param MyViewModel delete the user in firebase
 */
@Composable
fun DeleteUser(MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory()),
               authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory()),
               savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory()),
               notesViewModel: NotesViewModel = viewModel(factory= NotesViewModelFactory())
               ) {
    val userData = data.current
    var popupControl by rememberSaveable { mutableStateOf(false) }
    val navController = LocalNavController.current
    val allNotes by notesViewModel.allNotes.collectAsState()
    val notesList by notesViewModel.allUserNotes.collectAsState()



    TextButton(onClick = {popupControl=true}) {
        Text("Delete Account",color=MaterialTheme.colorScheme.error)

        if(allNotes.isNotEmpty()){
            notesViewModel.getNotes(userData.Email)
        }
    }

    if(popupControl){


        DeleteConfirmationDialog(
            onDeleteConfirm = {

                notesList.forEach(){
                    notesViewModel.deleteNote(it.id)
                }

                popupControl = false
                authViewModel.delete()
                MyViewModel.clearProfile(userData.Email)
                savedUserViewModel.saveEmailAndPassword("empty", "empty")
                userData.Email="";
                navController.navigate(Routes.SignUpSignIn.route)

            },
            onDeleteCancel = { popupControl = false },
        )
    }
}

/**
 * This function allows the user to delete their account, and we have a dialog to make sure that they
 * want to delete their account.
 * @param authViewModel this is used to delete the user of the app and remove their information
 * @param savedUserViewModel this remove the user information and when they run the app again they wont be signIn and
 * wont be able to signUp
 */
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
