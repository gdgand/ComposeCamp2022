package com.example.compose.rally

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    fun setupContent() {
        val allScreens = RallyScreen.values().toList()

        composeTestRule.setContent {
            var currentScreen by rememberSaveable { mutableStateOf(RallyScreen.Overview) }
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = {
                    currentScreen = it
                },
                currentScreen = currentScreen
            )
        }
    }

    @Test
    fun rallyTopAppBarTest() {
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")
        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                        hasParent(
                            hasContentDescription(RallyScreen.Accounts.name)
                        ),
                useUnmergedTree = true
            )
            .assertExists()
//        composeTestRule
//            .onNodeWithContentDescription(RallyScreen.Accounts.name)
//            .assertExists()
    }

    @Test
    fun clickedTabTest() {
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Overview.name)
            .performClick()
            .assertIsSelected()
            .assertIsSelected()
            .printToLog("currentLabelExists")
    }
}