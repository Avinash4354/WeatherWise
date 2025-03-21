package uk.ac.tees.mad.weatherwise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.weatherwise.data.local.WeatherEntity
import uk.ac.tees.mad.weatherwise.domain.model.WeatherData
import uk.ac.tees.mad.weatherwise.domain.repository.WeatherRepository
import uk.ac.tees.mad.weatherwise.domain.use_case.GetWeatherUseCase
import uk.ac.tees.mad.weatherwise.utils.Constants
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val auth: FirebaseAuth,
):ViewModel() {
    private val currentUser = auth.currentUser?.uid?:""

    private val _favoriteLocationData = MutableStateFlow<List<WeatherEntity>>(emptyList())
    val favoriteLocationData:StateFlow<List<WeatherEntity>> get() = _favoriteLocationData

    private val _currentEntity = MutableStateFlow<WeatherEntity?>(null)
    val currentEntity:StateFlow<WeatherEntity?> get() = _currentEntity
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex:StateFlow<Int>  get() = _currentIndex
    init {
        viewModelScope.launch {
            repository.getFavoriteLocations(currentUser).collect{
                _favoriteLocationData.value = it
                if (_favoriteLocationData.value.isNotEmpty()){
                    _currentEntity.value = _favoriteLocationData.value[_currentIndex.value]
                }
            }
        }
    }

    fun updateIndex(index:Int){
        _currentIndex.value = index
    }

    fun deleteFavorite(entity: WeatherEntity){
        viewModelScope.launch {
            repository.deleteFavoriteLocation(entity)
        }
    }

    private val _weatherState = MutableStateFlow<WeatherData?>(null)
    val weatherState: StateFlow<WeatherData?> get() = _weatherState
    fun updateWeather(){
        viewModelScope.launch {
            val lat = _currentEntity.value!!.latitude
            val lon = _currentEntity.value!!.longitude
            _weatherState.value = getWeatherUseCase(lat, lon, Constants.WEATHER_API)
            if (_weatherState.value != null) {
                repository.updateCurrentLocation(
                    _currentEntity.value!!.copy(
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
                        timeStamp = System.currentTimeMillis(),
                        dataType = "favorite_location",
                    )
                )
            }
        }
    }
}