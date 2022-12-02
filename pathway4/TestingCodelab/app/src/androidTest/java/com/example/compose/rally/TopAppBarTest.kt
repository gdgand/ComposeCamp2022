package com.example.compose.rally

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsSelectable
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.click
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import com.example.compose.rally.ui.components.RallyTopAppBar
import com.example.compose.rally.ui.theme.RallyTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun prepare(){
        composeTestRule.setContent {
            val allScreens = RallyScreen.values().toList()
            RallyTheme {
                RallyTopAppBar(
                    allScreens = allScreens,
                    onTabSelected = {},
                    currentScreen = RallyScreen.Accounts
                )
            }
        }
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TopAppBarTest")
    }

    @Test
    fun rallyTopAppBarTest_currentTabSelected(){
        prepare()
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists(){
        prepare()
        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                        hasParent(hasContentDescription(RallyScreen.Accounts.name)),
                useUnmergedTree = true
            )
            .assertExists()
    }

    @Test
    fun rallyTopAppBarTest_selectBillsTab(){
        composeTestRule.setContent {
            RallyTheme {
                RallyApp()
            }
        }

        composeTestRule
            .onNode(
                hasContentDescription(RallyScreen.Bills.name) and
                        isSelectable()
            )
            .performClick()
            .assertIsSelectable()
    }

    @Test
    fun rallyTopAppBarTest_selectAccountsTab(){
        composeTestRule.setContent {
            RallyTheme {
                RallyApp()
            }
        }

        composeTestRule
            .onNode(
                hasContentDescription(RallyScreen.Accounts.name) and
                        isSelectable()
            )
            .performClick()
            .assertIsSelectable()
    }
}