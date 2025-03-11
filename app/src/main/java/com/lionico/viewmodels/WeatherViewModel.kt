package com.lionico.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lionico.network.WeatherApi
import com.lionico.network.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {
    
    // LiveData to hold weather response
    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> get() = _weatherData

    // LiveData for error messages
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // Function to fetch weather data from API
    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val call = WeatherApi.service.getCurrentWeather(city, apiKey)
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful) {
                        _weatherData.postValue(response.body()) // Update LiveData with weather data
                    } else {
                        _error.postValue("Failed to fetch weather: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    _error.postValue("Network error: ${t.message}")
                }
            })
        }
    }
}
