/**
 * WeatherResponse.kt
 * Data model for parsing weather API responses from OpenWeatherMap.
 */
package com.lionico.data.models

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val name: String
)

data class Main(
    val temp: Double,
    val humidity: Int
)

data class Weather(
    val description: String
)
