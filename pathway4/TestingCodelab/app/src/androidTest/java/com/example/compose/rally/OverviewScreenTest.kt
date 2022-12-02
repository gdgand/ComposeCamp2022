package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.compose.rally.ui.overview.OverviewBody
import com.example.compose.rally.ui.theme.RallyTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OverviewScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun prepare(){
        composeTestRule.setContent {
            RallyTheme{
                OverviewBody()
            }
        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("OverviewScreenTest")
    }

    @Test
    fun overviewScreen_alertsDisplayed(){
        composeTestRule
            .onNodeWithText("Alerts")
            .assertIsDisplayed()
    }
}