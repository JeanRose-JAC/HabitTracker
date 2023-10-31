import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.navigation.AboutScreen
import com.example.habittrackerapp.navigation.NoteScreen
import com.example.habittrackerapp.navigation.UserSignUp
import com.example.habittrackerapp.noteInput.EditNote
import com.example.habittrackerapp.noteInput.screens.NoteList
import com.example.habittrackerapp.noteInput.screens.SingleNote
import com.example.habittrackerapp.noteInput.screens.SingleNoteEdit


/**
 * Class that specify the routes of different screens
 */

sealed class Routes(val route:String)  {
    object SignUp : Routes("SignUpScreenRoute")
    object About : Routes("AboutScreenRoute")
    object Note: Routes("NoteScreenRoute")
    object ViewSingle: Routes("SingleNoteScreenRoute/{id}"){
        fun go(id: String) = "ContactScreenRoute/$id"
    }
    object EditNote: Routes("EditNoteScreenRoute/{id}")
    object ViewList: Routes("NoteListScreenRoute")
}

/**
 * Composable that allows for navigation within the app
 */
@SuppressLint("SuspiciousIndentation")
@Composable
fun Router() {
    val navController = LocalNavController.current
    NavHost(navController = navController as NavHostController,
        startDestination = Routes.About.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { fadeOut() }) {

        composable(Routes.SignUp.route) { UserSignUp() }
        composable(Routes.About.route) { AboutScreen()}
        composable(Routes.Note.route){ NoteScreen() }
        composable(Routes.ViewList.route){ NoteList()}
        //how to extract the elements from the text fields.....
        composable(Routes.ViewSingle.route){
            SingleNote(it.arguments?.getString("id")?:"")
        }
        composable(Routes.EditNote.route){ SingleNoteEdit(it.arguments?.getString("id")?:"" ) }
    }

}