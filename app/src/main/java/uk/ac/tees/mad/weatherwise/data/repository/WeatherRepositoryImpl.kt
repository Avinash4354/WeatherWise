package uk.ac.tees.mad.weatherwise.data.repository

import uk.ac.tees.mad.weatherwise.data.remote.WeatherApiService
import uk.ac.tees.mad.weatherwise.domain.model.WeatherData
import uk.ac.tees.mad.weatherwise.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double, apiKey: String): WeatherData {
        val response = apiService.getWeather(lat, lon, apiKey)
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
}