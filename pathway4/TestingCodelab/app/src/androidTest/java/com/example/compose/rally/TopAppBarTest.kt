package com.example.compose.rally

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import com.example.compose.rally.ui.overview.OverviewBody
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        composeTestRule.setContent {
            Text("You can set any Compose content!")
        }
    }

    @Test
    fun rallyTopAppBarTest() {
        composeTestRule.setContent {
            val allScreens = RallyScreen.values().toList()
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }
        //Thread.sleep(5000)
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
                hasText(RallyScreen.Accounts.name.uppercase()) and
                        hasParent(
                            hasContentDescription(RallyScreen.Accounts.name)
                        ),
                useUnmergedTree = true
            )
            .assertExists()

//        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

//        composeTestRule
//            .onNodeWithContentDescription(RallyScreen.Accounts.name)
//            .assertExists()
    }



}

class OverviewScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun overviewScreen_alertsDisplayed() {
        composeTestRule.setContent {
            OverviewBody()
        }

        composeTestRule
            .onNodeWithText("Alerts")
            .assertIsDisplayed()
    }
}

class PerformClickTabsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun clickOverviewTabClickedTest() {
        composeTestRule.setContent {
            RallyApp()
        }

        val allScreens = RallyScreen.values().toList()
        var tabNames = listOf("overview", "accounts", "bills")

        for (i in 0..2) {
            composeTestRule.onNodeWithContentDescription(tabNames[i], ignoreCase = true).performClick()
            composeTestRule
                .onNodeWithContentDescription(allScreens[i].name)
                .assertIsSelected()
        }
    }
}