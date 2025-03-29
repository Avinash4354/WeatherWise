package uk.ac.tees.mad.weatherwise.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.weatherwise.utils.Utils

@Composable
fun RefreshBox(
    time:Long?,
    onRefresh:()->Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .background(Color(0x430277BD), shape = RoundedCornerShape(12.dp))
            .border(2.dp, Color.White, RoundedCornerShape(12.dp))
    ) {
        Text("Last updated: ${time?.let { Utils.getTimeAgo(it) }}",
            color = Color.White,
            modifier=Modifier.padding(start = 12.dp))
        IconButton(onClick = {onRefresh()}) {
            Icon(
                Icons.Default.Refresh,
                contentDescription = "refresh",
                tint = Color.White
            )
        }
    }
}