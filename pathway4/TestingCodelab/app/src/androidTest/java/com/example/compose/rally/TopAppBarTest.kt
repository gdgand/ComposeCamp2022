package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Add tests
    @Test
    fun rallyTopAppBarTest() {
        val allScreen = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreen,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }
        Thread.sleep(5000)
    }

    @Test
    fun rallyTopAppBarTest_currentTableSelected() {
        val allScreen = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreen,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }

    // Test Fail

//    Turns out, there is no text property with a value of "ACCOUNTS" and this is why the test fails.
//    However, there is a content description for each tab.
//    You can check how this property is set in the RallyTab composable inside TopAppBar.kt:

//    @Test
//    fun rallyTopAppBarTest_currentLabelExists() {
//        val allScreen = RallyScreen.values().toList()
//        composeTestRule.setContent {
//            RallyTopAppBar(
//                allScreens = allScreen,
//                onTabSelected = {},
//                currentScreen = RallyScreen.Accounts
//            )
//        }
//        composeTestRule.onRoot().printToLog("currentLabelExists")
//
//        composeTestRule
//            .onNodeWithText(RallyScreen.Accounts.name.toUpperCase())
//            .assertExists()
//    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreen = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreen,
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts
            )
        }
//        composeTestRule.onRoot().printToLog("currentLabelExists")

//        composeTestRule
//            .onNodeWithContentDescription(RallyScreen.Accounts.name)
//            .assertExists()

//        Bad news though: this test is not very useful! If you look at the Semantics tree closely,
//        the content descriptions of all three tabs are there whether or not their tab is selected. We must go deeper!


//        The property MergeDescendants = 'true' is telling us that this node had descendants,
//        but they have been merged into it. In tests we oftentimes need to access all nodes.

//        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                        hasParent(
                            hasContentDescription(RallyScreen.Accounts.name)
                        ),
                useUnmergedTree = true
            )

//        Note: In this case, strictly, you don't have to add the parent to the matcher because it's a very isolated test.
//        However, it's a good idea to avoid using broad finders alone (such as hasText) which might fail in larger tests (when other instances of the text might be found).
    }
}