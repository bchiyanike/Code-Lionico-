/**
 * WeatherViewModel.kt
 * Fetches weather data from OpenWeatherMap and exposes it to the UI.
 */
package com.lionico.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lionico.api.WeatherApiService
import com.lionico.data.models.WeatherResponse
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val _weatherData = MutableLiveData<String>()
    val weatherData: LiveData<String> get() = _weatherData

    private val apiService: WeatherApiService

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private const val API_KEY = "YOUR_API_KEY" // Replace with your OpenWeatherMap API key
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(WeatherApiService::class.java)
    }

    fun fetchWeather(city: String) {
        apiService.getWeather(city, API_KEY).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weather = response.body()
                    val description = weather?.weather?.firstOrNull()?.description ?: "No data"
                    val temp = weather?.main?.temp ?: "N/A"
                    _weatherData.value = "Weather in ${weather?.name}: $description, ${temp}°C"
                } else {
                    _weatherData.value = "Failed to load weather data"
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                _weatherData.value = "Error: ${t.message}"
            }
        })
    }
}
