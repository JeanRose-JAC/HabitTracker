package com.example.habittrackerapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.model.SavedUser
import com.example.habittrackerapp.model.SavedUserViewModel
import com.example.habittrackerapp.model.SavedUserViewModelSavedFactory
import com.example.habittrackerapp.noteInput.ViewNote


@Composable
fun NoteScreen(savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory())
) {
    val myUiState by savedUserViewModel.uiState.collectAsState()
    val navController = LocalNavController.current
    val savedEmail by rememberSaveable { mutableStateOf(myUiState.email)}
    val savedPW by rememberSaveable { mutableStateOf(myUiState.password)}

    Column {
        if( !savedEmail.isEmpty() && !savedPW.isEmpty()){
            Text(text = "Notes",
                modifier = Modifier
                    .padding(20.dp, 10.dp)
                    .align(Alignment.CenterHorizontally),
                style= MaterialTheme.typography.displaySmall
            )
            ViewNote()
        }else{
            navController.navigate(Routes.SignUpSignIn.route)
        }
    }
}
