package com.example.compose.rally

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts
            )
        }
    }

    @Test
    fun rallyTopAppBarTest() {
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertExists()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists_useUnMergedTree() {
        composeTestRule
            .onNodeWithText(
                text = RallyScreen.Accounts.name.uppercase(),
                useUnmergedTree = true
            )
            .assertExists()
    }
}