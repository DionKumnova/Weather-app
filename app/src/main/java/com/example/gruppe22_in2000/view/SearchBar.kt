package com.example.gruppe22_in2000.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gruppe22_in2000.model.gps.SavedLocation
import com.example.gruppe22_in2000.viewmodel.WeatherViewModel


@Composable
fun SearchScreenBuilder(
    onNavigateBack: () -> Unit,
    viewModel: WeatherViewModel,
    context: Context
) {
    val uiState = viewModel.uiState.collectAsState()
    val savedLocations = viewModel.savedLocations.collectAsState().value
    val searchResults = viewModel.searchResults.collectAsState().value
    val snackbarHostState = remember {SnackbarHostState()}

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF283747))
    ) {
        item {
            Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                SearchBar(
                    viewModel = viewModel,
                    onConfirmLocation = {
                        if (viewModel.uiState.value.isLoaded) {
                            onNavigateBack()
                        }
                    }, context

                )
            }
        }



        item {
            Text(
                text = "Lagret lokasjoner",
                style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier.padding(start = 25.dp, top = 26.dp)
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        items(savedLocations.size) { index ->
            LocationCard(
                savedLocation = savedLocations[index],
                onDelete = { viewModel.removeSavedLocation(index, context) },
                onClick = {
                    viewModel.updatePos(
                        savedLocations[index].longitude,
                        savedLocations[index].latitude,
                        savedLocations[index].name
                    )
                    onNavigateBack()
                }
            )
        }
    }
}




@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    viewModel: WeatherViewModel,
    onConfirmLocation: (String) -> Unit,
    //onNavigateBack: () -> Unit,
    context: Context
) {
    val inputLocation = remember { mutableStateOf("") }
    val snackbarMessage = remember { mutableStateOf<String?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchResults = viewModel.searchResults.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            //  .background(Color(0xFF5A5A5A)) //
            .padding(top = 15.dp)
    ) {
        Text(
            text = "Søk",
            style = TextStyle(
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            TextField(
                value = inputLocation.value,
                onValueChange = { newValue ->
                    inputLocation.value = newValue.filter { it != '\n' && it != '\t' } // NEW LINE AND TAB PROHIBITED
                    viewModel.updateSearchResults(inputLocation.value)
                },
                textStyle = TextStyle(fontSize = 25.sp),
                placeholder = {
                    Text(
                        "Skriv et sted",
                        fontSize = 30.sp,
                        color = Color.Gray // Morkere?
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 50.dp) // Make room for the search icon
                    .padding(vertical = 4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if (inputLocation.value.trim().isNotEmpty()) {
                        viewModel.searchWeather(inputLocation.value)
                        viewModel.addSavedLocation(
                            SavedLocation(
                                inputLocation.value,
                                viewModel.uiState.value.lat,
                                viewModel.uiState.value.lon
                            ), context = context
                        )
                        onConfirmLocation(inputLocation.value)
                        keyboardController?.hide()
                    } else {
                        snackbarMessage.value =
                            "Invalid input. Please enter a location."
                    }
                })
            )

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color(0xFF216BB9), // Dark blue color for the search icon
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
                    .size(40.dp)
                    .clickable {
                        if (inputLocation.value
                                .trim()
                                .isNotEmpty()
                        ) {
                            viewModel.searchWeather(inputLocation.value)
                            viewModel.addSavedLocation(
                                SavedLocation(
                                    inputLocation.value,
                                    viewModel.uiState.value.lat,
                                    viewModel.uiState.value.lon
                                ), context
                            )
                            onConfirmLocation(inputLocation.value)
                            keyboardController?.hide()
                        } else {
                            snackbarMessage.value = "Invalid input. Please enter a location."
                        }
                    }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
       //AUTOCOMPLETE
        Column {
            searchResults.forEach { searchResult ->
                val cityName = searchResult.name.substringBefore(",").substringBefore("-") // Get only the city name
                Text(
                    text = searchResult.name, // Display full location name with city and country
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            inputLocation.value =
                                cityName // Update inputLocation to contain only the city name
                            viewModel.updateSearchResults("")
                            keyboardController?.hide() // Hide the keyboard when a location is clicked
                            viewModel.searchWeather(cityName)
                            onConfirmLocation(cityName) // Navigate to the home screen when a location is clicked
                            viewModel.addSavedLocation(
                                SavedLocation(
                                    cityName,
                                    viewModel.uiState.value.lat,
                                    viewModel.uiState.value.lon
                                ), context
                            )

                        }
                        .padding(8.dp),
                    fontSize = 25.sp,
                    color = Color.White, // Make the text appear white
                    fontWeight = FontWeight.SemiBold //  // Adju
                )
            }
        }
    }
}



@Composable
fun LocationCard(
    savedLocation: SavedLocation,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFDBE1EB) // Change the color of the cards
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = savedLocation.name,
                fontSize = 40.sp,
               // fontWeight = FontWeight.Bold
            )

            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = MaterialTheme.colors.error)
            }
        }
    }
}



@Composable
fun InvalidInputSnackbar(
    snackbarHostState: SnackbarHostState,
) {
    SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(16.dp)) { data ->
        Snackbar(
            action = {
                TextButton(onClick = { snackbarHostState.currentSnackbarData?.dismiss() }) {
                    Text("Dismiss", color = Color.White)
                }
            },
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(data.message, color = Color.White)
        }
    }
}

