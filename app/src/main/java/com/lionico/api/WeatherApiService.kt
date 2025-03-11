/**
 * WeatherApiService.kt
 * Defines the Retrofit API interface for fetching weather data from OpenWeatherMap.
 */
package com.lionico.api

import com.lionico.data.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric" // Fetch temperature in Celsius
    ): Call<WeatherResponse>
}
