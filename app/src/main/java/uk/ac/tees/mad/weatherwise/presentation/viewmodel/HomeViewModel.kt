package uk.ac.tees.mad.weatherwise.presentation.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toUpperCase
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.weatherwise.data.local.WeatherEntity
import uk.ac.tees.mad.weatherwise.domain.model.WeatherData
import uk.ac.tees.mad.weatherwise.domain.repository.WeatherRepository
import uk.ac.tees.mad.weatherwise.domain.use_case.GetWeatherUseCase
import uk.ac.tees.mad.weatherwise.utils.Constants
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val auth: FirebaseAuth,
    @ApplicationContext private val context: Context
):ViewModel() {

    private val _userLocation = MutableStateFlow<Location?>(null)
    val userLocation: StateFlow<Location?> get() = _userLocation

    private val _currentLocationData = MutableStateFlow<WeatherEntity?>(null)
    val currentLocationData:Flow<WeatherEntity?> get() = _currentLocationData

    private val currentUser = auth.currentUser?.uid?:""

    init {
        viewModelScope.launch {
            repository.getCurrentLocation(currentUser).collect{
                _currentLocationData.value = it
            }
        }
    }

    val lat = 25.5941
    val lon = 85.1376

    private val _weatherState = MutableStateFlow<WeatherData?>(null)
    val weatherState:StateFlow<WeatherData?> get() = _weatherState


    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _weatherState.value = getWeatherUseCase(lat, lon, Constants.WEATHER_API)
            if (_weatherState.value != null) {
                if (_currentLocationData.value==null) {
                    repository.addWeatherData(
                        WeatherEntity(
                            userId = currentUser,
                            latitude = lat,
                            longitude = lon,
                            mainWeather = _weatherState.value!!.mainWeather,
                            description = _weatherState.value!!.description,
                            temperature = _weatherState.value!!.temperature,
                            feelsLike = _weatherState.value!!.feelsLike,
                            minTemp = _weatherState.value!!.tempMin,
                            maxTemp = _weatherState.value!!.tempMax,
                            humidity = _weatherState.value!!.humidity,
                            visibility = _weatherState.value!!.visibility,
                            windSpeed = _weatherState.value!!.windSpeed,
                            sunrise = _weatherState.value!!.sunrise,
                            sunset = _weatherState.value!!.sunset,
                            dataType = "current_location",
                            city = "New York".uppercase(Locale.ROOT),
                            country = "United States"
                        )
                    )
                }
                else{
                    repository.updateCurrentLocation(
                        _currentLocationData.value!!.copy(
                            latitude = lat,
                            longitude = lon,
                            mainWeather = _weatherState.value!!.mainWeather,
                            description = _weatherState.value!!.description,
                            temperature = _weatherState.value!!.temperature,
                            feelsLike = _weatherState.value!!.feelsLike,
                            minTemp = _weatherState.value!!.tempMin,
                            maxTemp = _weatherState.value!!.tempMax,
                            humidity = _weatherState.value!!.humidity,
                            visibility = _weatherState.value!!.visibility,
                            windSpeed = _weatherState.value!!.windSpeed,
                            sunrise = _weatherState.value!!.sunrise,
                            sunset = _weatherState.value!!.sunset,
                            dataType = "current_location",
                            city = "Patna".uppercase(Locale.ROOT),
                            country = "India"
                    ))
                }
            }
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