package com.example.compose.rally

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test
import java.util.*

class TopAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                        hasParent(
                            hasContentDescription(RallyScreen.Accounts.name)
                        ),
                useUnmergedTree = true
            )
            .assertExists()
    }

    @Test
    fun appbarChangeTest() {
        val allScreens = RallyScreen.values().toList()
        val currentScreen = mutableStateOf(RallyScreen.Overview)
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { screen -> currentScreen.value = screen },
                currentScreen = currentScreen.value
            )
        }

        composeTestRule.onNodeWithContentDescription("Accounts")
            .assertExists()
            .performClick()
        composeTestRule.onNode(
            hasText(currentScreen.value.name.uppercase()) and
                    hasParent(
                        hasContentDescription(currentScreen.value.name)
                    ),
            useUnmergedTree = true
        )
            .assertExists()
    }
}