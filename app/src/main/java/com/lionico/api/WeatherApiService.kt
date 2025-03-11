package com.lionico.network

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Base URL for the weather API (Replace with actual API provider's URL)
private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

// Retrofit instance for making network requests
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create()) // Convert JSON responses to Kotlin objects
    .build()

// Interface defining the weather API endpoints
interface WeatherApiService {

    @GET("weather")
    fun getCurrentWeather(
        @Query("q") city: String,   // Query parameter for city name
        @Query("appid") apiKey: String, // API key for authentication
        @Query("units") units: String = "metric" // Units (metric for Celsius)
    ): Call<WeatherResponse> // Returns a call object to fetch weather data
}

// Singleton object to access the Weather API service
object WeatherApi {
    val service: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}
