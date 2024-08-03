package com.example.gruppe22_in2000

import android.annotation.SuppressLint
import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.gruppe22_in2000.ui.theme.Gruppe22_in2000Theme
import com.example.gruppe22_in2000.view.*
import com.example.gruppe22_in2000.view.navigation.BottomNavHost
import com.example.gruppe22_in2000.view.navigation.BottomNavigationScreen
import com.example.gruppe22_in2000.view.navigation.Screen
import com.example.gruppe22_in2000.view.navigation.bottombarAction
import com.example.gruppe22_in2000.viewmodel.WeatherViewModel
import com.google.android.gms.location.*

@SuppressLint("MissingPermission")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm = WeatherViewModel(this)

        setContent {
            val listItems = listOf(
                Screen.Home,
                Screen.TTS,
                Screen.Search,
                Screen.Menu
            )
            val navController = rememberNavController()
            Gruppe22_in2000Theme {
                val uiState = vm.uiState.collectAsState()
                if (uiState.value.isLoaded) {
                    Surface(
                        Modifier.fillMaxSize()
                    ) {
                        Scaffold(bottomBar = {
                            BottomNavigationScreen(
                                navController = navController,
                                items = listItems,
                                onClickAction = { route, backstack ->
                                    bottombarAction(route, backstack, this, vm)
                                }
                            )
                        }) {
                            val mainContext = this
                            Box(modifier = Modifier.padding(it)) {
                                BottomNavHost(
                                    navHostController = navController,
                                    context = mainContext,
                                    viewModel = vm
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
/*
// NAVIGATION
@Composable
fun NextScreen(vm: WeatherViewModel, context: Context) {
    val navController = rememberNavController()
    val uiState = vm.uiState.collectAsState() // Access the locationName

    // Use derivedStateOf to create a derived state that depends on the location
    val locationState = derivedStateOf { uiState.value.location }

    NavHost(navController = navController, startDestination = "homescreen") {
        composable("homescreen") {
            HomeScreen(
                vm,
                onNavigateToNext = { navController.navigate("search") },
                locationName = locationState.value, // Pass locationState to HomeScreen,
                context = context
            )
        }
        composable("search") { EmptyWhiteScreen2(
            onNavigateBack = { navController.popBackStack() }, vm) }
    }
}

@Composable
fun EmptyWhiteScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}
*/