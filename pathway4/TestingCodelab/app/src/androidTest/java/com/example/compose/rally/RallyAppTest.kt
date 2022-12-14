package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class RallyAppTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyAppTest() {
        composeTestRule.setContent {
            RallyApp()
        }
        Thread.sleep(1000)
        composeTestRule.onRoot().printToLog("currentLabelExists")

        composeTestRule
            .onNode(
                hasContentDescription("Accounts"),
                useUnmergedTree = true
            ).performClick()

        Thread.sleep(1000)
        composeTestRule
            .onNode(
                hasContentDescription("Accounts") and isSelected(),
                useUnmergedTree = true
            )
            .assertExists()
        Thread.sleep(1000)

    }
}