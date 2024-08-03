package com.example.gruppe22_in2000.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gruppe22_in2000.ui.theme.*

//PRIORITY:

//Monochromatism
//Dichromatism
//Anomalous Trichromacy:
// protanomaly, which is a reduced sensitivity to red light,
// deuteranomaly which is a reduced sensitivity to green light (the most common form of colour blindness),
//tritanomaly which is a reduced sensitivity to blue light (extremely rare).

//deuteranomaly:Navy blue (#000080)
//Mustard yellow (#FFDB58)
//Coral pink (#FF7F50)
//Beige (#F5F5DC)
//Lavender (#E6E6FA)


//Tekststørrelse

//beste farge paletter for darkmode og lightmode
//besteme fargepaletter for fargeblindhet
//Oppdatere UI according to chosen color pallet, and text size.
//1 Screenshot av skjerm(inni farge, eller rett under utseende)
//Knapp som endrer seg etter hva som er valgt(if light is choosen, button should say dark..etc..)

@Composable

///MODES
fun Fargeblindhet(onBackPressed: () -> Unit) {
    //oppdatere UI-STATE
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TopAppBar(
                title = { Text("Farge", fontSize = 35.sp, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                backgroundColor = Color(0xFF001217),
                modifier = Modifier
                    .height(54.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = (Color(0xFF001217)))
                ) {
                    Text(
                        text = "Fargeblindhet",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }

                Button(
                    onClick = {}, ///HERE???
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = (Color(0xFF001217)))
                ) {
                    Text(
                        text = "Mørkt tema", //or lyst temma depending on what is already choosen
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}




