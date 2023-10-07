package com.example.habittrackerapp.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.habittrackerapp.LocalNavController

/**
 * The MainLayout, using the helps us seperate the code into a tapbar, a content and a bottom bar
 * @param screenName it's basically the name of the app
 * @param content it's the content of the page, like the different composable
 */

@Composable
fun MainLayout(screenName:String,content:@Composable ()->Unit){


    val navController = LocalNavController.current

    Scaffold(
        modifier = Modifier,
        topBar = { TopBarScreen(title = screenName)},
        bottomBar = {BottomBarScreen()},

        ) {
        Column(modifier = Modifier.padding(it)) {
            content()
        }
    }


}