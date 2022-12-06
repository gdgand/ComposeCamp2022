package com.example.compose.rally

import androidx.compose.material.Text
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setupRallyCompose() {
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
    fun myTest() {
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        composeTestRule
            .onNode(
                matcher = hasText(
                    RallyScreen.Accounts.name.uppercase()
                ) and hasParent(
                    hasContentDescription(RallyScreen.Accounts.name)
                ),
                useUnmergedTree = true
            )
            .assertExists()
    }

    @Test
    fun rallyTopApPBarTest_click() {
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Overview.name)
            .performClick()
        Thread.sleep(5000)
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Overview.name)
            .assertIsSelected()
    }
}