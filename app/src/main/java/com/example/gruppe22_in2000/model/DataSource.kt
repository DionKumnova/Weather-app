package com.example.gruppe22_in2000.model

import android.util.Log
import com.example.gruppe22_in2000.model.LocationForecastDataClasses.WeatherData
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*


class DataSource {
    private val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        } }
    suspend fun retrieveWeatherData(URL: String): WeatherData {

        return try {
            val response: HttpResponse = client.request(URL)
            val json = response.readText()
            //val gson = Gson()
            Gson().fromJson(json, WeatherData::class.java)
        } catch (e: Exception) {
            Log.e("DataSource", "Error fetching weather data: ${e.message}")
            WeatherData()
        }
    }

}