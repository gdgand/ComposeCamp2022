package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
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
        onTabSelected = {},
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
        onTabSelected = {},
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
  fun rallyTopAppBarTest_changeTab() {
    fun clickTab(tabName: String) {
      composeTestRule
        .onNodeWithContentDescription(tabName)
        .performClick()
    }

    val allScreens = RallyScreen.values().toList()
    composeTestRule.setContent {
      RallyTopAppBar(
        allScreens = allScreens,
        onTabSelected = {},
        currentScreen = RallyScreen.Accounts
      )
    }

    clickTab(RallyScreen.Overview.name)
    composeTestRule
      .onNodeWithContentDescription("Overview")
      .assertIsDisplayed()

    clickTab(RallyScreen.Accounts.name)
    composeTestRule
      .onNodeWithContentDescription("Accounts")
      .assertIsDisplayed()

    clickTab(RallyScreen.Bills.name)
    composeTestRule
      .onNodeWithContentDescription("Bills")
      .assertIsDisplayed()
  }
}