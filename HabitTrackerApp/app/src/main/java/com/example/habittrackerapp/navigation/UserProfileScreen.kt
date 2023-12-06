package com.example.habittrackerapp.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.LocalNavController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModel
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModelSavedFactory
import com.example.habittrackerapp.model.userViewModel.UserViewModel
import com.example.habittrackerapp.model.userViewModel.UserViewModelFactory
import com.example.habittrackerapp.signInSignUp.ValidateUser


/**
 * The UserProfileScreen checks if the user is or isn't signIn.
 * if they arent they will have the choise to signIn or signUp else it
 * will display the user informations
 */
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
        DisplayUserInformation()
    }

}

/**
 * The DisplayUserInformation gets the user information
 * and displays them and allow the user to edit their different informations
 */
@Composable
fun DisplayUserInformation(MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())) {
    val userInput = data.current
    var firstName by rememberSaveable { mutableStateOf(userInput.FirstName) }
    var lastName by rememberSaveable { mutableStateOf(userInput.LastName) }
    var email by rememberSaveable { mutableStateOf(userInput.Email) }
    var password by rememberSaveable { mutableStateOf(userInput.Password) }
    var saveChanges by rememberSaveable {mutableStateOf(false)}

    val focusManager = LocalFocusManager.current

    LazyColumn {
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

            //DeleteUser()
        }

    }
}

/**
 * allows the user to see their profilePicture
 */
@Composable
fun ShowProfilePicture() {
    val userInput = data.current
    AsyncImage(
        model = userInput.ProfilePicture,
        contentDescription = "Translated description of what the image contains",
        error = painterResource( R.drawable.notgood),
        alignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

/**
 * the different card to see the user informations
 * @param name the name of the information being display
 * @param value what is the current value of the information
 * @param onChange what happens if the value changes in the card
 */
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
//                Icon(painter = Icons.Filled.Create, contentDescription = "Edit")
            }
        }
    }

}

/**
 * check if any changes were make in the user information
 *@param firstName check if the firstname param is different then the current data
 *@param lastName check if the lastname param is different then the current data
 *@param email check if the email param is different then the current data
 *@param password check if the password param is different then the current data
 * @return boolean if their were any changed in the user information
 */
@Composable
fun ValidUserProfileChanges(firstName: String,lastName: String,email: String,password: String,MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())):Boolean {
    val userInput = data.current

    return (firstName!=userInput.FirstName) || (lastName !=userInput.LastName) || (email!=userInput.Email) || (password!=userInput.Password)
}


/**
 * This updates the user informations if anything was edited
 * @param firstName the new edited firstname
 * @param lastName the edited lastname
 * @param email the edited email
 * @param password the edited password
 * @param MyViewModel the viewModel to update the user informations
 *
 */
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




