package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RallyAppTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            RallyApp()
        }
    }

    @Test
    fun rallyTopAppBarTest_selectBills() {
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Bills.name)
            .performClick()
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Bills.name)
            .assertIsSelected()
    }

    @Test
    fun rallyTopAppBarTest_selectAccounts() {
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .performClick()
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }
}