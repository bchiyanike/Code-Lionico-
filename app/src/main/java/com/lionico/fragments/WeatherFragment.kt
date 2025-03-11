/**
 * WeatherFragment.kt
 * Displays current weather data fetched from OpenWeatherMap.
 */
package com.lionico.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lionico.R
import com.lionico.viewmodels.WeatherViewModel

class WeatherFragment : Fragment() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var tvWeather: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        tvWeather = view.findViewById(R.id.tvWeather)
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        observeWeatherData()
        viewModel.fetchWeather("YourCity") // Replace "YourCity" with a real city

        return view
    }

    private fun observeWeatherData() {
        viewModel.weatherData.observe(viewLifecycleOwner) { weather ->
            tvWeather.text = weather
        }
    }
}
