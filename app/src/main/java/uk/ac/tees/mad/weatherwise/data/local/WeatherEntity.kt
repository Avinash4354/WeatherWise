package uk.ac.tees.mad.weatherwise.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val latitude: Double,
    val longitude: Double,
    val mainWeather: String,
    val description:String,
    val temperature: Double,
    val feelsLike: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int,
    val visibility: Int,
    val windSpeed: Double,
    val sunrise: Long,
    val sunset: Long,
    val timeStamp: Long = System.currentTimeMillis(),
    val dataType: String,
    val city:String,
    val country:String
)
