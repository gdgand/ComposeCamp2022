package com.example.compose.rally

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreen = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreen,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule.onRoot().printToLog("currentLabelExists")

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertExists()
    }
}
