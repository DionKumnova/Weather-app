package com.example.gruppe22_in2000.model.LocationForecastDataClasses

import com.google.gson.annotations.SerializedName


@kotlinx.serialization.Serializable
data class Timeseries (

  @SerializedName("time" ) var time : String? = null,
  @SerializedName("data" ) var data : Data?   = Data()

)