package uk.ac.tees.mad.weatherwise.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import uk.ac.tees.mad.weatherwise.presentation.components.MainBottomBar
import uk.ac.tees.mad.weatherwise.presentation.viewmodel.HomeViewModel

@Composable
fun MainScreen(navController: NavController) {
    var selectedScreen by rememberSaveable { mutableIntStateOf(0) }
    val homeViewModel:HomeViewModel = hiltViewModel()
    Scaffold(
        bottomBar = { MainBottomBar(
            selectedScreen,
            onClick = {selectedScreen=it}
        ) }
    ) { paddingValues ->
        when(selectedScreen){
            0-> HomeScreen(homeViewModel)
            1-> SearchScreen(modifier = Modifier.padding(paddingValues))
            2-> FavoriteScreen(modifier = Modifier.padding(paddingValues), navController = navController)
            3-> ProfileScreen(navController,modifier = Modifier.padding(paddingValues))
        }

    }
}