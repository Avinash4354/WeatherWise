package uk.ac.tees.mad.weatherwise.presentation.ui

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.weatherwise.R
import uk.ac.tees.mad.weatherwise.data.model.GeocodeResult
import uk.ac.tees.mad.weatherwise.presentation.components.ChangeLocationDialog
import uk.ac.tees.mad.weatherwise.presentation.components.CurrentLocationDialog
import uk.ac.tees.mad.weatherwise.presentation.components.LocationSearchCard
import uk.ac.tees.mad.weatherwise.presentation.components.SearchTopAppBar
import uk.ac.tees.mad.weatherwise.presentation.components.SettingButton
import uk.ac.tees.mad.weatherwise.presentation.viewmodel.SearchScreenViewModel

@Composable
fun CurrentLocationScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val query by viewModel.query.collectAsState()
    val locations by viewModel.locations.collectAsState()
    val context = LocalContext.current
    var showDialog1 by remember { mutableStateOf(false) }
    var showDialog2 by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf<GeocodeResult?>(null) }

    val isPermission by viewModel.isPermission.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.hasPermission(true)
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Box(modifier=Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.height(22.dp))
            Text("Change Location",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
                )
            SettingButton(
                ImageVector.vectorResource(R.drawable.baseline_my_location_24),
                "Current Location"
            ) {
                if (isPermission) {
                    message = "Change location to your current location"
                    showDialog2 = true
                }else{
                    Toast.makeText(context, "No permission for location", Toast.LENGTH_SHORT).show()
                }
            }
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
                        showHeart = false,
                        onFavoriteClick = {},
                        onClick = {
                            selectedLocation = location
                            message = "Change your location to ${location.name}"
                            showDialog1 = true}
                    )
                }
            }
        }
    }
    if (showDialog1 && selectedLocation!=null){
        ChangeLocationDialog(
            message,
            {
                viewModel.saveToFavorite("current_location",selectedLocation!!)
                showDialog1 = false},
            {showDialog1=false}
        )
    }
    if (showDialog2){
        CurrentLocationDialog(
            onSave = {city,country->
                viewModel.updateCurrentLocation(city,country)
                showDialog2 = false
            },
            onDismiss = {showDialog2 = false}
        )
    }
}