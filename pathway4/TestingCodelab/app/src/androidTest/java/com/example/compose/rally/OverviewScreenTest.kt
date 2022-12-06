package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.compose.rally.ui.overview.OverviewBody
import org.junit.Rule
import org.junit.Test

class OverviewScreenTest {

    @get:Rule
    val composableRule = createComposeRule()

    @Test
    fun overviewScreen_alertDisplayed() {
        composableRule.setContent {
            OverviewBody()
        }

        composableRule
            .onNodeWithText("Alerts")
            .assertIsDisplayed()
    }
}