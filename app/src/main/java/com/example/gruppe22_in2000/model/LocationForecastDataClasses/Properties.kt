package com.example.gruppe22_in2000.model.LocationForecastDataClasses

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class Properties (

  @SerializedName("meta"       ) var meta       : Meta?                 = Meta(),
  @SerializedName("timeseries" ) var timeseries : List<Timeseries> = emptyList()

)