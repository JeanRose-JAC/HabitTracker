package com.example.habittrackerapp.signInSignUp


import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.auth.ResultAuth
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModel
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModelSavedFactory
import com.example.habittrackerapp.model.userViewModel.UserViewModel
import com.example.habittrackerapp.model.userViewModel.UserViewModelFactory
import com.example.habittrackerapp.ui.theme.HabitTrackerAppTheme



/**
 * this is where most of the information abt the sign Up page is
 * it is also where it checks if everything is being inputed and that it is valid
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun UserSignUp(name: String? = null, modifier: Modifier = Modifier,
               myViewModel: UserViewModel =
                   viewModel(factory= UserViewModelFactory()),
               authViewModel: AuthViewModel= viewModel(factory = AuthViewModelFactory()),
               savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory())
) {
    val localContext = LocalContext.current
    val activity = localContext as ComponentActivity
    val signUpResult by authViewModel.signUpResult.collectAsState(ResultAuth.Inactive)
    val snackbarHostState = remember { SnackbarHostState() } // Material 3 approach

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var profilePicture by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var submitClicked by rememberSaveable { mutableStateOf(false) }
    var popup by rememberSaveable { mutableStateOf(false) }
    var createdOnce by rememberSaveable { mutableStateOf(true) }

    val users by myViewModel.allUsers.collectAsState()

    val navController = LocalNavController.current
    val userInput= data.current
    val showList=remember{ mutableStateOf(false)};

    var fromLauncher = false
    if(name!=null){
        firstName=name;
        fromLauncher = true
    }

    LaunchedEffect(signUpResult) {
        signUpResult?.let {
            if (it is ResultAuth.Inactive) {
                println("is inactive")
                return@LaunchedEffect
            }
            if (it is ResultAuth.InProgress) {
                println("is progress")
                snackbarHostState.showSnackbar("Sign-up In Progress")
                return@LaunchedEffect
            }
            if (it is ResultAuth.Success && it.data) {
                println("is signUp successful")
                snackbarHostState.showSnackbar("Sign-up Successful")
            } else if (it is ResultAuth.Failure || it is ResultAuth.Success) { // success(false) case
                println("is signUp unsuccessfull")
                snackbarHostState.showSnackbar("Sign-up Unsuccessful")
            }
        }
    }

    Scaffold(
    ) { it->
        LazyColumn(contentPadding = it){

            item{
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text = "Sign Up",
                        fontSize = 24.sp,
                        modifier = modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign =  TextAlign.Center
                    )

                }

                ProfilePicture(profilePicture,{profilePicture=it})
                FirstName(firstName,{firstName=it})
                LastName(lastName,{lastName=it})
                Gender(gender,{gender=it})
                Email(email,{email=it})
                Password(password,{password=it})
            }
            item{
                if(fromLauncher){
                    Button(onClick = {
                        val resultIntent = activity.intent
                        resultIntent.putExtra("resultData", "You are back at the launcher app.") // Set the value to return as a result
                        localContext.setResult(Activity.RESULT_OK, resultIntent)
                        localContext.finish() // Finish the activity
                    },
                        modifier=Modifier.fillMaxWidth()
                            .padding(60.dp, 8.dp)) {
                        Text("Send back a value to launching app")
                    }
                }

                Button(
                    onClick = { submitClicked = true },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(60.dp, 8.dp),
                    enabled = ValidateUser(firstName,lastName,email,password)

                ){
                    Text("Submit ")
                    userInput.FirstName=firstName
                    userInput.ProfilePicture=profilePicture
                    userInput.Email=email
                    userInput.Gender=gender
                    userInput.LastName=lastName
                    userInput.Password=password
                }
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item{

                if(submitClicked){
                    submitClicked = false
                    val profile = users.filter { it.Email == email }
                    if(profile.isEmpty()){
                        showList.value=true
                    }
                    else{
                        popup = true
                    }
                }

                if(showList.value && createdOnce){
                    createdOnce = false
                    savedUserViewModel.saveEmailAndPassword(userInput.Email, userInput.Password)
                    authViewModel.signUp(userInput.Email,userInput.Password)
                    myViewModel.addUser(userInput)
                    navController.navigate(Routes.Profile.route)
                }

                if(popup){
                    AlertDialog(onDismissRequest = { popup = false },
                        title = { Text("Email Already In Use") },
                        text = { Text("Please enter in a another email. The current email has already an account.") },
                        modifier = modifier,
                        confirmButton = {
                            TextButton(onClick = {popup = false}) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    }
}


/**
 * just to see how it is in the dark
 */
@Preview
@Composable
fun tryDark() {
    HabitTrackerAppTheme(darkTheme = true) {
        UserSignUp();
    }
}

/**
 * this creates the list that remembers the previous states of the elements
 * @param elements the current state of this variable
 */
