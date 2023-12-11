import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.settings.PolicyScreen
import com.example.habittrackerapp.habit.screens.HabitEditScreen
import com.example.habittrackerapp.habit.screens.HabitItemScreen
import com.example.habittrackerapp.habit.screens.HabitListScreen
import com.example.habittrackerapp.habit.screens.HabitQuestionnaireScreen
import com.example.habittrackerapp.habit.screens.HabitsForTodayListScreen
import com.example.habittrackerapp.AboutTheAppScreen
import com.example.habittrackerapp.AboutUsScreen
import com.example.habittrackerapp.settings.AccountSetting
import com.example.habittrackerapp.settings.EditProfile
import com.example.habittrackerapp.note.NoteScreen
import com.example.habittrackerapp.settings.UserProfileScreen
import com.example.habittrackerapp.note.DisplayNotesList
import com.example.habittrackerapp.note.EditNote
import com.example.habittrackerapp.signInSignUp.SignSignUpScreen
import com.example.habittrackerapp.signInSignUp.UserSignInScreen
import com.example.habittrackerapp.signInSignUp.UserSignUp
import com.example.habittrackerapp.note.ViewSingleNote
import com.example.habittrackerapp.settings.Appearance


/**
 * Class that specify the routes of different screens
 */

sealed class Routes(val route:String)  {
    //==============================================================================
    //Sign in Sign out screens
    object SignUp : Routes("SignUpScreenRoute")
    object SignUpSignIn : Routes("SignUpSignUpScreenRoute")
    object SignIn : Routes("SignInScreenRoute")
    //==============================================================================
    //Information pages
    object AboutApp : Routes("AboutAppScreenRoute")
    object AboutUs : Routes("AboutUsScreenRoute")
    //==============================================================================
    //Notes Routes
    object Note: Routes("NoteScreenRoute")
    object ViewSingle: Routes("SingleNoteScreenRoute/{id}"){
        fun go(id: String) = "ContactScreenRoute/$id"
    }
    object EditNote: Routes("EditNoteScreenRoute/{id}")
    object ViewList: Routes("NoteListScreenRoute")
    //==============================================================================
    //Habit Routes
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
    //==============================================================================
    // profile screen routes
    object Profile:Routes("ProfileScreenRoute")
    object Setting:Routes("SettingScreenRoute")
    object  Policy: Routes("PolicyScreenRoute")
    object  EditProfile: Routes("EditProfileRoute")
    object AccountSetting: Routes("AccountSettingRoute")
    object Appearance: Routes("AppearanceScreen")
    //==============================================================================
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

        //navigation bar routes
        composable(Routes.AboutApp.route) { AboutTheAppScreen() }
        composable(Routes.AboutUs.route) { AboutUsScreen() }
        composable(Routes.Note.route){ NoteScreen() }

        //sign in sign out
        composable(Routes.SignIn.route){ UserSignInScreen() }
        composable(Routes.SignUpSignIn.route){ SignSignUpScreen() }
        composable(Routes.SignUp.route) { UserSignUp() }

        //Notes
        composable(Routes.ViewList.route){ DisplayNotesList() }
        composable(Routes.ViewSingle.route){
            ViewSingleNote(it.arguments?.getString("id")?:"")
        }
        composable(Routes.EditNote.route){ EditNote(it.arguments?.getString("id")?:"" ) }


        //Habits
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
        )}
        composable(
            route = Routes.EditHabit.routeWithArgs,
            arguments = listOf(navArgument(Routes.EditHabit.habitIdArg){
                type = NavType.IntType
            })){
            HabitEditScreen()
        }

        // profile screen routes
        composable(Routes.EditProfile.route){ EditProfile()}
        composable(Routes.Profile.route){ UserProfileScreen()}
        composable(Routes.Policy.route){ PolicyScreen()}
        composable(Routes.AccountSetting.route){ AccountSetting()}
        composable(Routes.Appearance.route){ Appearance()}


        //Deep Links for Launcher Apps
        //- goes to habit screen
        composable("deeplink2?desc={desc}",
            deepLinks = listOf(navDeepLink { uriPattern = "example://compose.deeplink2/?desc={desc}" })
        ) { backStackEntry ->
            HabitQuestionnaireScreen(backStackEntry.arguments?.getString("desc"))
        }
        //- goes to about screen
        composable("deeplink2?id={id}",
            deepLinks = listOf(navDeepLink { uriPattern = "example://compose.deeplink2/?id={id}" })
        ) { backStackEntry ->
            AboutTheAppScreen(backStackEntry.arguments?.getString("id"))
        }
        //- goes to the signup screen
        composable("deeplink2?name={name}",
            deepLinks = listOf(navDeepLink { uriPattern = "example://compose.deeplink2/?name={name}"})
        ) { backStackEntry ->
            UserSignUp(backStackEntry.arguments?.getString("name"))
        }
    }

}