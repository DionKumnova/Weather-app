package com.example.gruppe22_in2000.model.LocationForecastDataClasses

import com.google.gson.annotations.SerializedName


@kotlinx.serialization.Serializable
data class WeatherData (

  @SerializedName("type"       ) var type       : String?     = null,
  @SerializedName("geometry"   ) var geometry   : Geometry?   = Geometry(),
  @SerializedName("properties" ) var properties : Properties? = Properties()

)