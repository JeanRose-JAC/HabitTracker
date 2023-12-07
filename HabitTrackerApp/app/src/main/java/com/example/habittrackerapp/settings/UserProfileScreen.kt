package com.example.habittrackerapp.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
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
import com.example.habittrackerapp.note.CreateNote
import com.example.habittrackerapp.signInSignUp.ValidateUser

/**
 * The UserProfileScreen checks if the user is or isn't signIn.
 * if they arent they will have the choise to signIn or signUp else it
 * will display the user information
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UserProfileScreen(
    savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory()),
    MyViewModel: UserViewModel =viewModel(factory= UserViewModelFactory()),
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())) {

    val myUiState by savedUserViewModel.uiState.collectAsState()
    val activeUser = MyViewModel.activeUser.collectAsState()
    var onceSigned by rememberSaveable {mutableStateOf(true) }
    val scrollState = rememberScrollState()
    val userInput = data.current
    val navController = LocalNavController.current

    Column {
        if( !myUiState.email.isEmpty()){
            if(myUiState.email != "empty"){
                if(onceSigned){
                    authViewModel.signIn(myUiState.email,myUiState.password)
                    MyViewModel.getUser(myUiState.email, myUiState.password)
                    onceSigned = false
                }

                if(activeUser.value.FirstName.isNotEmpty()){
                    println("in signIn-> ${activeUser.value.FirstName}")
                    userInput.FirstName=activeUser.value.FirstName
                    userInput.Email = activeUser.value.Email
                    userInput.Gender = activeUser.value.Gender
                    userInput.LastName = activeUser.value.LastName
                    userInput.ProfilePicture = activeUser.value.ProfilePicture
                    userInput.Password = activeUser.value.Password

                }

                if(userInput.Email.isNotEmpty()) {
                    DisplayUserInformation()
                }
            }
        }
    }
}

/**
 * The DisplayUserInformation gets the user information
 * and displays them and allow the user to edit their different informations
 */
@Composable
fun DisplayUserInformation(authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory()),
                            savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory())) {
    val navController = LocalNavController.current
    val userInput = data.current
    val userData = data.current
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
            Text(userInput.Email, modifier = Modifier.padding(16.dp))

            Spacer(modifier = Modifier.padding(20.dp))
            Divider()
            TextButton(onClick = {navController.navigate(Routes.EditProfile.route)},modifier = Modifier.fillMaxWidth()){
                Text(text = "Edit Profile")
            }

            Divider()
            TextButton(onClick = {navController.navigate(Routes.AccountSetting.route)},modifier = Modifier.fillMaxWidth()){
                Text(text = "Account Settings")
            }

            Divider()
            TextButton(onClick = {navController.navigate(Routes.Appearance.route)}, modifier = Modifier.fillMaxWidth()){
                Text(text = "Appearance")
            }
            Divider()
            TextButton(onClick = {navController.navigate(Routes.AboutUs.route)}, modifier = Modifier.fillMaxWidth()){
                Text(text = "About Us")
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
                userData.Email=""
                savedUserViewModel.saveEmailAndPassword("empty", "empty")
                authViewModel.signOut()
                navController.navigate(Routes.SignUpSignIn.route)

            },
            onSignOutCancel = { popupControl = false },
        )
    }
}


/**
 * Allows the user to see their profilePicture.
 */
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
/**
 * The different card to see the user information
 * @param name the name of the information being display
 * @param value what is the current value of the information
 * @param onChange what happens if the value changes in the card
 */
@Composable
fun TextCard(name:String,value:String, focusManager: FocusManager, onChange:(String)->Unit) {
    val focusRequester = remember { FocusRequester() }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceTint,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start=20.dp,end=20.dp,top=20.dp)
            .size(width = 240.dp, height = 100.dp)
    ) {
        Column() {
            Text(
                text = name, fontSize = 20.sp,fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp)
            )

            Row {
                TextField(value = value,
                    onValueChange = onChange,
                    colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.surfaceTint),
                    modifier = Modifier.focusRequester(focusRequester).fillMaxWidth()
                )

            }
        }
    }

}

/**
 * Validates if the user has modified their profile email or password
 * */
@Composable
fun ValidUserProfileSettingChanges(email: String,password: String,MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())):Boolean {
    val userInput = data.current

    return  (email!=userInput.Email) || (password!=userInput.Password)
}

/**
 * Validates if the user has modified their profile first name or last name
 * */
@Composable
fun ValidUserProfileEditChanges(firstName: String,lastName: String,profilePic:String,MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())):Boolean {
    val userInput = data.current

    return (firstName!=userInput.FirstName) || (lastName !=userInput.LastName)|| (profilePic!=userInput.ProfilePicture)
}
/**
 * Validates if the user has modified their profile first name or last name
 * */
@Composable
fun SaveUserProfileChange(firstName: String, lastName: String, email: String, password: String,profilePic:String, MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory()), savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory())
) {
    val userInput = data.current
    userInput.FirstName=firstName
    userInput.LastName=lastName
    userInput.Email=email
    userInput.Password=password
    userInput.ProfilePicture=profilePic
    MyViewModel.addUser(userInput)
    savedUserViewModel.saveEmailAndPassword(userInput.Email, userInput.Password)
}




