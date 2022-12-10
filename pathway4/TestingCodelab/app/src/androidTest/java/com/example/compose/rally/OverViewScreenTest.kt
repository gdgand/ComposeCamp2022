package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.compose.rally.ui.overview.OverviewBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OverViewScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            OverviewBody()
        }
    }

    @Test
    fun overviewScreen_alertDisplayed() {
        composeTestRule
            .onNodeWithText("Alerts")
            .assertIsDisplayed()
    }
}