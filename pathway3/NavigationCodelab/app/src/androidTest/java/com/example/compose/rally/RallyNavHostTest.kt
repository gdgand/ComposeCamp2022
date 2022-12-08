package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RallyNavHostTest {

    @get: Rule
    val composeTestRule = createComposeRule()
    lateinit var navHostController: NavHostController

    @Before
    fun setRallyNavHost() {
        composeTestRule.setContent {
            navHostController = rememberNavController()
            RallyNavController(navController = navHostController)
        }
    }

    @Test
    fun rallyNavHost() {
        composeTestRule.onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }
}