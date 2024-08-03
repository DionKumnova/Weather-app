package com.example.gruppe22_in2000.view.navigation

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gruppe22_in2000.R
import com.example.gruppe22_in2000.model.gps.getDeviceLocation
import com.example.gruppe22_in2000.view.SearchScreenBuilder
import com.example.gruppe22_in2000.view.MenuScreen
import com.example.gruppe22_in2000.view.HomeScreen
import com.example.gruppe22_in2000.viewmodel.WeatherViewModel

sealed class Screen (val title: String, val route: String, @DrawableRes val icon: Int = 0){
    object Home:Screen(
        title = "home",
        route = "home_route",
        icon = R.drawable.home,
    )
    object TTS:Screen(
        title = "tts",
        route = "tts_route",
        icon = R.drawable.sound_white
    )
    object Search:Screen(
        title = "search",
        route = "search_route",
        icon = R.drawable.search_white,
    )
    object Menu:Screen(
        title = "menu",
        route = "menu_route",
        icon = R.drawable.menu,
    )
    object MenuAppearance:Screen(
        title = "menu_appearance",
        route = "menu_appearance_route",
    )
    object MenuContact:Screen(
        title = "menu_contact",
        route = "menu_contact_route",
    )
    object MenuPrivacy:Screen(
        title = "menu_privacy",
        route = "menu_privacy_route",
    )

}


@Composable
fun BottomNavHost(navHostController: NavHostController, context: Context, viewModel: WeatherViewModel = WeatherViewModel(context)) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                context = context
            ) }
        composable(route = Screen.TTS.route){
            HomeScreen(
                viewModel = viewModel,
                context = context,
                speak = true)
        }
        composable(route = Screen.Search.route) {
            /*SearchBar(
                viewModel = viewModel,
                onConfirmLocation = {location ->
                    viewModel.searchWeather(location)},
                onNavigateBack = { navHostController.navigate(Screen.Home.route) },
                context = context
            )
             */
            SearchScreenBuilder(onNavigateBack = { navHostController.navigate(Screen.Home.route) },
                viewModel = viewModel,
                context = context)
        }
        composable(route = Screen.Menu.route) {
            MenuScreen(
                viewModel = viewModel,
                context = context
            )
        }
    }
}

@Composable
fun BottomNavigationScreen(
    navController: NavController,
    items: List<Screen>,
    onClickAction: (String, NavBackStackEntry?) -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        //backgroundColor = Color(0xFF09243e)
        backgroundColor = Color.White

    ) {
        items.forEach {
            BottomNavigationItem(
                selected = currentDestination?.route === it.route,
                onClick = {
                    onClickAction(it.route, navBackStackEntry)
                    navController.navigate(it.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = null)
                },
                //label = {Text(text = it.title)}
            )
        }
    }
}

fun bottombarAction(
    route: String, backStackEntry: NavBackStackEntry?,
    context: Context, viewModel: WeatherViewModel) {
    if (route == Screen.Home.route && backStackEntry?.destination?.route == Screen.Home.route){
        getDeviceLocation(context, viewModel)
    }
    else if (route == Screen.TTS.route) {
        viewModel.speak(context, "FIKS DENNE STRENGEN INN I VIEWMODEL eller noe sånt, det hadde vært veldig kult ")
    }

}