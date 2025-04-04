package uk.ac.tees.mad.weatherwise.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val auth: FirebaseAuth,
    @ApplicationContext private val context: Context
) : ViewModel() {


    private val _currentLocationData = MutableStateFlow<WeatherEntity?>(null)
    val currentLocationData: Flow<WeatherEntity?> get() = _currentLocationData

    private val currentUser = auth.currentUser?.uid ?: ""

    init {
        viewModelScope.launch {
            repository.getCurrentLocation(currentUser).collect {
                if (it == null) {
                    _weatherState.value = getWeatherUseCase(40.7128, 74.0060, Constants.WEATHER_API)
                    if (_weatherState.value != null) {
                        repository.addWeatherData(
                            WeatherEntity(
                                userId = currentUser,
                                latitude = 40.7128,
                                longitude = 74.0060,
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
                                icon = _weatherState.value!!.icon,
                                dataType = "current_location",
                                city = "New York".uppercase(Locale.ROOT),
                                country = "United States"
                            )
                        )
                    }
                }
                else{
                    _currentLocationData.value = it
                }
            }
        }
    }

    private val _weatherState = MutableStateFlow<WeatherData?>(null)
    val weatherState: StateFlow<WeatherData?> get() = _weatherState


    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _weatherState.value = getWeatherUseCase(_currentLocationData.value!!.latitude,
                _currentLocationData.value!!.longitude, Constants.WEATHER_API)
            if (_weatherState.value != null) {
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
                        icon = _weatherState.value!!.icon,
                        timeStamp = System.currentTimeMillis(),
                        dataType = "current_location",
                        city = _currentLocationData.value!!.city,
                        country = _currentLocationData.value!!.country
                    )
                )
            }
        }
    }
}