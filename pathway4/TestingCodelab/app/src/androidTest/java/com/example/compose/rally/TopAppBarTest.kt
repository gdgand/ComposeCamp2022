package com.example.compose.rally

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.SemanticsPropertiesAndroid
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import com.example.compose.rally.ui.theme.RallyTheme
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest_currentTabSelected() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTheme {
                RallyTopAppBar(
                    allScreens = allScreens,
                    onTabSelected = { },
                    currentScreen = RallyScreen.Accounts
                )
            }
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
    fun rallyTopAppBarTest_clickDifferentTabsAndChangeSelection() {
        composeTestRule.setContent {
            RallyTheme {
                RallyApp()
            }
        }

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .performClick()
            .assertIsSelected()


        val notAccountTabsMatcher = hasContentDescription(RallyScreen.Overview.name)
            .or(hasContentDescription(RallyScreen.Bills.name))

        composeTestRule
            .onAllNodes(notAccountTabsMatcher)
            .assertAll(isNotSelected())


        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Bills.name)
            .performClick()
            .assertIsSelected()


        val notBillsTabsMatcher = hasContentDescription(RallyScreen.Overview.name)
            .or(hasContentDescription(RallyScreen.Accounts.name))

        composeTestRule
            .onAllNodes(notBillsTabsMatcher)
            .assertAll(isNotSelected())

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Overview.name)
            .performClick()
            .assertIsSelected()


        val notOverviewTabsMatcher = hasContentDescription(RallyScreen.Bills.name)
            .or(hasContentDescription(RallyScreen.Accounts.name))

        composeTestRule
            .onAllNodes(notOverviewTabsMatcher)
            .assertAll(isNotSelected())


    }
}