package com.example.gruppe22_in2000.model.LocationForecastDataClasses

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class Next12Hours (

  @SerializedName("summary" ) var summary : Summary? = Summary()

)