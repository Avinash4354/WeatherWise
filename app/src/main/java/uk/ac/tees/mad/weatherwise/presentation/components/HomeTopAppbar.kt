package uk.ac.tees.mad.weatherwise.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.weatherwise.presentation.ui.theme.WeatherWiseTheme

@Composable
fun HomeTopAppbar(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .padding(top = 40.dp)
        .padding(horizontal = 16.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("New York",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text("USA",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White

            )
        }
        Spacer(Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color(0x27FFFFFF), shape = RoundedCornerShape(12.dp))
                .border(2.dp, Color.White, RoundedCornerShape(12.dp))
        ) {
            Text("Last updated: 1h",
                color = Color.White,
                modifier=Modifier.padding(start = 12.dp))
            IconButton(onClick = {}) {
                Icon(Icons.Default.Refresh,
                    contentDescription = "refresh",
                    tint = Color.White
                    )
            }
        }
    }
}
