package uk.ac.tees.mad.weatherwise.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.weatherwise.R
import uk.ac.tees.mad.weatherwise.presentation.ui.theme.WeatherWiseTheme

@Composable
fun ProfileSection(userName: String, onEditClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(vertical = 16.dp, horizontal = 12.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
    ) {
            Image(
                painter = painterResource(id = R.drawable.placeholder_profile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.dp, color = Color.Gray, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = userName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

        IconButton(onClick = onEditClick) {
            Icon(
                Icons.Default.Edit,
                contentDescription = "Edit Name",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePrev() {
    WeatherWiseTheme {
        ProfileSection("Test Name") { }
    }
}