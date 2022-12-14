package com.example.compose.rally

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
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
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }

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
    fun rallyTopAppBarTest_selectTest() {
        val allScreens = RallyScreen.values().toList()
        val start = RallyScreen.Overview
        val current = mutableStateOf(start)
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { selected -> current.value = selected },
                currentScreen = current.value
            )
        }

        // before start = RallyScreen.Overview
        composeTestRule.onNodeWithContentDescription(start.name)
            .assertIsSelected()
        composeTestRule.onNode(
            hasText(start.name.uppercase()) and
                hasParent(hasContentDescription(start.name)),
            useUnmergedTree = true
        ).assertExists()

        // when : [Accounts] Click
        val selected = RallyScreen.Accounts
        composeTestRule.onNodeWithContentDescription(selected.name)
            .performClick()

        // then : [Accounts] assertIsSelected and assertExists
        composeTestRule.onNodeWithContentDescription(selected.name)
            .assertIsSelected()
        composeTestRule.onNode(
            hasText(selected.name.uppercase()) and
                hasParent(hasContentDescription(selected.name)),
            useUnmergedTree = true
        ).assertExists()
    }
}
