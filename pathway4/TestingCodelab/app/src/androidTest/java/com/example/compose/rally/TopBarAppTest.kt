package com.example.compose.rally

import androidx.compose.material.Text
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.toUpperCase
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopBarAppTest {

    @get: Rule
    val getComposeTestRule = createComposeRule()

//    @get: Rule
//    val composeTestRule = createAndroidComposeRule(RallyActivity::class.java)

    @Test
    fun myTest() {
        getComposeTestRule.setContent {
            Text(text = "You can set any Compose content!")
        }
    }

    @Test
    fun rallyTopAppBarTest() {
        getComposeTestRule.setContent {
            val allScreen = RallyScreen.values().toList()
            RallyTopAppBar(allScreens = allScreen, onTabSelected = {}, currentScreen = RallyScreen.Accounts)
        }

        Thread.sleep(5000)
    }

    @Test
    fun rallyTopAppBarTest_currentTabSelected() {
        val allScreens = RallyScreen.values().toList()
        getComposeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        getComposeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreens = RallyScreen.values().toList()
        getComposeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

//        getComposeTestRule
//            .onNodeWithText(RallyScreen.Accounts.name.toUpperCase())
//            .assertExists()

//        getComposeTestRule.onRoot(true).printToLog("currentLabelExists")
        getComposeTestRule.onNode(
            hasText(RallyScreen.Accounts.name.toUpperCase()) and
                    hasParent(
                        hasContentDescription(RallyScreen.Accounts.name)
                    ),
            useUnmergedTree = true
        )
            .assertExists()
    }

}