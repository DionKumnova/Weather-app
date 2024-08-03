package com.example.gruppe22_in2000.model.gps

import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponse(
    val status: String,
    val results: List<GeocodingResult>
)