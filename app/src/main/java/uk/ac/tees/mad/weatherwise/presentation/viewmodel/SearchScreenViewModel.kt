package uk.ac.tees.mad.weatherwise.presentation.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
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
import uk.ac.tees.mad.weatherwise.data.model.GeocodeResult
import uk.ac.tees.mad.weatherwise.domain.repository.WeatherRepository
import uk.ac.tees.mad.weatherwise.utils.Constants
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val auth: FirebaseAuth,
    private val fusedLocationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
):ViewModel() {

    private val _userLocation = MutableStateFlow<Location?>(null)
    val userLocation: StateFlow<Location?> get() = _userLocation

    private val _isPermission = MutableStateFlow(false)
    val isPermission:StateFlow<Boolean> get() = _isPermission

    private val _locations = MutableStateFlow<List<GeocodeResult>>(emptyList())
    val locations: StateFlow<List<GeocodeResult>> get() = _locations
    private val _query = MutableStateFlow("")
    val query :StateFlow<String> get() = _query

    private val _currentLocationData = MutableStateFlow<WeatherEntity?>(null)
    val currentLocationData: Flow<WeatherEntity?> get() = _currentLocationData

    private val currentUser = auth.currentUser?.uid?:""

    init {
        fetchLocation()
        viewModelScope.launch {
            repository.getCurrentLocation(currentUser).collect {
                _currentLocationData.value = it
            }
        }
    }

    fun searchLocations(){
        viewModelScope.launch {
            try {
                val result = repository.getGeocode(_query.value, apiKey = Constants.WEATHER_API)
                _locations.value = result
            }
            catch (e:Exception){
                Log.e("Geocode Error", e.message.toString())
            }
        }
    }

    fun saveToFavorite(location:GeocodeResult){
        viewModelScope.launch {
            val weatherResponse = repository.getWeather(
                location.lat,
                location.lon,
                Constants.WEATHER_API
            )

            repository.addWeatherData(WeatherEntity(
                latitude = weatherResponse.latitude,
                longitude = weatherResponse.longitude,
                mainWeather = weatherResponse.mainWeather,
                description = weatherResponse.description,
                temperature = weatherResponse.temperature,
                feelsLike = weatherResponse.feelsLike,
                minTemp = weatherResponse.tempMin,
                maxTemp = weatherResponse.tempMax,
                humidity = weatherResponse.humidity,
                visibility = weatherResponse.visibility,
                windSpeed = weatherResponse.windSpeed,
                sunrise = weatherResponse.sunrise,
                sunset = weatherResponse.sunset,
                icon = weatherResponse.icon,
                dataType = "favorite_location",
                city = location.name.uppercase(),
                country = location.country,
                userId = currentUser,
            ))
        }
    }

    fun updateCurrentLocation(location:GeocodeResult){
        viewModelScope.launch {
            val weatherResponse = repository.getWeather(
                location.lat,
                location.lon,
                Constants.WEATHER_API
            )

            repository.addWeatherData(_currentLocationData.value!!.copy(
                latitude = weatherResponse.latitude,
                longitude = weatherResponse.longitude,
                mainWeather = weatherResponse.mainWeather,
                description = weatherResponse.description,
                temperature = weatherResponse.temperature,
                feelsLike = weatherResponse.feelsLike,
                minTemp = weatherResponse.tempMin,
                maxTemp = weatherResponse.tempMax,
                humidity = weatherResponse.humidity,
                visibility = weatherResponse.visibility,
                windSpeed = weatherResponse.windSpeed,
                sunrise = weatherResponse.sunrise,
                sunset = weatherResponse.sunset,
                icon = weatherResponse.icon,
                dataType = "current_location",
                city = location.name.uppercase(),
                country = location.country
            ))
        }
    }

    fun updateCurrentLocation(city:String, country:String){
        if (_userLocation.value!=null) {

            viewModelScope.launch {
                val weatherResponse = repository.getWeather(
                    _userLocation.value!!.latitude,
                    _userLocation.value!!.longitude,
                    Constants.WEATHER_API
                )

                repository.addWeatherData(_currentLocationData.value!!.copy(
                    latitude = weatherResponse.latitude,
                    longitude = weatherResponse.longitude,
                    mainWeather = weatherResponse.mainWeather,
                    description = weatherResponse.description,
                    temperature = weatherResponse.temperature,
                    feelsLike = weatherResponse.feelsLike,
                    minTemp = weatherResponse.tempMin,
                    maxTemp = weatherResponse.tempMax,
                    humidity = weatherResponse.humidity,
                    visibility = weatherResponse.visibility,
                    windSpeed = weatherResponse.windSpeed,
                    sunrise = weatherResponse.sunrise,
                    sunset = weatherResponse.sunset,
                    icon = weatherResponse.icon,
                    dataType = "current_location",
                    city = city.uppercase(),
                    country = country
                ))
            }
        }
    }

    fun onChangeQuery(newQuery:String){
        _query.value = newQuery
    }

    private fun fetchLocation() {
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

    fun hasPermission(value:Boolean){
        _isPermission.value = value
    }
}