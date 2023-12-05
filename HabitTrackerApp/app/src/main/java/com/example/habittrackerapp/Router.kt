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
import com.example.habittrackerapp.settings.PolicyScreen
import com.example.habittrackerapp.settings.SettingScreen
import com.example.habittrackerapp.habit.screens.HabitEditScreen
import com.example.habittrackerapp.habit.screens.HabitItemScreen
import com.example.habittrackerapp.habit.screens.HabitListScreen
import com.example.habittrackerapp.habit.screens.HabitQuestionnaireScreen
import com.example.habittrackerapp.habit.screens.HabitsForTodayListScreen
import com.example.habittrackerapp.navigation.AboutTheAppScreen
import com.example.habittrackerapp.navigation.AboutUsScreen
import com.example.habittrackerapp.navigation.AccountSetting
import com.example.habittrackerapp.navigation.EditProfile
import com.example.habittrackerapp.navigation.NoteScreen
import com.example.habittrackerapp.navigation.UserProfileScreen
import com.example.habittrackerapp.noteInput.EditNote
import com.example.habittrackerapp.signInSignUp.SignSignUpScreen
import com.example.habittrackerapp.signInSignUp.UserSignInScreen
import com.example.habittrackerapp.signInSignUp.UserSignUp
import com.example.habittrackerapp.noteInput.screens.NoteList
import com.example.habittrackerapp.noteInput.screens.SingleNoteElementDisplay


/**
 * Class that specify the routes of different screens
 */

sealed class Routes(val route:String)  {
    object SignUp : Routes("SignUpScreenRoute")
    object SignUpSignIn : Routes("SignUpSignUpScreenRoute")
    object SignIn : Routes("SignInScreenRoute")
    object AboutApp : Routes("AboutAppScreenRoute")
    object AboutUs : Routes("AboutUsScreenRoute")
    object Note: Routes("NoteScreenRoute")



    object ViewSingle: Routes("SingleNoteScreenRoute")
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

    // profile screen routes
    object Profile:Routes("ProfileScreenRoute")
    object Setting:Routes("SettingScreenRoute")
    object  Policy: Routes("PolicyScreenRoute")
    object  EditProfile: Routes("EditProfileRoute")
    object AccountSetting: Routes("AccountSettingRoute")
}

/**
 * Composable that allows for navigation within the app
 */
@SuppressLint("SuspiciousIndentation")
@Composable
fun Router() {
    val navController = LocalNavController.current
    NavHost(navController = navController as NavHostController,
        startDestination = Routes.AboutApp.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { fadeOut() }) {


        composable(Routes.SignUp.route) { UserSignUp() }
        composable(Routes.AboutApp.route) { AboutTheAppScreen()}
        composable(Routes.AboutUs.route) { AboutUsScreen()}
        composable(Routes.Note.route){ NoteScreen() }


        composable(Routes.ViewList.route){ NoteList()}
        //how to extract the elements from the text fields.....
        composable(Routes.ViewSingle.route){
            SingleNoteElementDisplay(it.arguments?.getString("id")?:"")
        }
        composable(Routes.EditNote.route){ EditNote(it.arguments?.getString("id")?:"" ) }

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

        // profile screen routes
        composable(Routes.EditProfile.route){ EditProfile() }
        composable(Routes.Profile.route){ UserProfileScreen()}
        composable(Routes.Setting.route){ SettingScreen()}
        composable(Routes.Policy.route){ PolicyScreen()}
        composable(Routes.AccountSetting.route){ AccountSetting()}

    }

}