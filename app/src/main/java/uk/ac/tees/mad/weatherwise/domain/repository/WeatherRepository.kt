package uk.ac.tees.mad.weatherwise.domain.repository

import uk.ac.tees.mad.weatherwise.domain.model.WeatherData

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double, apiKey: String): WeatherData
}