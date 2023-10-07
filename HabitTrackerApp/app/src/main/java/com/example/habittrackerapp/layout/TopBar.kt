package com.example.habittrackerapp.layout


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.R
/**
 * The Top bar of the different screens show in the application, it shows a way to navigate to
 * the Profile page
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarScreen(title:String){
    val navController = LocalNavController.current
    CenterAlignedTopAppBar(
        title={ Row(modifier= Modifier
            .padding(20.dp)
            .fillMaxWidth(),

            ){
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp),
                painter= painterResource(R.drawable.logo),
                contentDescription = null
            )
            Text(
                text=title,
                modifier = Modifier
                    .padding(0.dp,30.dp,0.dp,0.dp)

            )
            Spacer(modifier = Modifier
                .height(100.dp)
                .width(100.dp))


        }},
        navigationIcon={
            Row {
                IconButton(onClick = {navController.popBackStack()}) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                }

                IconButton(onClick = { navController.navigate(Routes.SignUp.route)}) {
                    Icon(Icons.Filled.AccountBox, contentDescription = "Profile")
                }

            }

        }


    )

}