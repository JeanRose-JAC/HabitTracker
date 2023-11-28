package com.example.habittrackerapp.signInSignUp

import Routes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.SavedUserViewModel
import com.example.habittrackerapp.model.SavedUserViewModelSavedFactory
import com.example.habittrackerapp.model.UserViewModel
import com.example.habittrackerapp.model.UserViewModelFactory

@Composable
fun SignSignUpScreen(savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory()),
                     MyViewModel: UserViewModel =viewModel(factory= UserViewModelFactory()),
                     authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())) {
    val myUiState by savedUserViewModel.uiState.collectAsState()
    val navController = LocalNavController.current
    val userInput= data.current
    val activeUser = MyViewModel.activeUser.collectAsState()
    var onceSigned by rememberSaveable { mutableStateOf(true) }


        if( !myUiState.email.isEmpty()) {
            if (myUiState.email != "empty") {
                if (onceSigned) {
                    authViewModel.signIn(myUiState.email, myUiState.password)
                    MyViewModel.getUser(myUiState.email)
                    onceSigned = false
                }

                if(activeUser.value.FirstName.isNotEmpty()){
                    println("in signIn-> ${activeUser.value.FirstName}")
                    userInput.FirstName=activeUser.value.FirstName
                    userInput.Email = activeUser.value.Email
                    userInput.Gender = activeUser.value.Gender
                    userInput.LastName = activeUser.value.LastName
                    userInput.ProfilePicture = activeUser.value.LastName
                    userInput.Password = activeUser.value.Password
                    navController.navigate(Routes.Setting.route)
                }
            } else {
                val scrollState = rememberScrollState()
                Image(
                    painter = painterResource(R.drawable.splashclock),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                )
                Column(
                    modifier = Modifier.verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Card(Modifier.padding(20.dp, 190.dp)) {

                    }
                    Text(
                        "Habit Minder",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Track Today. Transform Tomorrow.\nYour Habits. Your Success!",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp, 0.dp, 8.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = { navController.navigate(Routes.SignUp.route) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                    ) {
                        Text("GET STARTED")
                    }

                    Row(modifier = Modifier.align(CenterHorizontally)) {
                        Text(
                            "Already have an account? ",
                            modifier = Modifier.padding(14.dp, 14.dp, 0.dp, 14.dp)
                        )
                        TextButton(onClick = { navController.navigate(Routes.SignIn.route) },) {
                            Text("Login")


                        }
                    }
                }
            }


    }

}