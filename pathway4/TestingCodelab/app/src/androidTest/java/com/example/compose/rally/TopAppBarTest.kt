package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts
            )
        }
        Thread.sleep(5000)
    }

    @Test
    fun rallyTopAppBarTest_currentTabSelected() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts
            )
        }
        composeTestRule.onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsDisplayed()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

        composeTestRule.onNode(
            hasText(RallyScreen.Accounts.name.uppercase()) and
                    hasParent(
                        hasContentDescription(RallyScreen.Accounts.name)
                    ),
            useUnmergedTree = true
        )
            .assertExists()

    }


}