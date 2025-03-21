package uk.ac.tees.mad.weatherwise.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import uk.ac.tees.mad.weatherwise.R

@Composable
fun IconText(
    text:String,
    modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.baseline_cloudy_snowing_24),
            "sunset_sunrise",
        )
        Text(text,
        color = Color.White
        )
    }
}