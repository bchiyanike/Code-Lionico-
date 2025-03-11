package com.lionico.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lionico.R
import com.lionico.viewmodels.WeatherViewModel

class WeatherFragment : Fragment() {
    
    private val viewModel: WeatherViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // UI Elements
        val tvCity = view.findViewById<TextView>(R.id.tvCity)
        val tvTemperature = view.findViewById<TextView>(R.id.tvTemperature)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val tvWindSpeed = view.findViewById<TextView>(R.id.tvWindSpeed)

        // Observe weather data
        viewModel.weatherData.observe(viewLifecycleOwner) { weather ->
            weather?.let {
                tvCity.text = it.cityName
                tvTemperature.text = "${it.main.temp}°C"
                tvDescription.text = it.weather.firstOrNull()?.description ?: "N/A"
                tvWindSpeed.text = "Wind: ${it.wind.speed} m/s"
            }
        }

        // Observe errors
        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        // Fetch weather data (Replace "YourCity" and "YourApiKey" with actual values)
        viewModel.fetchWeather("YourCity", "YourApiKey")
    }
}
