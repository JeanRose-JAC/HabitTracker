import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.navigation.AboutScreen
import com.example.habittrackerapp.navigation.UserSignUp


/**
 * Class that specify the routes of different screens
 */

sealed class Routes(val route:String)  {
    object SignUp : Routes("SignUpScreenRoute")
    object About : Routes("AboutScreenRoute")
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
    }

}