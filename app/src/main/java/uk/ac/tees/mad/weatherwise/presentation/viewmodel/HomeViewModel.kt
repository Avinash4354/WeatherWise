package uk.ac.tees.mad.weatherwise.presentation.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.weatherwise.domain.model.WeatherData
import uk.ac.tees.mad.weatherwise.domain.use_case.GetWeatherUseCase
import uk.ac.tees.mad.weatherwise.utils.Constants
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val fusedLocationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
):ViewModel() {

    private val _userLocation = MutableStateFlow<Location?>(null)
    val userLocation: StateFlow<Location?> get() = _userLocation

    val lat = 44.34
    val lon = 10.99

    private val _weatherState = MutableStateFlow<WeatherData?>(null)
    val weatherState:StateFlow<WeatherData?> get() = _weatherState

    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _weatherState.value = getWeatherUseCase(lat, lon, Constants.WEATHER_API)
        }
    }


    fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                _userLocation.value = location
            }
            .addOnFailureListener {
                _userLocation.value = null
            }
    }
}