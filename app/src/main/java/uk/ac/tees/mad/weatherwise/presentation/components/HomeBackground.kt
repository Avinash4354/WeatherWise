package uk.ac.tees.mad.weatherwise.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.tees.mad.weatherwise.R
import uk.ac.tees.mad.weatherwise.presentation.ui.HomeScreen
import uk.ac.tees.mad.weatherwise.presentation.ui.theme.WeatherWiseTheme

@Composable
fun HomeBackGround(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "cloudy",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        RainAnimation()
    }
}
