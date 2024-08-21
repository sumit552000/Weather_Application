package com.example.weatherapp.viewmodel

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.RetrofitClient
import com.example.weatherapp.data.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String> get() = _cityName

    private val temp = MutableLiveData<String>()
    val temperature: LiveData<String> get() = temp

    private val minTemperature = MutableLiveData<String>()
    val minTemp: LiveData<String> get() = minTemperature

    private val maxTemperature = MutableLiveData<String>()
    val maxTemp: LiveData<String> get() = maxTemperature

    private val humidity = MutableLiveData<String>()
    val displayHumidity: LiveData<String> get() = humidity

    private val seaLevel = MutableLiveData<String>()
    val displaySeaLevel: LiveData<String> get() = seaLevel

    private val description = MutableLiveData<String>()
    val displayDescription: LiveData<String> get() = description

    private val windSpeed = MutableLiveData<String>()
    val displayWindSpeed: LiveData<String> get() = windSpeed

    private val sunRise = MutableLiveData<String>()
    val displaySunRise: LiveData<String> get() = sunRise

    private val sunSet = MutableLiveData<String>()
    val displaySunSet: LiveData<String> get() = sunSet

    fun fetchWeatherByCity(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response: WeatherResponse = RetrofitClient.instance.getWeatherByCity(city, apiKey)
                _cityName.value = response.name
                temp.value = "%.2f°C".format(response.main.temp - 273.15)
                minTemperature.value = "Min: %.2f°C".format(response.main.temp_min - 273.15)
                maxTemperature.value = "Max: %.2f°C".format(response.main.temp_max - 273.15)
                humidity.value = "${response.main.humidity}%"
                seaLevel.value = "${response.main.sea_level} hPa"
                description.value = response.weather[0].description
                windSpeed.value = "${response.wind.speed} m/s"
                sunRise.value = "${response.sys.sunrise}"
                sunSet.value = "${response.sys.sunset}"

                saveWeatherData(response)

            } catch (e: Exception) {
                _cityName.value = "Error fetching weather data"
            }
        }
    }

    private fun saveWeatherData(response: WeatherResponse) {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("WeatherPrefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("cityName", response.name)
            putString("temp", "%.2f°C".format(response.main.temp - 273.15))
            putString("minTemp", "Min: %.2f°C".format(response.main.temp_min - 273.15))
            putString("maxTemp", "Max: %.2f°C".format(response.main.temp_max - 273.15))
            putString("humidity", "${response.main.humidity}%")
            putString("seaLevel", "${response.main.sea_level} hPa")
            putString("description", response.weather[0].description)
            putString("windSpeed", "${response.wind.speed} m/s")
            putString("sunRise", "${response.sys.sunrise}")
            putString("sunSet", "${response.sys.sunset}")
            apply()
        }
    }
}
