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
import com.example.habittrackerapp.data
import com.example.habittrackerapp.LocalNavController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.habittrackerapp.R
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory
import com.example.habittrackerapp.model.UserViewModel
import com.example.habittrackerapp.model.UserViewModelFactory
import com.example.habittrackerapp.signInSignUp.ValidateUser
import kotlinx.coroutines.launch

@Composable
fun UserProfileScreen(
    MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())){

    val userData = MyViewModel.uiState.collectAsState()
    val navController = LocalNavController.current

    if(!ValidateUser(firstName = userData.value.FirstName, lastName = userData.value.LastName, email = userData.value.Email, password = userData.value.Password)){
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

@Composable
fun DisplayUserInformation(MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())){
    val userData = MyViewModel.uiState.collectAsState()
    var firstName by rememberSaveable { mutableStateOf(userData.value.FirstName) }
    var lastName by rememberSaveable { mutableStateOf(userData.value.LastName) }
    var email by rememberSaveable { mutableStateOf(userData.value.Email) }
    var password by rememberSaveable { mutableStateOf(userData.value.Password) }
    var saveChanges by rememberSaveable {mutableStateOf(false)}

    LazyColumn {
        item {
            ShowProfilePicture()
            if(userData.value.Gender!="" || !userData.value.Gender.isEmpty()){
                Text(text = userData.value.Gender)
            }
            TextCard("Firstname",firstName) { firstName = it }
            TextCard("LastName",lastName) { lastName = it }
            TextCard("Email",email) { email = it }
            TextCard("Password",password) { password = it }

            if(ValidUserProfileChanges(firstName,lastName,email,password)){
                Button(onClick = {saveChanges=true}) {
                    Text("Save Changes")
                }
                if(saveChanges){
                    SaveUserProfileChange(firstName,lastName,email,password)
                }
            }

            DeleteUser()
        }

    }
}
@Composable
fun ShowProfilePicture(MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())){
    val userData = MyViewModel.uiState.collectAsState()
    AsyncImage(
        model = userData.value.ProfilePicture,
        contentDescription = "Translated description of what the image contains",
        error = painterResource( R.drawable.notgood),
        alignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextCard(name:String,value:String,onChange:(String)->Unit){

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
                    colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.surfaceTint)
                )
//                Icon(painter = Icons.Filled.Create, contentDescription = "Edit")
            }
        }
    }

}

@Composable
fun ValidUserProfileChanges(firstName: String,lastName: String,email: String,password: String,MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())):Boolean{
    val userData = MyViewModel.uiState.collectAsState()

    return (firstName!=userData.value.FirstName) || (lastName !=userData.value.LastName) || (email!=userData.value.Email) || (password!=userData.value.Password)
}

@Composable
fun SaveUserProfileChange(firstName: String,lastName: String,email: String,password: String,MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())){
    val userData = MyViewModel.uiState.collectAsState()
    userData.value.FirstName=firstName
    userData.value.LastName=lastName
    userData.value.Email=email
    userData.value.Password=password

}

@Composable
fun DeleteUser(MyViewModel: UserViewModel = viewModel(factory= UserViewModelFactory())){
    val userData = MyViewModel.uiState.collectAsState()
    var popupControl by rememberSaveable { mutableStateOf(false) }



    Button(onClick = {popupControl=true}) {
        Text("Delete Account")
    }
    
    if(popupControl){
        Popup(onDismissRequest = { popupControl = false }) {
            Text(text = "Are you sure you want to delete you account?")
            
            Row {
                Button(onClick = {
                    MyViewModel.clearProfile(userData.value.Email);
                })
                {
                    Text(text = "yes")
                }
                Button(onClick = { popupControl = false}) {
                    Text(text = "No")
                }
            }
        }
    }
}


