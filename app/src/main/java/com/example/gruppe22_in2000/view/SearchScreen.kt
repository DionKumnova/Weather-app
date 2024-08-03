package com.example.gruppe22_in2000.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import com.example.gruppe22_in2000.viewmodel.WeatherViewModel

/*
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar2(viewModel: WeatherViewModel, onConfirmLocation: (String) -> Unit) {
    val inputLocation = remember { mutableStateOf("") }
    val snackbarMessage = remember { mutableStateOf<String?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current

   // Spacer(modifier = Modifier.height(40.dp))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000).copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(16.dp)),
            color = Color.White,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Søk etter et sted",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = inputLocation.value,
                    onValueChange = { newValue ->
                        inputLocation.value = newValue
                    },
                    textStyle = TextStyle(fontSize = 24.sp),

                    placeholder = { Text("Tast inn", fontSize = 24.sp) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        onConfirmLocation(inputLocation.value)
                        viewModel.searchWeather(inputLocation.value)
                    })
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    // onClick = {
                    //    onConfirmLocation(inputLocation.value)
                    //     viewModel.searchWeather(inputLocation.value)
                    //  },
                    onClick = {
                        if (inputLocation.value.trim().isNotEmpty()) {
                            viewModel.searchWeather(inputLocation.value)
                            onConfirmLocation(inputLocation.value)
                            keyboardController?.hide()
                        } else {
                            snackbarMessage.value = "Invalid input. Please enter a location." // FUNGERER IKKKE
                        }
                        //viewModel.searchWeather(inputLocation.value)
                        // onConfirmLocation(inputLocation.value)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text("Trykk", fontSize = 30.sp)
                }
            }
        }
        snackbarMessage.value?.let { message ->
            InvalidInputSnackbar(message = message, onDismiss = { snackbarMessage.value = null })
        }
    }
}

 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar2(
    viewModel: WeatherViewModel,
    onConfirmLocation: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val inputLocation = remember { mutableStateOf("") }
    val snackbarMessage = remember { mutableStateOf<String?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF283747)) // Change the background color to a darker shade 0xFF283747
            .padding(top = 15.dp)
    ) {
        Text(
            text = "Søk",
            style = TextStyle(
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(15.dp))
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            TextField(
                value = inputLocation.value,
                onValueChange = { newValue ->
                    inputLocation.value = newValue
                },
                textStyle = TextStyle(fontSize = 25.sp),

                placeholder = {
                    Text(
                        "Skriv et sted",
                        fontSize = 35.sp,
                        color = Color.Gray // Morkere?
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.White, // Add a focus border color
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    onConfirmLocation(inputLocation.value)
                    //viewModel.searchWeather(inputLocation.value)
                    onNavigateBack()
                })
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (inputLocation.value.trim().isNotEmpty()) {
                    //viewModel.searchWeather(inputLocation.value)
                    onConfirmLocation(inputLocation.value)
                    keyboardController?.hide()
                    onNavigateBack()
                } else {
                    snackbarMessage.value =
                        "Invalid input. Please enter a location."
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            //colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF998EA7)) // Change button color for better contrast
        ) {
            Text("Søk", fontSize = 35.sp, color = Color.White)
        }
    }
}


/*
@Composable
fun InvalidInputSnackbar(message: String, onDismiss: () -> Unit) {
    Snackbar(
        modifier = Modifier
            //.align(Alignment.Center)
            .padding(16.dp),
        action = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss", color = Color.White)
            }
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(message, color = Color.White)
    }
}

 */


