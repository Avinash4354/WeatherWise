package uk.ac.tees.mad.weatherwise.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.weatherwise.domain.model.WeatherData

@Composable
fun WeatherDetailBox(weatherData: WeatherData,modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(Color(0x27FFFFFF), shape = RoundedCornerShape(12.dp))
                .border(2.dp, Color.White, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                DetailDoubleText(weatherData.feelsLike.toString(),"Feels Like")
                VerticalDivider(
                    thickness = 4.dp, color = Color(0xB7FFFFFF),
                    modifier = Modifier
                        .size(16.dp)
                )
                DetailDoubleText(weatherData.humidity.toString(),"Humidity")
                VerticalDivider(
                    thickness = 4.dp, color = Color(0xB7FFFFFF),
                    modifier = Modifier
                        .size(16.dp)
                )
                DetailDoubleText(weatherData.visibility.toString(),"Visibility")
            }
            Spacer(Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                DetailDoubleText(weatherData.windSpeed.toString(),"Wind Speed")
                VerticalDivider(
                    thickness = 4.dp, color = Color(0xB7FFFFFF),
                    modifier = Modifier
                        .size(16.dp)
                )
                DetailDoubleText(weatherData.tempMin.toString(),"Min Temp")
                VerticalDivider(
                    thickness = 4.dp, color = Color(0xB7FFFFFF),
                    modifier = Modifier
                        .size(16.dp)
                )
                DetailDoubleText(weatherData.tempMax.toString(),"Max Temp")
            }
        }
    }
}