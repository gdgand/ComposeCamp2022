package com.example.compose.rally

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest() {
        val allScreens = RallyScreen.values().toList()
        var currentScreen = mutableStateOf(RallyScreen.Accounts)
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { currentScreen.value = it },
                currentScreen = currentScreen.value
            )
        }

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Overview.name)
            .performClick()

        composeTestRule
            .onNode(
                hasText(RallyScreen.Overview.name.uppercase()) and
                        hasParent(
                            hasContentDescription(RallyScreen.Overview.name)
                        ),
                useUnmergedTree = true
            )
            .assertExists()
    }
}