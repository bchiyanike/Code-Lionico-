package com.lionico.network

import com.google.gson.annotations.SerializedName

// Primary data class representing the API response
data class WeatherResponse(
    @SerializedName("weather") val weather: List<WeatherDescription>, // Weather condition details
    @SerializedName("main") val main: WeatherMain, // Temperature and humidity
    @SerializedName("wind") val wind: WeatherWind, // Wind speed
    @SerializedName("name") val cityName: String // City name
)

// Weather condition details (e.g., "Clear", "Cloudy")
data class WeatherDescription(
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String
)

// Temperature, pressure, and humidity data
data class WeatherMain(
    @SerializedName("temp") val temp: Double, // Temperature in Celsius
    @SerializedName("humidity") val humidity: Int // Humidity percentage
)

// Wind speed data
data class WeatherWind(
    @SerializedName("speed") val speed: Double // Wind speed in m/s
)
