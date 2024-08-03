package com.example.gruppe22_in2000.model.LocationForecastDataClasses

import com.google.gson.annotations.SerializedName


@kotlinx.serialization.Serializable
data class Instant (

  @SerializedName("details" ) var details : Details? = Details()


)