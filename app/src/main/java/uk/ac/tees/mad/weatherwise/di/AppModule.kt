package uk.ac.tees.mad.weatherwise.di

import android.content.Context
import com.cloudinary.Cloudinary
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.weatherwise.data.local.WeatherDataDao
import uk.ac.tees.mad.weatherwise.data.local.WeatherDatabase
import uk.ac.tees.mad.weatherwise.data.remote.GeocodingApiService
import uk.ac.tees.mad.weatherwise.data.remote.WeatherApiService
import uk.ac.tees.mad.weatherwise.data.repository.WeatherRepositoryImpl
import uk.ac.tees.mad.weatherwise.domain.repository.WeatherRepository
import uk.ac.tees.mad.weatherwise.domain.use_case.GetWeatherUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):WeatherDatabase{
        return WeatherDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideWeatherDataDao(database: WeatherDatabase):WeatherDataDao{
        return database.weatherDataDao()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGeocodingApiService(retrofit: Retrofit): GeocodingApiService {
        return retrofit.create(GeocodingApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherApiService: WeatherApiService,
        geocodingApiService: GeocodingApiService,
        weatherDataDao: WeatherDataDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherApiService, geocodingApiService, weatherDataDao)
    }

    @Provides
    @Singleton
    fun provideGetWeatherUseCase(repository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideCloudinary():Cloudinary{
        val config = HashMap<String,String>().apply {
            put("cloud_name", "ducf4jogf")
            put("api_key", "624469855219998")
            put("api_secret", "vHRLgES5smMswESNm147O_EkTa4")
        }

        return Cloudinary(config)
    }


}