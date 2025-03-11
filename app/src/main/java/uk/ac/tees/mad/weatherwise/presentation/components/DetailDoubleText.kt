package uk.ac.tees.mad.weatherwise.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DetailDoubleText(
    value:String,
    title:String,
    ) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(text = value, fontWeight = FontWeight.Bold, color = Color.White)
        Text(text = title, color = Color.White)
    }
}