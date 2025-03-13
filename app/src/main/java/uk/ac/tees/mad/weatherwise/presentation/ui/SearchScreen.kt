package uk.ac.tees.mad.weatherwise.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import uk.ac.tees.mad.weatherwise.presentation.components.SearchTopAppBar

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }
    Box(modifier=modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            SearchTopAppBar(
                query = query,
                onQueryChange = {query  = it},
                onSearch = {}
            )
        }
        LazyColumn {

        }
    }
}