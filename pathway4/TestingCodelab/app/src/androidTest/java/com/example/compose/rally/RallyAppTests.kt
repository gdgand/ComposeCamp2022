package com.example.compose.rally

import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class RallyAppTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyApp_selected_other_tab() {
        composeTestRule.setContent {
            RallyApp()
        }

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Overview.name)
            .performClick()
            .assertIsSelected()

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Bills.name)
            .assertIsNotSelected()

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsNotSelected()
    }
}