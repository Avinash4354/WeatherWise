package uk.ac.tees.mad.weatherwise.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeatherData(entity:WeatherEntity)

    @Query("SELECT * FROM weather_data WHERE userId = :userId and dataType = 'favorite_location'")
    fun getFavoriteLocations(userId:String):Flow<List<WeatherEntity>>

    @Query("SELECT * FROM weather_data WHERE userId = :userId and dataType = 'current_location' LIMIT 1")
    fun getCurrentLocation(userId:String):Flow<WeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCurrentLocation(entity: WeatherEntity)

    @Delete
    suspend fun deleteFavoriteLocation(entity: WeatherEntity)
}