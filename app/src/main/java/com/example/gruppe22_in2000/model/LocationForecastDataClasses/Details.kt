package com.example.gruppe22_in2000.model.LocationForecastDataClasses

import com.google.gson.annotations.SerializedName


@kotlinx.serialization.Serializable
data class Details (

  @SerializedName("air_pressure_at_sea_level" ) var airPressureAtSeaLevel : String? = null,
  @SerializedName("air_temperature"           ) var airTemperature        : String? = null,
  @SerializedName("cloud_area_fraction"       ) var cloudAreaFraction     : String? = null,
  @SerializedName("precipitation_amount"      ) var precipitationAmount   : String? = null,
  @SerializedName("relative_humidity"         ) var relativeHumidity      : String? = null,
  @SerializedName("wind_from_direction"       ) var windFromDirection     : String? = null,
  @SerializedName("wind_speed"                ) var windSpeed             : String? = null

)