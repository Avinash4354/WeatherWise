package uk.ac.tees.mad.weatherwise.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import uk.ac.tees.mad.weatherwise.presentation.components.HomeTopAppbar
import uk.ac.tees.mad.weatherwise.presentation.components.MainBottomBar

@Composable
fun MainScreen(navController: NavController) {
    var selectedScreen by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = { MainBottomBar(
            selectedScreen,
            onClick = {selectedScreen=it}
        ) }
    ) { paddingValues ->
        when(selectedScreen){
            0-> HomeScreen()
            1-> SearchScreen(modifier = Modifier.padding(paddingValues))
            2-> FavoriteScreen(modifier = Modifier.padding(paddingValues))
            3-> ProfileScreen(navController, modifier = Modifier.padding(paddingValues))
        }

    }
}