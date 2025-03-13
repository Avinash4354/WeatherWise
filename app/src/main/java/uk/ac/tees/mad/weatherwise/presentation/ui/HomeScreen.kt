package uk.ac.tees.mad.weatherwise.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.weatherwise.presentation.components.HomeBackGround
import uk.ac.tees.mad.weatherwise.presentation.components.HomeTopAppbar
import uk.ac.tees.mad.weatherwise.presentation.components.WeatherDetailBox
import uk.ac.tees.mad.weatherwise.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier) {
    val userLocation by homeViewModel.userLocation.collectAsState()
    val currentWeatherEntity by homeViewModel.currentLocationData.collectAsState(null)

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
            city = currentWeatherEntity?.city,
            country = currentWeatherEntity?.country,
            onRefresh = {homeViewModel.fetchWeather(homeViewModel.lat, homeViewModel.lon)},
            modifier = Modifier.align(Alignment.TopCenter))
        Text(text = "${currentWeatherEntity?.temperature}°",
            fontSize = 80.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.White,
            modifier = Modifier.align(Alignment.TopStart)
                .padding(start = 16.dp, top = 110.dp)
        )

        currentWeatherEntity?.let {
            Text(text = it.description,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 220.dp)
                    .rotate(-90f)
                    .align(Alignment.TopEnd)
            )
        }

        currentWeatherEntity?.let {
            WeatherDetailBox(
                it,
                modifier =Modifier.align(Alignment.BottomCenter)
                .padding(bottom = 68.dp)
            )
        }
    }
}