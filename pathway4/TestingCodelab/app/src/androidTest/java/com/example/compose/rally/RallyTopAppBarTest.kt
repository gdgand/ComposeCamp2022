package com.example.compose.rally

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import com.example.compose.rally.ui.theme.RallyTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RallyTopAppBarTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            var currentScreen by remember { mutableStateOf(RallyScreen.Accounts) }
            RallyTheme {
                RallyTopAppBar(
                    allScreens = allScreens,
                    onTabSelected = { currentScreen = it },
                    currentScreen = currentScreen
                )
            }
        }
    }

    @Test
    fun RallyTopAppBarTest_currentLabelExists() {
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                        hasParent(
                            hasContentDescription(RallyScreen.Accounts.name)
                        ),
                useUnmergedTree = true
            ).assertExists()
    }

    @Test
    fun RallyTopAppBarTest_clickDifferentTabs_showTheirLabels() {
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Bills.name)
            .performClick()
        composeTestRule
            .onNode(
                hasText(RallyScreen.Bills.name.uppercase()) and
                        hasParent(
                            hasContentDescription(RallyScreen.Bills.name)
                        ),
                useUnmergedTree = true
            )
            .assertIsDisplayed()
    }
}