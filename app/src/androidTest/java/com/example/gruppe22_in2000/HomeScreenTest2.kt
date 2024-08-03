package com.example.gruppe22_in2000

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gruppe22_in2000.viewmodel.WeatherViewModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest2 {

    // @get:Rule
    //val composeTestRule = createComposeRule()
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun testChangeOfLocationName(): Unit = runBlocking {
        //val context = LocalContext.current
        // Arrange
        val locationName = "Oslo"
        val viewModel = WeatherViewModel(context)

        // Act
        val locationDetails = viewModel.getCoordinatesForTest(locationName)



        // Assert
        locationDetails?.latitude?.let { TestCase.assertEquals(59.911491, it, 0.001) }
        locationDetails?.longitude?.let { TestCase.assertEquals(10.757933, it, 0.001) }
    }
}