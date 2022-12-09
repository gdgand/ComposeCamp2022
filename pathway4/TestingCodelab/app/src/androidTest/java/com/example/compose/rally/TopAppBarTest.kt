package com.example.compose.rally

import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest_currentTabSelected() {
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
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }
        println("----------- Unmerged")
        composeTestRule
            .onRoot(useUnmergedTree = true)
            .printToLog("currentLabelExists")

        println("----------- Merged")
        composeTestRule
            .onRoot(useUnmergedTree = false)
            .printToLog("currentLabelExists")

        /*
        |-Node #8 at (l=203.0, t=137.0, r=505.0, b=205.0)px
           | Role = 'Tab'
           | Focused = 'false'
           | Selected = 'true'
           | ContentDescription = '[Accounts]'
           | Actions = [OnClick, RequestFocus]
           | MergeDescendants = 'true'
           | ClearAndSetSemantics = 'true'
           |  |-Node #12 at (l=305.0, t=137.0, r=505.0, b=190.0)px
           |    Text = '[ACCOUNTS]'
           |    Actions = [GetTextLayoutResult]
         */
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
}
