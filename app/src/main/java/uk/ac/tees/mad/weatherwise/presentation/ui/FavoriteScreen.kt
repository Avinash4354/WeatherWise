package uk.ac.tees.mad.weatherwise.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.weatherwise.presentation.components.FavoriteLocationCard
import uk.ac.tees.mad.weatherwise.presentation.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val favoritePlaces by viewModel.favoriteLocationData.collectAsState()
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
                items(favoritePlaces){place->
                    FavoriteLocationCard(
                        place.city,
                        place.country,
                        place.mainWeather,
                        place.temperature.toString(),
                        Icons.Default.Star
                    ) {
                        viewModel.deleteFavorite(place)
                        Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}