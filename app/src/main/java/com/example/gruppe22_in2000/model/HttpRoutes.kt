package com.example.gruppe22_in2000.model

object HttpRoutes {
    private const val BASE_URL = "https://api.met.no/weatherapi/locationforecast/2.0/compact?"
    @JvmStatic
    fun URLBuilder(lon: String, lat: String): String {
        return BASE_URL + "lat=$lat&lon=$lon"
    }
}