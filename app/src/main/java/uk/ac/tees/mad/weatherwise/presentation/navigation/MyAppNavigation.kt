package uk.ac.tees.mad.weatherwise.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.ac.tees.mad.weatherwise.presentation.ui.AuthenticationScreen
import uk.ac.tees.mad.weatherwise.presentation.ui.CurrentLocationScreen
import uk.ac.tees.mad.weatherwise.presentation.ui.DetailedScreen
import uk.ac.tees.mad.weatherwise.presentation.ui.MainScreen
import uk.ac.tees.mad.weatherwise.presentation.ui.SplashScreen
import uk.ac.tees.mad.weatherwise.presentation.viewmodel.FavoriteViewModel
import uk.ac.tees.mad.weatherwise.presentation.viewmodel.HomeViewModel

@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {

        composable(Screens.SplashScreen.route){
            SplashScreen(navController)
        }

        composable(Screens.MainScreen.route) {
            MainScreen(homeViewModel,navController)
        }

        composable(Screens.AuthenticationScreen.route){
            AuthenticationScreen(navController)
        }

        composable(route = Screens.DetailedScreen.route+"/{place_index}",
            arguments = listOf(navArgument("place_index"){
                type = NavType.IntType
            })
            ) {backStackEntry->
            val placeIndex = backStackEntry.arguments?.getInt("place_index") ?: 0
            val viewModel:FavoriteViewModel = hiltViewModel()
            DetailedScreen(placeIndex,viewModel)
        }

        composable(Screens.CurrentLocationScreen.route){
            CurrentLocationScreen()
        }

    }
}