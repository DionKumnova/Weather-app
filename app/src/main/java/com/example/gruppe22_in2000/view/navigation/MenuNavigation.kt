package com.example.gruppe22_in2000.view.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

class MenuNavigation {

    @Composable
    fun MenuNavigationScreen(
        navController: NavController,
        items: List<Screen>,
        onClickAction: (String, NavBackStackEntry?) -> Unit
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
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
                            contentDescription = null
                        )
                    },
                    label = { Text(text = it.title) }
                )
            }
        }
    }
}