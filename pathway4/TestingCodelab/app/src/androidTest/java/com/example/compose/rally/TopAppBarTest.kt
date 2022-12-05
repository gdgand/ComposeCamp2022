package com.example.compose.rally

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
        Thread.sleep(5000)
    }

    @Test
    fun rallyTopAppBarTest_currentTabSelected() {
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
                hasText(RallyScreen.Accounts.name.uppercase()) and hasParent(
                    hasContentDescription(RallyScreen.Accounts.name)
                ),
                useUnmergedTree = true
            ).assertExists()
    }

    @Test
    fun rallyTopAppBarTest_clickTabLabel() {
        val allScreens = RallyScreen.values().toList()
        var currentScreen by mutableStateOf(RallyScreen.Overview)

        var assertionName = ""

        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = {
                    currentScreen = it
                    Log.e("clickTabLabel", "${currentScreen.name}, $assertionName")
                    assert(currentScreen.name == assertionName)
                },
                currentScreen = currentScreen)
        }

        assertionName = RallyScreen.Overview.name
        composeTestRule.onNodeWithContentDescription(RallyScreen.Overview.name).performClick()

        assertionName = RallyScreen.Accounts.name
        composeTestRule.onNodeWithContentDescription(RallyScreen.Accounts.name).performClick()

        assertionName = RallyScreen.Bills.name
        composeTestRule.onNodeWithContentDescription(RallyScreen.Bills.name).performClick()

    }
}