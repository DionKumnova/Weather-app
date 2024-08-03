package com.example.gruppe22_in2000.model.LocationForecastDataClasses

import com.google.gson.annotations.SerializedName


@kotlinx.serialization.Serializable
data class Meta (

  @SerializedName("updated_at" ) var updatedAt : String? = null,
  @SerializedName("units"      ) var units     : Units?  = Units()

)