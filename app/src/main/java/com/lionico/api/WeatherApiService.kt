/**
 * WeatherApiService.kt
 * This interface defines API calls to fetch weather data using Retrofit.
 */
package com.lionico.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import com.lionico.data.models.WeatherResponse

interface WeatherApiService {
    @GET("weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Call<WeatherResponse>
}
