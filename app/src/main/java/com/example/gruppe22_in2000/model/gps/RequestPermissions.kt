package com.example.gruppe22_in2000

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat

//TODO Remove or make useful, currently not in use
@Composable
fun RequestPermission(
    context: Context,
    permissions: List<Manifest.permission>
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen", "PERMISSION GRANTED")
        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen", "PERMISSION DENIED")
        }
    }
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) -> {
            // Some works that require permission
            Log.d("ExampleScreen", "Code requires permission")
        }
        else -> {
            // Asking for permission
            launcher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }
}

