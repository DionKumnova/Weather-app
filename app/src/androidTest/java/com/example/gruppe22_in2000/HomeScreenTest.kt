package com.example.gruppe22_in2000

import android.app.Instrumentation
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.gruppe22_in2000.view.HomeScreen
import com.example.gruppe22_in2000.view.getDirectionOnlyArrow
import com.example.gruppe22_in2000.viewmodel.WeatherViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.Test.*
import org.junit.runner.RunWith
//import androidx.compose.ui.test.*
//import androidx.compose.ui.test.junit4.createComposeRule

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    // @get:Rule
    //val composeTestRule = createComposeRule()
    private val context: Context = ApplicationProvider.getApplicationContext()

    /* @Test
    fun testChangeOfLocationName() = runBlocking {
        //val context = LocalContext.current
        // Arrange
        val locationName = "Oslo"
        val viewModel = WeatherViewModel(context)

        // Act
        val locationDetails = viewModel.getCoordinatesForTest(locationName)



        // Assert
        locationDetails?.latitude?.let { assertEquals(59.911491, it, 0.001) }
        locationDetails?.longitude?.let { assertEquals(10.757933, it, 0.001) }
    }*/
    //PASSED
    @Test
    fun testGetDirectionOnlyArrow() {
        // Arrange
        val expectedDirections = mapOf(
            "0.0" to "↑",
            "45.0" to "↗",
            "90.0" to "→",
            "135.0" to "↘",
            "180.0" to "↓",
            "225.0" to "↙",
            "270.0" to "←",
            "315.0" to "↖"
        )

            // Act & Assert
            for ((angle, expectedDirection) in expectedDirections) {
                val result =
                    getDirectionOnlyArrow(angle) // Replace with the correct call to your function
                assertEquals(expectedDirection, result)
            }
        }

        //PASSED
        @Test
        fun testInitialState() {
            val viewModel = WeatherViewModel(context) // Replace with your ViewModel's constructor
            val state = viewModel.uiState.value // Get the current value of the state

            // Test that the initial state is not null
            assertNotNull(state)
        }

}