package uk.ac.tees.mad.weatherwise.domain.repository

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.weatherwise.data.local.WeatherEntity
import uk.ac.tees.mad.weatherwise.data.model.GeocodeResult
import uk.ac.tees.mad.weatherwise.domain.model.WeatherData

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double, apiKey: String): WeatherData
    suspend fun getGeocode(location:String, apiKey: String):List<GeocodeResult>
    suspend fun addWeatherData(entity: WeatherEntity)
    fun getFavoriteLocations(userId:String): Flow<List<WeatherEntity>>
    fun getCurrentLocation(userId:String):Flow<WeatherEntity?>
    suspend fun updateCurrentLocation(entity: WeatherEntity)
    suspend fun deleteFavoriteLocation(entity: WeatherEntity)
}