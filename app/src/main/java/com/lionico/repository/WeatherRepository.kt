/**
 * WeatherRepository.kt
 * Handles API calls to fetch weather data and processes the response.
 */
package com.lionico.repository

import com.lionico.api.WeatherApiService
import com.lionico.data.models.WeatherResponse
import retrofit2.Response

class WeatherRepository(private val apiService: WeatherApiService) {
    
    suspend fun getWeather(city: String, apiKey: String): Response<WeatherResponse> {
        return apiService.getWeather(city, apiKey)
    }
}
