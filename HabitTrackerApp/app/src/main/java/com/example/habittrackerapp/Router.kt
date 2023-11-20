import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.Settings.PolicyScreen
import com.example.habittrackerapp.Settings.SettingScreen
import com.example.habittrackerapp.habit.screens.HabitEditScreen
import com.example.habittrackerapp.habit.screens.HabitItemScreen
import com.example.habittrackerapp.habit.screens.HabitListScreen
import com.example.habittrackerapp.habit.screens.HabitQuestionnaireScreen
import com.example.habittrackerapp.habit.screens.HabitsForTodayListScreen
import com.example.habittrackerapp.navigation.AboutScreen
import com.example.habittrackerapp.navigation.NoteScreen
import com.example.habittrackerapp.navigation.UserProfileScreen
import com.example.habittrackerapp.signInSignUp.SignSignUpScreen
import com.example.habittrackerapp.signInSignUp.UserSignInScreen
import com.example.habittrackerapp.signInSignUp.UserSignUp
import com.example.habittrackerapp.noteInput.screens.NoteList
import com.example.habittrackerapp.noteInput.screens.SingleNote
import com.example.habittrackerapp.noteInput.screens.SingleNoteEdit


/**
 * Class that specify the routes of different screens
 */

sealed class Routes(val route:String)  {
    object SignUp : Routes("SignUpScreenRoute")
    object SignUpSignIn : Routes("SignUpSignUpScreenRoute")
    object SignIn : Routes("SignInScreenRoute")
    object About : Routes("AboutScreenRoute")
    object Note: Routes("NoteScreenRoute")
    object Profile:Routes("ProfileScreenRoute")
    object Setting:Routes("SettingScreenRoute")
    object  Policy: Routes("PolicyScreenRoute")


    object ViewSingle: Routes("SingleNoteScreenRoute/{id}"){
        fun go(id: String) = "ContactScreenRoute/$id"
    }
    object EditNote: Routes("EditNoteScreenRoute/{id}")
    object ViewList: Routes("NoteListScreenRoute")

    object HabitQuestionnaire: Routes("HabitQuestionnaireRoute")
    object HabitItem: Routes("HabitItemRoute"){
        const val habitIdArg = "habitId"
        val routeWithArgs = "${route}/{$habitIdArg}"
    }
    object HabitList: Routes("HabitListRoute")
    object HabitForTodayList: Routes("HabitForTodayListRoute")

    object EditHabit: Routes("HabitEditRoute"){
        const val habitIdArg = "habitId"
        val routeWithArgs = "${route}/{$habitIdArg}"
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
        composable(Routes.Profile.route){ UserProfileScreen()}
        composable(Routes.Setting.route){ SettingScreen()}
        composable(Routes.Policy.route){ PolicyScreen()}

        composable(Routes.ViewList.route){ NoteList()}
        //how to extract the elements from the text fields.....
        composable(Routes.ViewSingle.route){
            SingleNote(it.arguments?.getString("id")?:"")
        }
        composable(Routes.EditNote.route){ SingleNoteEdit(it.arguments?.getString("id")?:"" ) }

        composable(Routes.SignIn.route){ UserSignInScreen() }
        composable(Routes.SignUpSignIn.route){ SignSignUpScreen() }
        composable(Routes.HabitQuestionnaire.route){ HabitQuestionnaireScreen() }
        composable(
            route = Routes.HabitItem.routeWithArgs,
            arguments = listOf(navArgument(Routes.HabitItem.habitIdArg){
                type = NavType.IntType
            })){
            HabitItemScreen(
                navigateToEditItem = { navController.navigate("${Routes.EditHabit.route}/$it") },
                )
        }
        composable(Routes.HabitList.route){ HabitListScreen(
            navigateToHabitGet = { navController.navigate("${Routes.HabitItem.route}/${it}")}
        )}

        composable(Routes.HabitForTodayList.route){ HabitsForTodayListScreen(
            navigateToHabitGet = { navController.navigate("${Routes.HabitItem.route}/${it}")}
        )
        }

        composable(
            route = Routes.EditHabit.routeWithArgs,
            arguments = listOf(navArgument(Routes.EditHabit.habitIdArg){
                type = NavType.IntType
            })){
            HabitEditScreen()
        }
    }

}