package uk.ac.tees.mad.weatherwise.presentation.ui

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.weatherwise.presentation.components.HomeBackGround
import uk.ac.tees.mad.weatherwise.presentation.components.HomeTopAppbar
import uk.ac.tees.mad.weatherwise.presentation.components.WeatherDetailBox
import uk.ac.tees.mad.weatherwise.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier) {
    val userLocation by homeViewModel.userLocation.collectAsState()

//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            homeViewModel.fetchLocation()
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//    }

    Box(modifier=modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
        ){
        HomeBackGround()
        HomeTopAppbar(
            onRefresh = {homeViewModel.fetchLocation()},
            modifier = Modifier.align(Alignment.TopCenter))
        Text(text = "19°",
            fontSize = 150.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.White,
            modifier = Modifier.align(Alignment.TopStart)
                .padding(start = 16.dp, top = 110.dp)
        )

        Text(text = "",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            modifier = Modifier
                .padding(top = 220.dp)
                .rotate(-90f)
                .align(Alignment.TopEnd)
        )

        WeatherDetailBox(modifier =Modifier.align(Alignment.BottomCenter)
            .padding(bottom = 68.dp)
        )
    }
}