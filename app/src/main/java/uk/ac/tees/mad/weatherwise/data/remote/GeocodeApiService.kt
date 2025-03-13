package uk.ac.tees.mad.weatherwise.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import uk.ac.tees.mad.weatherwise.data.model.GeocodeResult

interface GeocodingApiService {
    @GET("geo/1.0/direct")
    suspend fun getGeocode(
        @Query("q") location: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String
    ): List<GeocodeResult>
}