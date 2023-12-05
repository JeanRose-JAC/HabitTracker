package com.example.habittrackerapp.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.userViewModel.UserViewModel
import com.example.habittrackerapp.model.userViewModel.UserViewModelFactory

@Composable
fun EditProfile(MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())) {
    val navController = LocalNavController.current
    val userInput = data.current
    var profilePic by rememberSaveable { mutableStateOf(userInput.ProfilePicture) }
    var firstName by rememberSaveable { mutableStateOf(userInput.FirstName) }
    var lastName by rememberSaveable { mutableStateOf(userInput.LastName) }
    var saveChanges by rememberSaveable { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Text(
                text = "Edit Profile",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium,
                textAlign =  TextAlign.Center
            )
            ShowProfilePicture()
            if(userInput.Gender!="" || !userInput.Gender.isEmpty()){
                Text(text = userInput.Gender)
            }
            TextCard("Firstname",firstName, focusManager) { firstName = it }
            TextCard("LastName",lastName, focusManager) { lastName = it }
            //TextCard("Profile Pic",profilePic, focusManager) {  profilePic= it }

            if(ValidUserProfileEditChanges(firstName,lastName,profilePic)){
                Button(onClick = {saveChanges=true},modifier= Modifier.fillMaxWidth().padding(start=20.dp, top = 20.dp,end=20.dp) ) {
                    Text("Save Changes")
                }
                if(saveChanges){
                    SaveUserProfileChange(firstName,lastName,userInput.Email,userInput.Password,userInput.ProfilePicture)
                    focusManager.clearFocus()
                    saveChanges = false
                }
            }
            Button(onClick = {navController.navigate(Routes.Profile.route)},modifier = Modifier.padding(20.dp)){
                Text(text = "Cancel")
            }

        }

    }
}