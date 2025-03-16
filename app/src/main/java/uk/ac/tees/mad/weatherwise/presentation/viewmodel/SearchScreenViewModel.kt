package uk.ac.tees.mad.weatherwise.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val auth: FirebaseAuth
):ViewModel() {

    private val _locations = MutableStateFlow<List<GeocodeResult>>(emptyList())
    val locations: StateFlow<List<GeocodeResult>> get() = _locations
    private val _query = MutableStateFlow("")
    val query :StateFlow<String> get() = _query

    val currentUser = auth.currentUser?.uid?:""

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
                userId = currentUser,
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
                dataType = "favorite_location",
                city = location.name.uppercase(),
                country = location.country
            ))
        }
    }

    fun onChangeQuery(newQuery:String){
        _query.value = newQuery
    }
}