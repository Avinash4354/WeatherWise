package uk.ac.tees.mad.weatherwise.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import uk.ac.tees.mad.weatherwise.data.model.WeatherResponse

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}