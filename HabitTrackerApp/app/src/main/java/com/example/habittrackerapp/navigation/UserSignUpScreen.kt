package com.example.habittrackerapp.navigation


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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.data
import com.example.habittrackerapp.ui.theme.HabitTrackerAppTheme


/**
 * all of the user's log in logic is here
 */

/**
 * the different type of Input the user as
 */
enum class Input(val index:Int){
    FIRSTNAME(0),
    LASTNAME(1),
    GENDER(2),
    EMAIL(3),
    PASSWORD(4),
    PASSWORDCONFIRMATION(5),
    PROFILEPIC(6)
}

/**
 * this is where most of the information abt the sign Up page is
 * it is also where it checks if everything is being inputed and that it is valid
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun UserSignUp(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val userInput= data.current
    // all of the variable needed
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

                ProfilePicture()
                FirstName()
                LastName()
                Gender()
                Email()
                Password()
                PasswordConfimation()
            }
            item{
                Button(
                    onClick = {
                        showList.value=true
                    },
                    enabled = Validate(userInput)

                ){
                    Text("Submit ")
                }
            }
            item{
                if(showList.value){
                    navController.navigate("UserWelcomeScreenRoute/${userInput.get(Input.FIRSTNAME.index).toString()}")

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
fun FirstName(modifier: Modifier = Modifier) {
    //the different input
    val userInput= data.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = userInput.get(Input.FIRSTNAME.index),
            onValueChange = {userInput.set(Input.FIRSTNAME.index,it)},
            label={ Text("Please enter your first name") },
            isError=userInput.get(Input.FIRSTNAME.index).toString().isEmpty()
        )
    }
}

/**
 * this gets the lastName and validates it, to make sure it's not empty
 * and save it in the input list
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LastName(modifier: Modifier = Modifier) {
    val userInput= data.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        TextField(
            value = userInput.get(Input.LASTNAME.index),
            onValueChange = {userInput.set(Input.LASTNAME.index,it)},
            label={ Text("Please enter your last name") },
            isError=userInput.get(Input.LASTNAME.index).toString().isEmpty()
        )
    }

}

/**
 * this gets the Email and validates it, to make sure that it follows the regex syntax
 * and save it in the input list
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email(modifier: Modifier = Modifier) {
    val regex="""^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,63})${'$'}""".toRegex()
    val userInput= data.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        TextField(
            value = userInput.get(Input.EMAIL.index),
            onValueChange = {userInput.set(Input.EMAIL.index,it)},
            label={ Text("Please enter your email") },
            isError=userInput.get(Input.EMAIL.index).toString().matches(regex)

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
fun Password(modifier: Modifier=Modifier) {
    val userInput= data.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        TextField(
            value = userInput.get(Input.PASSWORD.index),
            onValueChange = {userInput.set(Input.PASSWORD.index,it)},
            label={ Text("Please enter your Password") },
            isError=userInput.get(Input.PASSWORD.index).toString()!=userInput.get(Input.PASSWORDCONFIRMATION.index).toString() && userInput.get(Input.PASSWORD.index).toString().length<8
        )
    }
}

/**
 * this gets the Passsword Confirmation and validates it, to make sure
 * that the password correspond to the password confirmation section
 * and save it in the input list
 */
@Composable
fun PasswordConfimation(modifier:Modifier=Modifier) {
    val userInput= data.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        TextField(
            value = userInput.get(Input.PASSWORD.index),
            onValueChange = {userInput.set(Input.PASSWORD.index,it)},
            label={ Text("Please confirm your password ") },
            isError=userInput.get(Input.PASSWORD.index).toString()!=userInput.get(Input.PASSWORDCONFIRMATION.index).toString()
        )
    }
}


/**
 * this gets the gender of the user and remembers it
 * and save it in the input list
 */
@Composable
fun Gender() {
    val userInput= data.current

    val isSelectedItem: (String) -> Boolean = { userInput.get(Input.GENDER.index) == it }
    val onChangeState: (String) -> Unit = { userInput.set(Input.GENDER.index,it) }

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
fun Validate(list: SnapshotStateList<String>):Boolean {

    val userInput= data.current

    val regex="""^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,63})${'$'}""".toRegex()
    return if(userInput.get(Input.FIRSTNAME.index).toString().isEmpty() ){
        false
    }
    else if (userInput.get(Input.LASTNAME.index).toString().isEmpty() ){
        false
    }else if( userInput.get(Input.EMAIL.index).toString().matches(regex)){
        false
    }    else if(userInput.get(Input.PASSWORD.index).toString()!=userInput.get(Input.PASSWORDCONFIRMATION.index).toString()){
        false
    }
    else !(userInput.get(Input.PASSWORD.index).toString().length<8 || userInput.get(Input.GENDER.index).toString().isEmpty())
}

/**
 * This allows the user to input a image linq for their profile picture if the link doesn't work
 * it shows a not found image
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePicture(modifier: Modifier=Modifier) {
    val userInput= data.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        Column {
            TextField(
                value = userInput.get(Input.PROFILEPIC.index),
                onValueChange = {userInput.set(Input.PROFILEPIC.index,it)},
                label={ Text("Please input a profile pic ") }
            )

        }

    }
    AsyncImage(
        model = Input.PROFILEPIC.index,
        contentDescription = "Translated description of what the image contains",
        error = painterResource( R.drawable.notgood),
        alignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    )

}
