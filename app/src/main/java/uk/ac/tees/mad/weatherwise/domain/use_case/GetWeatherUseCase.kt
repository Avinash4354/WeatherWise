package uk.ac.tees.mad.weatherwise.domain.use_case

import uk.ac.tees.mad.weatherwise.domain.model.WeatherData
import uk.ac.tees.mad.weatherwise.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double, apiKey: String): WeatherData {
        return repository.getWeather(lat, lon, apiKey)
    }
}