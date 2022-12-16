package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setupRallyTopAppBar() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }
    }

    @Test
    fun rallyTopAppBarTest() {
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsDisplayed()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                        hasParent(hasContentDescription(RallyScreen.Accounts.name)),
                useUnmergedTree = true
            )
            .assertExists()
    }

    @Test
    fun rallyTopAppBarTest_clickOverview_navigationToOverview() {
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Overview.name)
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Overview.name)
            .assertIsDisplayed()
    }

    @Test
    fun rallyTopAppBarTest_clickBills_navigationToBills() {
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Bills.name)
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Bills.name)
            .assertIsDisplayed()
    }
}