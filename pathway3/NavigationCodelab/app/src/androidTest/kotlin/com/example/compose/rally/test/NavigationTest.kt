package com.example.compose.rally.test

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.compose.rally.RallyNavHost
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyNavHost() {
        composeTestRule.setContent {
            val navController = TestNavHostController(LocalContext.current)

            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )

            RallyNavHost(navController = navController)
        }

        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }
}