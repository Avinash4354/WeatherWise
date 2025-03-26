package uk.ac.tees.mad.weatherwise.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.weatherwise.presentation.components.LocationSearchCard
import uk.ac.tees.mad.weatherwise.presentation.components.SearchTopAppBar
import uk.ac.tees.mad.weatherwise.presentation.viewmodel.SearchScreenViewModel

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier) {
    val query by viewModel.query.collectAsState()
    val locations by viewModel.locations.collectAsState()
    val context = LocalContext.current
    Box(modifier=modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            SearchTopAppBar(
                query = query,
                onQueryChange = {viewModel.onChangeQuery(it)},
                onSearch = {viewModel.searchLocations()}
            )
            LazyColumn {
                items(locations) { location ->
                    LocationSearchCard(
                        cityName = location.name,
                        countryName = location.country,
                        showHeart = true,
                        onFavoriteClick = {
                            if (!it) {
                                viewModel.saveToFavorite("favorite_location",location)
                                Toast.makeText(context,"Saved to favorite", Toast.LENGTH_SHORT).show()
                            }
                        },
                        onClick = {}
                    )
                }
            }
        }
    }
}