@Composable
fun <T: Any> rememberMutableStateListOf(vararg elements: T): SnapshotStateList<T> {
    return rememberSaveable(
        saver = listSaver(
            save = { stateList ->
                if (stateList.isNotEmpty()) {
                    val first = stateList.first()
                    if (!canBeSaved(first)) {
                        throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                    }
                }
                stateList.toList()
            },
            restore = { it.toMutableStateList() }
        )
    ) {
        elements.toList().toMutableStateList()
    }
}


/**
 * this gets the FirstName and validates it, to make sure it's not empty
 * @param firstName it takes in the mutable firstName that is being change
 * @param onChange what will happen when the user edit the text field aka append the firstname
 *
 */
@Composable
fun FirstName(firstName:String,onChange:(String)->Unit,modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp, 8.dp)
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp),
            value = firstName,
            onValueChange = onChange,
            label={ Text("Please enter your first name") },
            isError=firstName.isEmpty()
        )
    }
}

/**
 * this gets the lastName and validates it, to make sure it's not empty
 * @param lastName it is the mutable for the lastname and store the user lastname when it is change
 * @param onChange what will happen when the user edit the text field aka append the lastname
 */
@Composable
fun LastName(lastName:String,onChange:(String)->Unit,modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp, 8.dp)
    ){
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp),
            value = lastName,
            onValueChange = onChange,
            label={ Text("Please enter your last name") },
            isError=lastName.isEmpty()
        )
    }

}

/**
 * this gets the Email and validates it, to make sure that it follows the regex syntax
 * @param email the email that the user want to be stored for their account
 * @param onChange the changed value of the email in the textfield which will append the email mutable
 */
@Composable
fun Email(email:String, onChange:(String)->Unit,modifier: Modifier = Modifier) {
    val regex="""^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,63})${'$'}""".toRegex()
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp, 8.dp)
    ){
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp),
            value = email,
            onValueChange = onChange,
            label={ Text("Please enter your email") },
            isError=!email.matches(regex)

        )
    }

}

/**
 * this gets the Passsword and validates it, to make sure that the length
 * is bigger then 8
 * @param password the mutable password that will be stored in the user class
 * @param onChange what happen when the user changed the texfield, append the password mutable
 */
@Composable
fun Password(password:String, onChange: (String) -> Unit, modifier: Modifier=Modifier) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp, 8.dp)
    ){
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp),
            value = password,
            onValueChange = onChange,
            label={ Text("Please enter your Password") },
            isError = password.length<8,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            }
        )
    }
}



/**
 * this gets the gender of the user and remembers it which are radioButtons
 * @param gender the mutable string that will get the return value of the selected gender
 * @param onChange what happen when the user change input, aka assign the gender to the selected gender
 */
@Composable
fun Gender(gender:String,onChange: (String) -> Unit) {

    val isSelectedItem: (String) -> Boolean = { gender == it }
    val onChangeState: (String) -> Unit = onChange

    val genders=listOf("Female","Male","Non-binary")

    Column(Modifier.padding(20.dp,8.dp)){
        Text(text = "Please choose a gender")

        genders.forEach { item->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = isSelectedItem(item),
                        onClick = { onChangeState(item) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedItem(item),
                    onClick = null
                )
                Text(
                    text = item,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


/**
 * Validate all mandatory element for the user,
 *  @param firstName, the current first name on the form, it makes sure it's not empty
 *  @param lastName, the current last name on the form, it makes sure it's not empty
 *  @param email, current email on the form, it makes sure that it has a form fo an email
 *  @param password, current password on the form, it makes sure that the password is at least 8 characters long
 * @return {Boolean} true if it's all good and will enable the button else it will and return false
 */
@Composable
fun ValidateUser(firstName: String,lastName: String,email: String,password: String):Boolean {
    val regex="""^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,63})${'$'}""".toRegex()
    return if(firstName.isEmpty() ){
        false
    }
    else if (lastName.isEmpty() ){
        false
    }
    else if( !email.matches(regex)){
        false
    }
    else if(password.length<8){
        false
    }
    else true

}

/**
 * This allows the user to input a image linq for their profile picture if the link doesn't work
 * it shows a not found image
 * @param profilePic the mutableState that store the link of the image that will be put later on in the user class.
 * @param onChange check for the input of an image and if the text change show the image.
 */
@Composable
fun ProfilePicture(profilePic:String, onChange: (String) -> Unit, modifier: Modifier=Modifier) {
    val userInput= data.current
    Column(horizontalAlignment = Alignment.CenterHorizontally ,modifier= Modifier.fillMaxWidth()) {
        Box(  modifier = Modifier.padding(20.dp).align(Alignment.CenterHorizontally)) {
            AsyncImage(
                model = profilePic,
                contentDescription = "Translated description of what the image contains",
                error = painterResource(R.drawable.noprofilepic),
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = CircleShape
                    ),
            )
        }
    }


    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp, 8.dp)
    ){
        Column {
            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp),
                value = profilePic,
                onValueChange = onChange,
                label = {Text("Please input a profile pic.")},
                placeholder ={ Text("URL") }
            )

        }

    }


}
