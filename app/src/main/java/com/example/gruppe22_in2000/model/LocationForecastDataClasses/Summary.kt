package com.example.gruppe22_in2000.model.LocationForecastDataClasses

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class Summary (

  @SerializedName("symbol_code" ) var symbolCode : String? = null

)