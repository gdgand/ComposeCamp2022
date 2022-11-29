package com.example.compose.rally

import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            //Text(text = "You can set any Compose content!")
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts
            )
        }

        Thread.sleep(5000)
    }

    @Test
    fun rallyTopAppBarTest_currentLbaleExist() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            //Text(text = "You can set any Compose content!")
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts
            )
        }
//        composeTestRule
//            .onNodeWithContentDescription(RallyScreen.Accounts.name)
//            .assertIsSelected()

        //composeTestRule.onRoot().printToLog("currentLabelExists")
//        composeTestRule.onNodeWithContentDescription(RallyScreen.Accounts.name)
//            .assertExists()

//        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")
        composeTestRule.onNode(
            hasText(RallyScreen.Accounts.name.uppercase()) and
                    hasParent(hasContentDescription(RallyScreen.Accounts.name)),
            useUnmergedTree = true
        )
            .assertExists()
    }
}