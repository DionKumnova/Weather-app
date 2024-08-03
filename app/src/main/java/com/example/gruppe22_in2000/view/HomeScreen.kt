package com.example.gruppe22_in2000.view

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gruppe22_in2000.R
import com.example.gruppe22_in2000.model.LocationForecastDataClasses.Timeseries
import com.example.gruppe22_in2000.viewmodel.WeatherViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    viewModel: WeatherViewModel,
    context: Context,
    speak: Boolean = false
) {
    val speak = remember { mutableStateOf(speak) }
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState.collectAsState()
    val timeSeries = uiState.value.weatherData.properties?.timeseries
    val chosenTime =
        findClosestTimeseriesIndex(timeSeries) //TODO create function which finds closest timeseries to current
    val displayMode = remember { mutableStateOf("24h") }
    var currentTemp = " "
    var currentRain = " "
    var currentWind = " "
    var currentWindDir = " "

    //Get current time from API
    // #1: parser streng til localTimeObjekt
    //val currentLocalDateTime = LocalDateTime.parse(timeSeries?.get(chosenTime)?.time, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    // #2: gjoer objektet om til aa vise kun time og min
    //val currentTimeString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
    var currentTimeString by remember {
        mutableStateOf(" ")
    }
    LaunchedEffect(true) {
        while (true) {
            currentTimeString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            println(currentTimeString)
            delay(60_000L)

        }
    }
    //Current Image of temperature
    val currentImage = timeSeries?.get(chosenTime)?.data?.next1Hours?.summary?.symbolCode

    Box(
        modifier = Modifier
            //.verticalScroll(scrollState)
            .fillMaxSize() // 0xF32D2D47, 3e0924, 243e09, 0xFF09243e
            .background(Color(0xFF09243e)) // Blue: 0xFF2C3E50, Brown: 0xFF723937 Lavender: 0xFFA99FB4, 0xFFB6A7C2, 0xE1B9ABC4
    ) {
       // Spacer(modifier = Modifier.height(150.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Top,
    ) {
            //To reduce fontsize if location name too long for 1 line
            var textStyle by remember {
                mutableStateOf(
                    TextStyle(
                    color = Color(0xFFFFFFFF), // Change the color if needed
                    fontSize = 80.sp, // font size as needed
                    fontWeight = FontWeight.Bold)) }
            var readyToDraw by remember { mutableStateOf(false) }
            Text(
                text = uiState.value.location,
                maxLines = 1,
                softWrap = false,
                style = textStyle,
                modifier = Modifier.drawWithContent {
                    if (readyToDraw) drawContent()
                },
                onTextLayout = { textLayoutResult ->
                    if (textLayoutResult.didOverflowWidth) {
                        textStyle = textStyle.copy(fontSize = textStyle.fontSize * 0.92)
                    } else {
                    readyToDraw = true
                }
                }
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {




                val img: Painter
                val id = context.resources.getIdentifier(currentImage, "drawable", context.packageName)
                img = if (id == 0) {
                    painterResource(id = R.drawable.fog)
                } else {
                    painterResource(id = id)
                }

                    Text(
                        text = currentTimeString,
                        style = TextStyle(
                            color = Color(0xFFFFFFFF), // Change the color if needed
                            fontSize = 45.sp, // 50!!
                            fontWeight = FontWeight.Bold, // FJERNE DEN?
                        ),
                        modifier = Modifier.padding(13.dp),
                        softWrap = true
                    )
                if (currentImage != null) {
                   // Image(painter = img, contentDescription = "Weather Icon")
                CurrentVaerSymbol(
                    currentImage = currentImage, context = context, size = 170.dp,
                    alignment = Alignment.TopEnd, bottomPadding = 65.dp)


                }

            }

            //BILDE
            //Spacer(modifier = Modifier.height(55.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 230.dp)
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth() // fillMaxHeight()
                    .height(150.dp)
                    .padding(horizontal = 16.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(16.dp)),
                color =  Color(0xFFECF0F3),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                        .verticalScroll(scrollState),
                ) {
                    Spacer(modifier = Modifier.height(3.dp))

                   // Text(text = "Været nå", fontWeight = FontWeight.Bold, fontSize = 26.sp)

                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1.5f),
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {
                            var temp: String
                            if (timeSeries != null) {

                                println(timeSeries[chosenTime])
                                timeSeries[chosenTime].data?.instant?.details?.airTemperature?.let { x ->
                                    temp = x
                                    currentTemp = x
                                    Text(
                                        text = temp.toDouble().roundToInt().toString() + "°C",
                                        modifier = Modifier.padding(end = 20.dp),
                                        style = TextStyle(
                                            color = Color(0xFFAD4621), // Change the color if needed
                                            fontSize = 75.sp, // font size as needed
                                            fontStyle = FontStyle.Italic, // font weight)
                                            fontWeight = FontWeight.Bold, // Bold?
                                        )
                                    )
                                }
                            }
                        }

                        //Kode for nedbor
                        Column(
                            modifier = Modifier.weight(1.1f),
                        ) {

                            Spacer(modifier = Modifier.height(3.dp)) // spacer for a gjore elementene ved siden temp lenger ned

                            var mengde = "0.0"
                            var speed = "0"
                            var retning = "Ingen vind"
                            if (timeSeries != null) {
                                timeSeries[chosenTime].data?.next1Hours?.details?.precipitationAmount.let { regn ->
                                    if (regn != null) {
                                        mengde = regn
                                    }
                                    currentRain = mengde
                                    Text(
                                        text = ("$mengde mm"),
                                        style = TextStyle(
                                            color = Color(0xFF000000), // Change the color if needed
                                            fontSize = 38.sp, // font size as needed
                                            fontWeight = FontWeight.Bold
                                        )
                                    )

                                    //Code for vind hastighet
                                    Row( // row for a gjore retning og hastighet ved siden av
                                        modifier = Modifier.padding(horizontal = 0.dp) // Add some space between the Text elements

                                    ) {
                                        //Code for antall vindkast
                                        timeSeries[chosenTime].data?.instant?.details?.windSpeed.let { njoom ->

                                            //Converting wind speed to nearest integer
                                            if (njoom != null) {
                                                val njoomDouble = njoom.toDoubleOrNull()
                                                if (njoomDouble != null) {
                                                    speed = njoomDouble.roundToInt().toString()
                                                }
                                            }

                                            currentWind = speed

                                            Text(
                                                text = ("$speed m/s"),
                                                modifier = Modifier.align(Alignment.CenterVertically),
                                                style = TextStyle(
                                                    color = Color(0xFF000000), // Change the color if needed, blue: 0xFF005A5A
                                                    fontSize = 38.sp, // font size as needed
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        }

                                        // Add space between the Text elements
                                        Spacer(modifier = Modifier.width(8.dp))

                                        //Code for antall vindkast retning
                                        timeSeries[chosenTime].data?.instant?.details?.windFromDirection?.let { hvor ->

                                            println(hvor)
                                            retning = (getDirectionOnlyArrow(hvor))

                                            currentWindDir = getDirectionOnlyString(hvor)
                                            Text(
                                                text = retning,
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .offset(y = (-10).dp), // brukes for a gjore retnig lenger opp
                                                style = TextStyle(
                                                    color = Color(0xFF000000), // Change the color if needed
                                                    fontSize = 57.sp, // font size as needed
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        }
                                    }

                                    //TODO SJEKK OM DENNE TRENGS}

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (displayMode.value == "24h") {
        DisplayTemperatureOfNextHour(timeSeries, displayMode, context, viewModel)
    } else {
        DisplayTemperatureOfNext7Days(timeSeries, context, displayMode)
    }

}


fun getDirectionOnlyArrow(angle: String): String {
    val tall = angle.toDouble()
    val directions = listOf("↑", "↗", "→", "↘", "↓", "↙", "←", "↖")
    // nord, nord-øst, øst, sør-øst, sør, sør-vest, vest, nord-vest
    val index = ((tall / 45) + 0.5).toInt() % 8
    return directions[index]
}


//brukes for texttospeech
fun getDirectionOnlyString(angle: String): String {
    val tall = angle.toDouble()
    val directions =
        listOf("nord", "nord-øst", "øst", "sør-øst", "sør", "sør-vest", "vest", "nord-vest")
    val index = ((tall / 45) + 0.5).toInt() % 8
    return directions[index]
}


fun findClosestTimeseriesIndex(timeSeries: List<Timeseries>?): Int {
    val now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS)
    return timeSeries?.indexOfFirst { ts ->
        LocalDateTime.parse(ts.time, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .truncatedTo(ChronoUnit.HOURS) >= now
    } ?: -1
}


// 5-dagers TABELL
@Composable
fun DisplayTemperatureOfNextHour(
    timeSeries: List<Timeseries>?,
    displayMode: MutableState<String>,
    context: Context,
    viewModel: WeatherViewModel
) {
    // Get the temperature and time of the next hour
    val chosenTime = findClosestTimeseriesIndex(timeSeries)
    val next24Hours = 24 // denne kan nedres til 12 timer +-
    val uiState = viewModel.uiState.collectAsState()


    if (chosenTime != -1) { // error check
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 401.dp)
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(361.dp)
                    .padding(horizontal = 16.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(16.dp)),
                color = Color(0xFFECF0F3), // 0xFFD39191 0xFFEEF1F5
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    DisplayToggle(displayMode)

                    Spacer(modifier = Modifier.height(2.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(next24Hours) { hourIndex ->

                            //kode for tid i timer
                            val nextHourTime = LocalDateTime.now()
                                .plusHours(hourIndex.toLong())
                                .truncatedTo(ChronoUnit.HOURS) // only hours

                            val nextHourTimeString =
                                nextHourTime.format(DateTimeFormatter.ofPattern("HH:mm")) // formatting

                            val nextHourTemperature = uiState.value
                                ?.dataByTime?.get("$nextHourTime:00Z")
                                ?.instant?.details?.airTemperature
                                ?.let { x -> "${(x.toDouble()).roundToInt()} °C" } ?: "N/A"


                            //kode for image i forhold til tid
                            val nextHourImage = timeSeries?.get(chosenTime + hourIndex)
                                ?.data?.next1Hours?.summary?.symbolCode


                            //display av tid, temp og image i forhold til tiden
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp), //8.dp
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = nextHourTimeString,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 46.sp, // 42
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                if (nextHourImage != null) {
                                    Box(
                                        modifier = Modifier.weight(0.3f) // Adjust the weight to get the desired width
                                    ) {
                                        CurrentVaerSymbol(
                                            nextHourImage,
                                            context,
                                            80.dp,
                                            //Alignment.CenterHorizontally
                                            Alignment.Center
                                        )
                                    }
                                    Text(
                                        text = nextHourTemperature,
                                        modifier = Modifier.padding(1.dp),
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 46.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    } else {
        // Display an error message if there's an issue with the data
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 420.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Error: Unable to load weather data.")
        }
    }
}

//DisplayTemp7days
@Composable
fun DisplayTemperatureOfNext7Days(
    timeSeries: List<Timeseries>?,
    context: Context,
    //currentImage: String?,
    displayMode: MutableState<String>
) {
    val chosenTime = findClosestTimeseriesIndex(timeSeries)
    val next7Days = 7

    if (chosenTime != -1) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 401.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(361.dp)
                    .padding(horizontal = 16.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(16.dp)),
                color = Color(0xFFECF0F3),
            ) {

                Column(modifier = Modifier.fillMaxSize()) {
                    DisplayToggle(displayMode)

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(next7Days) { dayIndex ->
                            val nextDayTime = LocalDateTime.now()
                                .plusDays(dayIndex.toLong())
                                .truncatedTo(ChronoUnit.DAYS)

                            val today = LocalDate.now()
                            val nextDayTimeString = if (nextDayTime.toLocalDate() == today) {
                                "I dag"
                            } else {
                                nextDayTime.format(
                                    DateTimeFormatter.ofPattern(
                                        "EEE",
                                        Locale("no")
                                    )
                                ).capitalize(Locale("no"))
                            }
                            /*        val today = LocalDate.now()
                            val nextDayTimeString = if (nextDayTime.toLocalDate() == today) {
                                "I dag"
                            } else {
                                nextDayTime.format(
                                    DateTimeFormatter.ofPattern(
                                        "EEEE",
                                        Locale("no")
                                    )
                                ).capitalize(Locale("no"))
                            }*/

                            // Get the most common daytime weather symbol between 6-18
                            val daytimeStart = nextDayTime.withHour(6)
                            val daytimeEnd = nextDayTime.withHour(18)
                            val daytimeWeatherSymbols = mutableListOf<String>()

                            for (timeSeriesItem in timeSeries ?: emptyList()) {
                                val itemDateTime = LocalDateTime.parse(
                                    timeSeriesItem.time,
                                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
                                )
                                if (itemDateTime.toLocalDate() == nextDayTime.toLocalDate() &&
                                    !itemDateTime.isBefore(daytimeStart) &&
                                    !itemDateTime.isAfter(daytimeEnd)
                                ) {
                                    timeSeriesItem.data?.next6Hours?.summary?.symbolCode?.let {
                                        daytimeWeatherSymbols.add(it)
                                    }
                                }
                            }

                            val nextDayImage = daytimeWeatherSymbols
                                .groupingBy { it }
                                .eachCount()
                                .maxByOrNull { it.value }
                                ?.key

                            // Calculate the lowest and highest temperatures for each day
                            val dayTemperatures = mutableListOf<Double>()
                            for (timeSeriesItem in timeSeries ?: emptyList()) {
                                val itemDateTime = LocalDateTime.parse(
                                    timeSeriesItem.time,
                                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
                                )
                                if (itemDateTime.toLocalDate() == nextDayTime.toLocalDate()) {
                                    timeSeriesItem.data?.instant?.details?.airTemperature?.let {
                                        dayTemperatures.add(it.toDouble()) // Convert airTemperature to Double before adding
                                    }
                                }
                            }

                            val minTemperature =
                                dayTemperatures.minOrNull()?.roundToInt()?.toString()
                            val maxTemperature =
                                dayTemperatures.maxOrNull()?.roundToInt()?.toString()
                            val temperatureRange =
                                if (minTemperature != null && maxTemperature != null) {
                                    "$minTemperature | $maxTemperature°C"
                                } else {
                                    "N/A"
                                }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = nextDayTimeString,
                                    modifier = Modifier.weight(0.4f), // Adjust the weight to get the desired width
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 36.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )

                                if (nextDayImage != null) {
                                    Box(
                                        modifier = Modifier.weight(0.3f) // Adjust the weight to get the desired width
                                    ) {
                                        CurrentVaerSymbol(
                                            nextDayImage,
                                            context,
                                            62.dp,
                                            Alignment.Center,
                                            //Alignment.CenterHorizontally
                                        )
                                    }
                                }

                                Text(
                                    text = temperatureRange,
                                    modifier = Modifier
                                        .weight(0.6f) // brukes for en rett vertical fremvisning av symboler
                                        .padding(5.dp),
                                    textAlign = TextAlign.End,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 36.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 450.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Error: Unable to load weather data.")
        }
    }
}

/*
@Composable
fun DisplayToggle2(displayMode: MutableState<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Text(
            text = "24 t",
            style = TextStyle(
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        )

        CustomSwitch(
            checked = displayMode.value == "7d",
            onCheckedChange = { isChecked ->
                displayMode.value = if (isChecked) "7d" else "24h"
            }
        )

        Text(
            text = "7 d",
            style = TextStyle(
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        )

    }
    Row(
        modifier = Modifier
            .fillMaxWidth() // Fill the entire available width
            .height(1.dp), // Height of the divider
        horizontalArrangement = Arrangement.Center // Horizontally center the contents
    ) {
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.width(310.dp) // Set the width of the divider
        )
    }
}

@Composable
fun CustomSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val width = 85.dp // Set the desired width for the switch
    val height = 34.dp // Set the desired height for the switch

    Box(
        modifier = Modifier
            .size(width, height)
            .clip(RoundedCornerShape(15.dp))
            .background(if (checked) Color(0xFF2C3E50) else Color(0xFF2C3E50))
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(height - 4.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .align(if (checked) Alignment.CenterEnd else Alignment.CenterStart)
                .padding(20.dp)
        )
    }
}
*/

@Composable
fun DisplayToggle(displayMode: MutableState<String>) { // knapp for 24 time rog 7 timer
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "24 timer",
            modifier = Modifier
                .padding(6.dp)
                .clickable(onClick = { displayMode.value = "24h" })
                .background(
                    color = if (displayMode.value == "24h") Color(0xFF2C3E50) else Color.Gray,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 13.dp, vertical = 4.dp),
            style = TextStyle(
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = "7 dager",
            modifier = Modifier
                .padding(6.dp)
                .clickable(onClick = { displayMode.value = "7d" })
                .background(
                    color = if (displayMode.value == "7d") Color(0xFF2C3E50) else Color.Gray,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 13.dp, vertical = 4.dp),
            style = TextStyle(
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )
        )

    }
    Row(
        modifier = Modifier
            .fillMaxWidth() // Fill the entire available width
            .height(1.dp), // Height of the divider
        horizontalArrangement = Arrangement.Center // Horizontally center the contents
    ) {
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.width(310.dp) // Set the width of the divider
        )
    }
}



// 5-dagers TABELL
@Composable
fun CurrentVaerSymbol(
    currentImage: String,
    context: Context,
    size: Dp, alignment:
    Alignment,
    bottomPadding: Dp = 0.dp
) {
/*fun currentVaerSymbol2(
    currentImage: String,
    context: Context,
    size: Dp, alignment:
    Alignment.Horizontal){

 */

    val img: Painter
    val id = context.resources.getIdentifier(currentImage, "drawable", context.packageName)
    img = if (id == 0) {
        painterResource(id = R.drawable.fog)
    } else {
        painterResource(id = id)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        // horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

        ) {
        Image(
            painter = img,
            contentDescription = "Sun Image", // Provide a description for accessibility purposes
            modifier = Modifier
                .size(size) // 210 VANLIG, 240 FOR CLOUDY //TODO sizing
                //.align(alignment)
                .padding(bottom = bottomPadding),
            alignment = alignment
                // END
            // .padding(top = 10.dp, end = 15.dp) // Adjust the padding as needed
        )
    }
}

fun hvaSkalSies(loc: String, temp: String, regn: String, vind: String, retning: String): String {
    var setning = "Det er $temp grader i $loc"

    setning += if (regn.toDouble() != 0.0) {
        " og det regner $regn millimeter timen"
    } else {
        " og det regner ikke"
    }

    if (vind.toDouble() != 0.0) {
        setning += " , vinden kommer fra $retning med en hastighet på $vind meter i sekundet"
    }

    println(setning)
    return setning
}

