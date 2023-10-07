import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.navigation.UserSignUp


/**
 * Class is the different routes that can accure in the app
 */

sealed class Routes(val route:String)  {
    object SignUp : Routes("SignUpScreenRoute")
}

/**
 * The Route given the different route of a sealed object, it will go the function that that
 * route and render it on the application
 */
@SuppressLint("SuspiciousIndentation")
@Composable
fun Router() {
    val navController = LocalNavController.current
    NavHost(navController = navController as NavHostController,
        startDestination = Routes.SignUp.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { fadeOut() }) {

        composable(Routes.SignUp.route) { UserSignUp() }


    }

}