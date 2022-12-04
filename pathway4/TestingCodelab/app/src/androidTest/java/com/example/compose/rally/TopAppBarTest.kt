package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

/**
 * @Description
 * @author yc.park (DEEP.FINE)
 * @since 2022-11-30
 * @version 1.0.0
 */
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
}