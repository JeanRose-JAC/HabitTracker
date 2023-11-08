package com.example.habittrackerapp.signInSignUp


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.UserViewModel
import com.example.habittrackerapp.model.UserViewModelFactory
import com.example.habittrackerapp.ui.theme.HabitTrackerAppTheme


/**
 * all of the user's log in logic is here
 */

/**
 * the different type of Input the user as
 */

/**
 * this is where most of the information abt the sign Up page is
 * it is also where it checks if everything is being inputed and that it is valid
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun UserSignUp(modifier: Modifier = Modifier, authViewModel: AuthViewModel =
    viewModel(factory= AuthViewModelFactory()),
               myViewModel: UserViewModel =
                   viewModel(factory= UserViewModelFactory())) {

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var profilePicture by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }


    val navController = LocalNavController.current
    val userInput= data.current
    val showList=remember{ mutableStateOf(false)};

    Scaffold(
    ) { it->
        LazyColumn(contentPadding = it){
            item{
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ){

                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp),
                        painter= painterResource(R.drawable.logo),
                        contentDescription = null
                    )

                    Text(
                        text = "User Sign Up",
                        style = MaterialTheme.typography.displaySmall,
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
                Button(
                    onClick = {
                        showList.value=true
                    },
                    enabled = Validate(firstName,lastName,email,password)

                ){
                    Text("Submit ")
                    userInput.FirstName=firstName
                    userInput.ProfilePicture=profilePicture
                    userInput.Email=email
                    userInput.Gender=gender
                    userInput.LastName=lastName
                    userInput.Password=password
                    authViewModel.signUp(userInput.Email,userInput.FirstName,userInput.LastName,userInput.Gender,userInput.ProfilePicture,userInput.Password)


                }
            }
            item{
                if(showList.value){
                    Text(text = "Congrats you are logged in")
                    myViewModel.addUser(userInput)

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
 * and save it in the input list
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstName(firstName:String,onChange:(String)->Unit,modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = firstName,
            onValueChange = onChange,
            label={ Text("Please enter your first name") },
            isError=firstName.isEmpty()
        )
    }
}

/**
 * this gets the lastName and validates it, to make sure it's not empty
 * and save it in the input list
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LastName(lastName:String,onChange:(String)->Unit,modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        TextField(
            value = lastName,
            onValueChange = onChange,
            label={ Text("Please enter your last name") },
            isError=lastName.isEmpty()
        )
    }

}

/**
 * this gets the Email and validates it, to make sure that it follows the regex syntax
 * and save it in the input list
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email(email:String, onChange:(String)->Unit,modifier: Modifier = Modifier) {
    val regex="""^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,63})${'$'}""".toRegex()
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        TextField(
            value = email,
            onValueChange = onChange,
            label={ Text("Please enter your email") },
            isError=!email.matches(regex)

        )
    }

}

/**
 * this gets the Passsword and validates it, to make sure that the length
 * is bigger then 8 and that correspond to the password confimation section
 * and save it in the input list
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Password(password:String, onChange: (String) -> Unit, modifier: Modifier=Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        TextField(
            value = password,
            onValueChange = onChange,
            label={ Text("Please enter your Password") },
            isError = password.length<8
        )
    }
}




/**
 * this gets the gender of the user and remembers it
 * and save it in the input list
 */
@Composable
fun Gender(gender:String,onChange: (String) -> Unit) {

    val isSelectedItem: (String) -> Boolean = { gender == it }
    val onChangeState: (String) -> Unit = onChange

    val genders=listOf("no","female","male","non-binary")

    Column(Modifier.padding(8.dp)){
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
 * Validate all the different list and show error message at the bottom of the app
 * the current first name on the form, it makes sure it's not empty
 * the current last name on the form, it makes sure it's not empty
 * current email on the form, it makes sure that it has a form fo an email
 * current gender on the form, it makes sure that the user inputed a gender
 *  current password on the form, it makes sure that the password is at least 8 characters long
 *  current confirmation of password, makes sure that the password and the confirmation are the same.
 * @return {Boolean} true if it's all good and will enable the button else it will and return false
 */
@Composable
fun Validate(firstName: String,lastName: String,email: String,password: String):Boolean {

    val userInput= data.current

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
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePicture(profilePic:String, onChange: (String) -> Unit, modifier: Modifier=Modifier) {
    val userInput= data.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        Column {
            TextField(
                value = profilePic,
                onValueChange = onChange,
                label={ Text("Please input a profile pic ") }
            )

        }

    }
    AsyncImage(
        model = profilePic,
        contentDescription = "Translated description of what the image contains",
        error = painterResource( R.drawable.notgood),
        alignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    )

}
