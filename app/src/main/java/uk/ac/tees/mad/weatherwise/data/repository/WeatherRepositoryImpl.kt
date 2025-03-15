package uk.ac.tees.mad.weatherwise.data.repository

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.weatherwise.data.local.WeatherDataDao
import uk.ac.tees.mad.weatherwise.data.local.WeatherEntity
import uk.ac.tees.mad.weatherwise.data.model.GeocodeResult
import uk.ac.tees.mad.weatherwise.data.remote.GeocodingApiService
import uk.ac.tees.mad.weatherwise.data.remote.WeatherApiService
import uk.ac.tees.mad.weatherwise.domain.model.WeatherData
import uk.ac.tees.mad.weatherwise.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiWeatherService: WeatherApiService,
    private val geocodingApiService: GeocodingApiService,
    private val dao: WeatherDataDao
) : WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double, apiKey: String): WeatherData {
        val response = apiWeatherService.getWeather(lat, lon, apiKey)
        return WeatherData(
            longitude = response.coord.lon,
            latitude = response.coord.lat,
            mainWeather = response.weather[0].main,
            description = response.weather[0].description,
            temperature = response.main.temp,
            feelsLike = response.main.feelsLike,
            tempMin = response.main.tempMin,
            tempMax = response.main.tempMax,
            humidity = response.main.humidity,
            visibility = response.visibility,
            windSpeed = response.wind.speed,
            sunrise = response.sys.sunrise,
            sunset = response.sys.sunset
        )
    }

    override suspend fun getGeocode(location: String, apiKey: String): List<GeocodeResult> {
        return geocodingApiService.getGeocode(location, apiKey = apiKey)
    }

    override suspend fun addWeatherData(entity: WeatherEntity) {
        dao.addWeatherData(entity)
    }

    override fun getFavoriteLocations(userId: String): Flow<List<WeatherEntity>> {
        return dao.getFavoriteLocations(userId)
    }

    override fun getCurrentLocation(userId: String): Flow<WeatherEntity?> {
        return dao.getCurrentLocation(userId)
    }

    override suspend fun updateCurrentLocation(entity: WeatherEntity) {
        dao.updateCurrentLocation(entity)
    }

    override suspend fun deleteFavoriteLocation(entity: WeatherEntity) {
        dao.deleteFavoriteLocation(entity)
    }
}