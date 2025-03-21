package uk.ac.tees.mad.weatherwise.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.weatherwise.presentation.components.HomeBackGround
import uk.ac.tees.mad.weatherwise.presentation.components.HomeTopAppbar
import uk.ac.tees.mad.weatherwise.presentation.components.IconText
import uk.ac.tees.mad.weatherwise.presentation.components.WeatherDetailBox
import uk.ac.tees.mad.weatherwise.presentation.viewmodel.FavoriteViewModel
import uk.ac.tees.mad.weatherwise.utils.Utils

@Composable
fun DetailedScreen(
    index: Int,
    viewModel: FavoriteViewModel,
    modifier: Modifier = Modifier
) {
    val currentWeatherEntity by viewModel.currentEntity.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.updateIndex(index)
    }
    Box(
        modifier = modifier
            .fillMaxSize(),

        ) {
        HomeBackGround()
        Column(modifier = Modifier.fillMaxSize()) {
            HomeTopAppbar(
                city = currentWeatherEntity?.city,
                country = currentWeatherEntity?.country,
            )
            Text(
                text = "${currentWeatherEntity?.temperature}°C",
                fontSize = 80.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )

            currentWeatherEntity?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = it.mainWeather+":",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Text(
                        text = it.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier
                    )
                }
                RefreshBox(it.timeStamp) {
                    viewModel.updateWeather()
                }
                Spacer(Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .fillMaxWidth()
                    ) {
                    IconText(Utils.formatTimestampToTime(it.sunrise))
                    IconText(Utils.formatTimestampToTime(it.sunset))
                }
                WeatherDetailBox(it)
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}