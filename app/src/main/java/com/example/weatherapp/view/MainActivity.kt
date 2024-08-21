package com.example.weatherapp.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityMainBinding

    private val locationPermissionRequest: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
                loadLastWeatherData()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            getLocation()
        } else {
            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        weatherViewModel.cityName.observe(this) { weatherInfo ->
            binding.cityName.text = weatherInfo
        }
        weatherViewModel.temperature.observe(this) { weatherInfo ->
            binding.temp.text = weatherInfo
        }
        weatherViewModel.maxTemp.observe(this) { weatherInfo ->
            binding.maxTemp.text = weatherInfo
        }
        weatherViewModel.minTemp.observe(this) { weatherInfo ->
            binding.minTemp.text = weatherInfo
        }
        weatherViewModel.displayHumidity.observe(this) { weatherInfo ->
            binding.humidity.text = weatherInfo
        }
        weatherViewModel.displaySeaLevel.observe(this) { weatherInfo ->
            binding.pressure.text = weatherInfo
        }
        weatherViewModel.displayDescription.observe(this) { weatherInfo ->
            binding.condition.text = weatherInfo
            binding.weather.text = weatherInfo
            changeImageAccordingToWeatherCondition()
        }
        weatherViewModel.displayWindSpeed.observe(this) { weatherInfo ->
            binding.windSpeed.text = weatherInfo
        }
        weatherViewModel.displaySunRise.observe(this) { weatherInfo ->
            binding.sunrise.text = time(weatherInfo.toLong())
        }
        weatherViewModel.displaySunSet.observe(this) { weatherInfo ->
            binding.sunset.text = time(weatherInfo.toLong())
        }
        binding.day.text = dayName()
        binding.date.text = date()
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient.lastLocation.addOnCompleteListener { task: Task<Location?> ->
            val location = task.result
            if (location != null) {
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val cityName = addresses?.get(0)?.locality ?: "Unknown City"
                val apiKey = "paste API key here" // Paste your API key here
                weatherViewModel.fetchWeatherByCity(cityName, apiKey)
            } else {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show()
                loadLastWeatherData()
            }
        }
    }

    private fun loadLastWeatherData() {
        val sharedPreferences = getSharedPreferences("WeatherPrefs", MODE_PRIVATE)
        val cityName = sharedPreferences.getString("cityName", "Unknown City")
        val temp = sharedPreferences.getString("temp", "N/A")
        val minTemp = sharedPreferences.getString("minTemp", "N/A")
        val maxTemp = sharedPreferences.getString("maxTemp", "N/A")
        val humidity = sharedPreferences.getString("humidity", "N/A")
        val seaLevel = sharedPreferences.getString("seaLevel", "N/A")
        val description = sharedPreferences.getString("description", "N/A")
        val windSpeed = sharedPreferences.getString("windSpeed", "N/A")
        val sunRise = sharedPreferences.getString("sunRise", "N/A")
        val sunSet = sharedPreferences.getString("sunSet", "N/A")

        binding.cityName.text = cityName
        binding.temp.text = temp
        binding.minTemp.text = minTemp
        binding.maxTemp.text = maxTemp
        binding.humidity.text = humidity
        binding.pressure.text = seaLevel
        binding.condition.text = description
        binding.weather.text = description
        changeImageAccordingToWeatherCondition()
        binding.windSpeed.text = windSpeed
        binding.sunrise.text = time(sunRise?.toLong() ?: 0)
        binding.sunset.text = time(sunSet?.toLong() ?: 0)
    }

    private fun dayName(): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun date(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun changeImageAccordingToWeatherCondition() {
        when (weatherViewModel.displayDescription.value) {
            "clear sky", "sunny", "clear" -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
            "partly clouds", "clouds", "scattered clouds","overcast", "mist", "foggy" -> {
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }
            "light rain", "drizzle", "moderate rain", "showers", "heavy rain" -> {
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.rain)
            }
            "light snow", "moderate snow", "heavy snow", "blizzard" -> {
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView.setAnimation(R.raw.snow)
            }
            else -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
        }
        binding.lottieAnimationView.playAnimation()
    }

    private fun time(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format((Date(timestamp * 1000)))
    }
}
