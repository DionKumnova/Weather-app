package com.example.gruppe22_in2000.model.gps
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingLocation(
    val lat: Double,
    val lng: Double
)