package uk.ac.tees.mad.weatherwise.domain.model

data class WeatherData(
    val longitude: Double,
    val latitude: Double,
    val mainWeather: String,
    val description: String,
    val temperature: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int,
    val visibility: Int,
    val windSpeed: Double,
    val sunrise: Long,
    val sunset: Long
)
