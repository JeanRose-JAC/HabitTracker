package com.example.habittrackerapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.data
import com.example.habittrackerapp.LocalNavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create

@Composable
fun UserProfileScreen(){
    val userInput= data.current
    val navController = LocalNavController.current
    val predicate: (String) -> Boolean = {it ==""}

    if(userInput.toList().any(predicate)){
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
fun DisplayUserInformation(){
    val userInput= data.current
    var name=""
    LazyColumn {
        itemsIndexed(userInput.toList()){index, item ->
            if(index==Input.EMAIL.index){
                name="Email"
            }
            else if(index==Input.FIRSTNAME.index){
                name="Firstname"
            }
            else if(index==Input.LASTNAME.index){
                name="LastName"
            }
            else if(index==Input.PASSWORD.index){
                name="Password"
            }
            else if(index==Input.GENDER.index){
                name="Gender"
            }

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
                        text = name,
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )

                    Row {
                        Text(text = item,
                            modifier = Modifier
                                .padding(8.dp))

                        IconButton(onClick = { userInput.remove(item) }) {
                            Icon(Icons.Filled.Clear, contentDescription = "remove")
                        }
                    }
                }
            }
        }
    }


}