import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.habit.screens.HabitEditScreen
import com.example.habittrackerapp.habit.screens.HabitItemScreen
import com.example.habittrackerapp.habit.screens.HabitListScreen
import com.example.habittrackerapp.habit.screens.HabitQuestionnaireScreen
import com.example.habittrackerapp.navigation.AboutScreen
import com.example.habittrackerapp.navigation.NoteScreen
import com.example.habittrackerapp.navigation.UserSignUp


/**
 * Class that specify the routes of different screens
 */

sealed class Routes(val route:String)  {
    object SignUp : Routes("SignUpScreenRoute")
    object About : Routes("AboutScreenRoute")
    object Note: Routes("NoteScreenRoute")
    object HabitQuestionnaire: Routes("HabitQuestionnaireRoute")
    object HabitItem: Routes("HabitItemRoute/{id}"){
        fun go(id : String) = "HabitItemRoute/$id"
    }
    object HabitList: Routes("HabitListRoute")
    object EditHabit: Routes("HabitEditRoute/{id}"){
        fun go(id : String) = "HabitEditRoute/$id"
    }
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
        composable(Routes.HabitQuestionnaire.route){ HabitQuestionnaireScreen() }
        composable(Routes.HabitItem.route){ HabitItemScreen(it.arguments?.getString("id") ?: "") }
        composable(Routes.HabitList.route){ HabitListScreen() }
        composable(Routes.EditHabit.route){ HabitEditScreen(it.arguments?.getString("id") ?: "") }

    }

}