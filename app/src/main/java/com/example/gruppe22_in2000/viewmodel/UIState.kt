package com.example.gruppe22_in2000.viewmodel

import com.example.gruppe22_in2000.model.LocationForecastDataClasses.Data
import com.example.gruppe22_in2000.model.LocationForecastDataClasses.WeatherData

/*
sealed class WeatherUiState {
    object Loading : WeatherUiState()

    data class Success(
    val weatherData: WeatherData = WeatherData(),
    val location: String = "Oslo",
    val dataByTime: Map<String?, Data?> = emptyMap(),
    val lat: String = "59.911491", //Lat for Oslo
    val lon: String = "10.757933", // Lon for Oslo
    val isLoaded: Boolean = false) : WeatherUiState()

    object Error : WeatherUiState()

}

 */
data class WeatherUiState(
    val weatherData: WeatherData = WeatherData(),
    val location: String = "Oslo",
    val dataByTime: Map<String?, Data?> = emptyMap(),
    val lat: String = "59.911491",
    val lon: String = "10.757933",
    val isLoaded: Boolean = false
)
