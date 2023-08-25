package ge.giosan777.matutu.mbasemobile.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ge.giosan777.matutu.mbasemobile.ScreenMobileBase
import ge.giosan777.matutu.mbasemobile.ScreenMobileBaseOrg
import ge.giosan777.matutu.mbasemobile.WelcomeScreen
import ge.giosan777.matutu.mbasemobile.screen_components.AddCompanyCard

@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.MBase.route) {
        composable(
            route = Screen.MBase.route
        ){
            ScreenMobileBase(navController)
        }
        composable(
            route = Screen.MBaseOrg.route
        ){
            ScreenMobileBaseOrg(navController)
        }
        composable(
            route = Screen.WelcomeScreen.route
        ){
            WelcomeScreen(navController)
        }
        composable(
            route = Screen.AddCompanyCard.route
        ){
            AddCompanyCard(navController)
        }
    }
}