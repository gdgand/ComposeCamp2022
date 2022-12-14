package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // 초기 UI 가 RallyScreen.Accounts 를 선택하고 있는지 테스트
    @Test
    fun rallyTopAppBarTest_currentTabSelected() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)    // "RallyScreen.Accounts.name" 이라는 ContentDescription 을 찾는다
            .assertIsSelected()                                         // 해당 node 가 현재 선택된 상태인지 테스트!
    }

    // 초기 UI 가 RallyScreen.Accounts 를 선택하고 있을 때 Accounts 의 Label 이존재하는지 테스트
    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        // Todo : merged Tree
        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name.uppercase())       // "RallyScreen.Accounts.name.uppercase()" 에 해당하는 ContentDescription 찾기
            .assertExists()                                                            // 해당 node 가 실제로 존재하는가?


        // Todo : unmerged Tree
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")
        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                        hasParent(
                            hasContentDescription(RallyScreen.Accounts.name)
                        ),
                useUnmergedTree = true
            )
            .assertExists()
    }
}