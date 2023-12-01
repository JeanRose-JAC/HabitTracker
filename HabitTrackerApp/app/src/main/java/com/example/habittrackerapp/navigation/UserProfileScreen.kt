package com.example.habittrackerapp.navigation

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.LocalNavController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.habittrackerapp.R
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModel
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModelSavedFactory
import com.example.habittrackerapp.model.userViewModel.UserViewModel
import com.example.habittrackerapp.model.userViewModel.UserViewModelFactory
import com.example.habittrackerapp.settings.SignOutConfirmationDialog
import com.example.habittrackerapp.signInSignUp.ValidateUser

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UserProfileScreen(
    MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())) {

    val activeUser = MyViewModel.activeUser.collectAsState()
    val userInput = data.current
    val navController = LocalNavController.current

    if(!ValidateUser(firstName = userInput.FirstName, lastName = userInput.LastName, email = userInput.Email, password = userInput.Password)){
        Column {
            Text(text = "Please sign Up")
            Button(onClick = {navController.navigate(Routes.SignUp.route)}) {
                Text(text = "Go to Sign Up")
            }
        }
    }
    else{
        DisplayUserInformation2()
    }

}

@Composable
fun EditProfile(MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())) {
    val navController = LocalNavController.current
    val userInput = data.current
    var firstName by rememberSaveable { mutableStateOf(userInput.FirstName) }
    var lastName by rememberSaveable { mutableStateOf(userInput.LastName) }
    var email by rememberSaveable { mutableStateOf(userInput.Email) }
    var password by rememberSaveable { mutableStateOf(userInput.Password) }
    var saveChanges by rememberSaveable {mutableStateOf(false)}

    val focusManager = LocalFocusManager.current

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            ShowProfilePicture()
            if(userInput.Gender!="" || !userInput.Gender.isEmpty()){
                Text(text = userInput.Gender)
            }
            TextCard("Firstname",firstName, focusManager) { firstName = it }
            TextCard("LastName",lastName, focusManager) { lastName = it }
            TextCard("Email",email, focusManager) { email = it }
            TextCard("Password",password, focusManager) { password = it }

            if(ValidUserProfileChanges(firstName,lastName,email,password)){
                Button(onClick = {saveChanges=true}) {
                    Text("Save Changes")
                }
                if(saveChanges){
                    SaveUserProfileChange(firstName,lastName,email,password)
                    focusManager.clearFocus()
                    saveChanges = false
                }
            }
            Button(onClick = {navController.navigate(Routes.Profile.route)}){
                Text(text = "Cancel")
            }


            //DeleteUser()
        }

    }
}
@Composable
fun DisplayUserInformation2(authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory()),
                            savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory())) {
    val navController = LocalNavController.current
    val userInput = data.current
    val userData = data.current;
    var popupControl by rememberSaveable { mutableStateOf(false) }

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        item {

            ShowProfilePicture()
            if(userInput.Gender!="" || !userInput.Gender.isEmpty()){
                Text(text = userInput.Gender)
            }
            Row(){
                Text(userInput.FirstName, fontSize = 30.sp)
                Spacer(modifier = Modifier.padding(2.dp))
                Text(userInput.LastName,fontSize = 30.sp)
            }
            Text(userInput.Email, modifier = Modifier.padding(20.dp))

            Spacer(modifier = Modifier.padding(40.dp))
            Divider()
            TextButton(onClick = {navController.navigate(Routes.EditProfile.route)},modifier = Modifier.fillMaxWidth()){
                Text(text = "Edit Profile")
            }

            Divider()
            TextButton(onClick = {navController.navigate(Routes.EditProfile.route)},modifier = Modifier.fillMaxWidth()){
                Text(text = "Account Settings")
            }

            Divider()
            TextButton(onClick = {navController.navigate(Routes.EditProfile.route)}, modifier = Modifier.fillMaxWidth()){
                Text(text = "Appearance")
            }

            Divider()
            TextButton(onClick = {popupControl=true},modifier = Modifier.fillMaxWidth()){
                Text(text = "Sign out")
            }
            Divider()
        }

    }
    if(popupControl){

        SignOutConfirmationDialog(
            onSignOutConfirm = {

                popupControl = false
                userData.Email="";
                savedUserViewModel.saveEmailAndPassword("empty", "empty")
                authViewModel.signOut();
                navController.navigate(Routes.SignUpSignIn.route)

            },
            onSignOutCancel = { popupControl = false },
        )
    }
}



@Composable
fun ShowProfilePicture() {
    val userInput = data.current

    Box(  modifier = Modifier.padding(20.dp)) {
        AsyncImage(
            model = userInput.ProfilePicture,
            contentDescription = "Translated description of what the image contains",
            error = painterResource(R.drawable.noprofilepic),
            alignment = Alignment.Center,
            modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
                .padding(0.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                ),
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextCard(name:String,value:String, focusManager: FocusManager, onChange:(String)->Unit) {
    val focusRequester = remember { FocusRequester() }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceTint,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .size(width = 240.dp, height = 100.dp)
    ) {
        Column {
            Text(
                text = name, fontSize = 20.sp,fontWeight = FontWeight.Bold
            )

            Row {
                TextField(value = value,
                    onValueChange = onChange,
                    colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.surfaceTint),
                    modifier = Modifier.focusRequester(focusRequester)
                )

            }
        }
    }

}

@Composable
fun ValidUserProfileChanges(firstName: String,lastName: String,email: String,password: String,MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())):Boolean {
    val userInput = data.current

    return (firstName!=userInput.FirstName) || (lastName !=userInput.LastName) || (email!=userInput.Email) || (password!=userInput.Password)
}

@Composable
fun SaveUserProfileChange(firstName: String, lastName: String, email: String, password: String, MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory()), savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory())
) {
    val userInput = data.current
    userInput.FirstName=firstName
    userInput.LastName=lastName
    userInput.Email=email
    userInput.Password=password
    MyViewModel.addUser(userInput);
    savedUserViewModel.saveEmailAndPassword(userInput.Email, userInput.Password)
}




