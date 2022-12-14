package com.example.compose.rally

import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.overview.OverviewBody
import org.junit.Rule
import org.junit.Test

class OverviewScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun overviewScreen_alertsDisplayed(){
        composeTestRule.setContent {
            OverviewBody()
        }
    }
}