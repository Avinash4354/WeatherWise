package uk.ac.tees.mad.weatherwise.presentation.navigation

sealed class Screens(val route:String) {

    object SplashScreen:Screens("splash_screen")
    object MainScreen:Screens("main_screen")
    object AuthenticationScreen:Screens("authentication_screen")
    object DetailedScreen:Screens("detailed_screen")
    object CurrentLocationScreen:Screens("current_location_screen")
}