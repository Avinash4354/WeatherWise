package uk.ac.tees.mad.weatherwise.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import uk.ac.tees.mad.weatherwise.utils.Utils

@Composable
fun HomeBackGround(weather:String) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(Utils.getBackground(weather)),
            contentDescription = "cloudy",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        when(weather){
            "Rain" -> RainAnimation()
            "Drizzle" -> RainAnimation()
            "Thunderstorm" -> RainAnimation()
            "Snow" -> SnowAnimation()
        }
    }
}
