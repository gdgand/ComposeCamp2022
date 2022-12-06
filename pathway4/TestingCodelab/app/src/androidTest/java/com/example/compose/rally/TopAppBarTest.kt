package com.example.compose.rally

import androidx.compose.material.Text
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun test() {
        val allScreens = RallyScreen.values().toList()
        composeRule.setContent {
            RallyTopAppBar(allScreens = allScreens, onTabSelected = { }, currentScreen = RallyScreen.Accounts)
        }

        composeRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }
}