package com.example.gruppe22_in2000.viewmodel

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gruppe22_in2000.model.DataSource
import com.example.gruppe22_in2000.model.HttpRoutes
import com.example.gruppe22_in2000.model.LocationForecastDataClasses.LocationDetails
import com.example.gruppe22_in2000.model.gps.GeocodingResponse
import com.example.gruppe22_in2000.model.gps.PlacesApiResponse
import com.example.gruppe22_in2000.model.gps.SavedLocation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class WeatherViewModel(context: Context) : ViewModel() {
    private val ds: DataSource = DataSource()
    // private val _uiState = MutableStateFlow(WeatherUiState(location = ""))
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState
    // val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location.asStateFlow()

    private val tts = mutableStateOf<TextToSpeech?>(null)

    private val _savedLocations = MutableStateFlow<List<SavedLocation>>(emptyList())
    val savedLocations: StateFlow<List<SavedLocation>> = _savedLocations.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SavedLocation>>(emptyList())
    val searchResults: StateFlow<List<SavedLocation>> = _searchResults.asStateFlow()

    private val _locationSuggestions = MutableStateFlow<List<SavedLocation>>(emptyList())
    val locationSuggestions: StateFlow<List<SavedLocation>> = _locationSuggestions.asStateFlow()

    init {
        getWeatherData()
        val loadedLocations = loadLocations(context)
        _savedLocations.value = loadedLocations
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            val url = HttpRoutes.URLBuilder(uiState.value.lon, uiState.value.lat)
            val data = ds.retrieveWeatherData(url)
            val map =
                data.properties!!.timeseries.associate { it.time to it.data }
            _uiState.update { it.copy(weatherData = data, isLoaded = true, dataByTime = map) }
            Log.d("VM", "lon -> ${uiState.value.lat} lat -> ${uiState.value.lon}")
        }
    }

    fun searchWeather(query: String)  {
        viewModelScope.launch {
            val coordinates = getCoordinatesFromQuery(query)
            Log.d("VM", "Coordinates for $query: $coordinates") //  correct latitude and longitude for the given location:?
            coordinates?.let {
                updatePos(it.longitude.toString(), it.latitude.toString(), query)

            }
        }
    }





/*
    suspend fun searchWeather(query: String): Pair<Double?, Double?>? {
        val coordinates = getCoordinatesFromQuery(query)
        Log.d("VM", "Coordinates for $query: $coordinates")
        return if (coordinates != null) {
            updatePos(coordinates.longitude.toString(), coordinates.latitude.toString(), query)
            Pair(coordinates.latitude, coordinates.longitude)
        } else {
            null
        }
    }
*/

    fun clearSearchResults() {
        _searchResults.value = emptyList()
    }



    private suspend fun getCoordinatesFromQuery(query: String): LocationDetails? {
        val geocodingUrl =
        val client = HttpClient()
        val response: HttpResponse = try {
            client.get(geocodingUrl)
        } catch (e: Exception) {
            Log.e("VM", "Error fetching coordinates: ${e.message}")
            return null
        }
        Log.d("VM", "Geocoding API response: $response")

        val gson = Gson()
        val geocodingResponse = gson.fromJson(response.readText(), GeocodingResponse::class.java)

        return if (geocodingResponse.status == "OK") {
            val location = geocodingResponse.results.first().geometry.location
            LocationDetails(location.lat, location.lng)
        } else {
            null
        }
    }

    fun updateSearchResults(query: String) { // autocomplete API
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                val geocodingUrl =
                    "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=${query}&types=(cities)&language=en&key=AIzaSyC_yGrsC7md_hbBiVCgNo5ybJFad1L2GYM" // NY NOKKEL
                val client = HttpClient()
                val response: HttpResponse = try {
                    client.get(geocodingUrl)
                } catch (e: Exception) {
                    Log.e("VM", "Error fetching search results: ${e.message}")
                    return@launch
                }
                Log.d("VM", "Place Autocomplete API response: $response")

                val gson = Gson()
                val placesApiResponse = gson.fromJson(response.readText(), PlacesApiResponse::class.java)

                if (placesApiResponse.status == "OK") {
                    val searchResults = placesApiResponse.predictions.map { prediction ->
                        SavedLocation(prediction.description, "", "")
                    }
                    _searchResults.value = searchResults
                } else {
                    _searchResults.value = emptyList()
                }
            } else {
                _searchResults.value = emptyList()
            }
        }
    }




    fun updatePos(lon: String, lat: String, locationName: String) {
        viewModelScope.launch {
            Log.d("VM", "lon is ${uiState.value.lat} lat is ${uiState.value.lon}")
            Log.d("VM", "lon -> $lon lat -> $lat")
            //_uiState.update { it.copy(lon = lon, lat = lat, location = locationName) }
            //_location.emit(locationName)
            _uiState.update { it.copy(lon = lon, lat = lat, location = locationName) }
            _location.emit(locationName)

            getWeatherData()
            Log.d("VM", "After updatePos: ${uiState.value}")
        }
    }
    //TTS
    fun speak(context: Context, text: String) {
        if (tts.value == null) {
            tts.value = TextToSpeech(context) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    tts.value?.language = Locale("no", "NO")
                }
            }
        }
        tts.value?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
    //en del av tts, brukes for å forhinde memory leaks
    override fun onCleared() {
        tts.value?.stop()
        tts.value?.shutdown()
        super.onCleared()
    }

    fun updateUiState(newUiState: WeatherUiState) {
        _uiState.value = newUiState
    }

   /* fun addSavedLocation(savedLocation: SavedLocation) {
        val newSavedLocations = _savedLocations.value.toMutableList().apply {
            add(savedLocation)
        }
        _savedLocations.value = newSavedLocations
        saveLocations(newSavedLocations)
    }*/

    fun addSavedLocation(savedLocation: SavedLocation, context: Context) {
        val currentSavedLocations = _savedLocations.value // sjekker duplikater
        if (currentSavedLocations.any { it.name == savedLocation.name }) {
            Log.d("VM", "Location ${savedLocation.name} already exists.")
        } else {
            val newSavedLocations = currentSavedLocations.toMutableList().apply {
                add(savedLocation)
            }
            _savedLocations.value = newSavedLocations
            saveLocations(newSavedLocations, context)
        }
    }

    private fun saveLocations(savedLocations: List<SavedLocation>, context: Context) {
        val sharedPreferences = context.getSharedPreferences("saved_locations", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()

        val json = gson.toJson(savedLocations)
        editor.putString("locations", json)
        editor.apply()
    }

    private fun loadLocations(context: Context): List<SavedLocation> {
        val sharedPreferences = context.getSharedPreferences("saved_locations", Context.MODE_PRIVATE)
        val gson = Gson()

        val json = sharedPreferences.getString("locations", null)
        if (json.isNullOrEmpty()) {
            return emptyList()
        }

        val type = object : TypeToken<List<SavedLocation>>() {}.type
        return gson.fromJson(json, type)
    }

    fun removeSavedLocation(index: Int, context: Context) {
        val newSavedLocations = _savedLocations.value.toMutableList().apply {
            removeAt(index)
        }
        _savedLocations.value = newSavedLocations
        saveLocations(newSavedLocations, context)
    }

    suspend fun getCoordinatesForTest(query: String): LocationDetails? {
        return getCoordinatesFromQuery(query)
    }

}

