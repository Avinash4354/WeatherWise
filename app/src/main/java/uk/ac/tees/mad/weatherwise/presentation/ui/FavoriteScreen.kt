package uk.ac.tees.mad.weatherwise.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.weatherwise.presentation.components.FavoriteLocationCard

@Composable
fun FavoriteScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Box(modifier=modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Text("Favorite Places",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            LazyColumn {
                items(10){
                    FavoriteLocationCard(
                        "Patna",
                        "IND",
                        "Cold",
                        "22.34",
                        Icons.Default.Star
                    ) {
                        Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}