package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        val allScreen = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreen,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

        // composeTestRule{.finder}{.assertion}{.action}
        // clearAndSetSemantics { contentDescription = text } 하위 항목에서 속성을 지우고 자체 콘텐츠 설명을 설정. ACCOUNTS -> Accounts
//        composeTestRule.onNodeWithContentDescription(RallyScreen.Accounts.name)
//            .assertExists()

        composeTestRule.onNode(
            hasText(RallyScreen.Accounts.name.uppercase()) and
                    hasParent(
                        hasContentDescription(RallyScreen.Accounts.name)
                    ),
            useUnmergedTree = true
        ).assertExists()

        composeTestRule.onNodeWithContentDescription(label = RallyScreen.Accounts.name)
            .assertIsSelected()
    }

//    @Test
//    fun rallyTopAppBarTest_currentLabelExists() {
//        val allScreens = RallyScreen.values().toList()
//        composeTestRule.setContent {
//            RallyTopAppBar(
//                allScreens = allScreens,
//                onTabSelected = { },
//                currentScreen = RallyScreen.Accounts
//            )
//        }
//
//        composeTestRule
//            .onNodeWithContentDescription(RallyScreen.Accounts.name)
//            .assertExists()
//    }
}