package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.compose.rally.ui.overview.OverviewBody
import org.junit.Rule
import org.junit.Test

class OverviewScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun overviewScreenAlertsDisplayed() {
        composeRule.setContent {
            OverviewBody()
        }

        composeRule.onRoot(useUnmergedTree = false).printToLog("currentLabelExists")

        composeRule.onNodeWithText("Alerts")
            .assertIsDisplayed()
    }
}