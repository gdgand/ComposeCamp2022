package com.example.compose.rally

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
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
    fun rallyTopAppBarTest_otherTabSelected() {
        rallyTopAppBarTest_selectedTabBill()
        rallyTopAppBarTest_selectedTabAccount()
        rallyTopAppBarTest_selectedTabOverView()
    }

    @Test
    fun rallyTopAppBarTest_selectedTabBill() {
        composeTestRule.onNodeWithContentDescription(RallyScreen.Bills.name)
            .performClick()
        composeTestRule.onNodeWithContentDescription(RallyScreen.Bills.name)
            .assertIsSelected()
    }

    @Test
    fun rallyTopAppBarTest_selectedTabAccount() {
        composeTestRule.onNodeWithContentDescription(RallyScreen.Accounts.name)
            .performClick()
        composeTestRule.onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }

    @Test
    fun rallyTopAppBarTest_selectedTabOverView() {
        composeTestRule.onNodeWithContentDescription(RallyScreen.Overview.name)
            .performClick()
        composeTestRule.onNodeWithContentDescription(RallyScreen.Overview.name)
            .assertIsSelected()
    }
}