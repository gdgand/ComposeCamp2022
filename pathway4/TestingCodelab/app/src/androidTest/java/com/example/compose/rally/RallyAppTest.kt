package com.example.compose.rally

import androidx.compose.material.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.compose.rally.ui.overview.OverviewBody
import com.example.compose.rally.ui.theme.RallyTheme
import org.junit.Rule
import org.junit.Test

class RallyAppTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        composeTestRule.setContent {
            RallyTheme {
                OverviewBody()
            }

        }
        composeTestRule
            .onAllNodes(hasText("SEE ALL"))[1].performClick()

        composeTestRule
            .onNodeWithText("Checking", useUnmergedTree = true).assertIsDisplayed()
    }
}