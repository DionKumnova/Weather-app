package com.example.gruppe22_in2000.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import com.example.gruppe22_in2000.viewmodel.WeatherViewModel


@Composable
fun MenuScreen(
    //onNavigateToSettings: () -> Unit,
    //onNavigateReturn: () -> Unit,
    //onNavigateToText: () -> Unit,
    //onNavigateToSearch: () -> Unit,
    viewModel: WeatherViewModel,
    context: Context,
) {
    val scrollState = rememberScrollState()
    Surface(
        color = (Color(0xFFFFFFFF)),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                //.padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            TopAppBar(
                title = { Text("Innstillinger", fontSize = 24.sp, color = Color.White) },
                backgroundColor = Color(0xFF001217),
                elevation = 0.dp,
                modifier = Modifier
                    .height(56.dp)
                //.align(Alignment.Center)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()

            ){


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Utseende",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = (Color(0xFF001217))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    //onClick = onNavigateToSettings,
                    onClick = {/* TODO */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(backgroundColor = (Color(0xFF001217)))

                    //colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)

                ) {
                    Text(
                        text = "Farge",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    //onClick = onNavigateToText,
                    onClick = {/* TODO */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(backgroundColor = (Color(0xFF001217)))

                ) {
                    Text(
                        text = "Tekststørrelse",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }

                //Fet tekst
                //Tekst typer

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Kontakt",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = (Color(0xFF001217))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(backgroundColor = (Color(0xFF001217)))


                ) {
                    Text(
                        text = "Send inn tips",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(backgroundColor = (Color(0xFF001217)))

                ) {
                    Text(
                        text = "Kontakt oss",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Personvern",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = (Color(0xFF001217))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(backgroundColor = (Color(0xFF001217)))

                ) {
                    Text(
                        text = "Innstillinger for personvern",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = (Color(0xFF001217)))

                    //ADD COLOR
                ) {
                    Text(
                        text = "Personvernerklæring",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }


            }

            //Could not be added in the BottomBar menu as this only applies here
            //align does not work here, so we have to work around it.
            Spacer(modifier = Modifier.weight(1f))

            /*
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color(0xFFFFFFFF))
            ) {
                // Add content to the bottom bar here
                androidx.compose.material3.Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = Color.Black
                )
                //BottomBar(onNavigateToBurger = {}, onNavigateReturn = onNavigateReturn, onNavigateSearch = onNavigateToSearch, viewModel, context)
            }

             */



        }


    }

}







@Composable
//Opdatere UI STATE
fun Tekst(onBackPressed: () -> Unit) {
    var textSize by remember { mutableStateOf(18f) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TopAppBar(
                title = { Text("Tekst", fontSize = 35.sp, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                backgroundColor = Color(0xFF001217),
                modifier = Modifier
                    .height(50.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Apper som støtter Dynamisk skrift, justerer seg etter den foretrukne skriftstørrelsen din",
                fontSize = textSize.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.End)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "A", fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterVertically))
                    Slider(

                        value = textSize,
                        onValueChange = { newSize -> textSize = newSize },
                        valueRange = 12f..60f,
                        steps = 12,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        colors = SliderDefaults.colors(
                            thumbColor = (Color(0xFF001217)),
                            activeTrackColor = (Color(0xFF001217)),
                            inactiveTrackColor = (Color(0xFF001217))
                        )



                    )
                    Text(text = "A", fontSize = 60.sp, modifier = Modifier.align(Alignment.CenterVertically))
                }
            }

            Spacer(modifier = Modifier.weight(1f))


        }
    }
}




@Composable
fun Dark(onBackPressed: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("Mørkt tema", fontSize = 35.sp, color = Color.White) },
                navigationIcon = {
                    androidx.compose.material.IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                modifier = Modifier
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

    }

}


