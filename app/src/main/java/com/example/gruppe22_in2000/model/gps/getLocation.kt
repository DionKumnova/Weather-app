package com.example.gruppe22_in2000.model.gps

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Looper
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.example.gruppe22_in2000.viewmodel.WeatherViewModel
import com.google.android.gms.location.*
import io.ktor.utils.io.errors.*
import java.util.*

private const val REQUEST_CODE_LOCATION_PERMISSION = 100





fun getDeviceLocation(context: Context, viewModel: WeatherViewModel) {
    val locationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val currentLocation = mutableStateOf<Double?>(null)

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_CODE_LOCATION_PERMISSION
        )
    } else {
        try {
            val locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    currentLocation.value = locationResult.lastLocation.latitude
                    currentLocation.value = locationResult.lastLocation.longitude
                    println("locatioon")

                    println("${locationResult.lastLocation.latitude}")
                    println("${locationResult.lastLocation.longitude}")

                    val locationName =
                        getCityName(
                            locationResult.lastLocation.latitude,
                            locationResult.lastLocation.longitude, context
                        )

                    viewModel.updatePos(
                        locationResult.lastLocation.longitude.toString(),
                        locationResult.lastLocation.latitude.toString(),
                        locationName
                    )
                }
            }
            locationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()!!
            )
        } catch (e: Exception) {
            currentLocation.value = null
        }
    }
}

fun getCityName(latitude: Double, longitude: Double, context: Context): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        addresses?.firstOrNull()?.adminArea ?: "Unknown Location"
    } catch (e: IOException) {
        e.printStackTrace()
        "Unknown Location"
    }
}

/*
suspend fun getDeviceLocation(context: Context): List<Double?> {
    val locationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val currentLocation = mutableStateOf<Double?>(null)
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_CODE_LOCATION_PERMISSION)
    } else {
        try {
            val locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                //.setNumUpdates(1)
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    currentLocation.value = locationResult.lastLocation.latitude
                    currentLocation.value = locationResult.lastLocation.longitude

                    println("locatioon")

                    println("${locationResult.lastLocation.latitude}")
                    println("${locationResult.lastLocation.longitude}")



@Suppress("DEPRECATION")
fun getCityName(latitude: Double, longitude: Double, context: Context): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        addresses?.firstOrNull()?.adminArea ?: "Unknown Location"
    } catch (e: IOException) {
        e.printStackTrace()
        "Unknown Location"
    }
}

 */

