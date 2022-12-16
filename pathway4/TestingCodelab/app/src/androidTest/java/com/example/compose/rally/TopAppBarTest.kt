package com.example.compose.rally

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import com.example.compose.rally.ui.theme.RallyTheme
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Component를 고립시켜 테스트할 수 있다. 별도의 Activity 부터 시작할 필요가 없다.

    @Test
    fun myTeset() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTheme {
                RallyTopAppBar(
                    allScreens = allScreens,
                    onTabSelected = { },
                    currentScreen = RallyScreen.Accounts,
                )
            }
        }

        // 테스트를 위해서 Semantics tree를 사용한다.
        // - 스크린 요소를 찾거나
        // - 그들의 속성을 읽기 위함

        composeTestRule.onRoot().printToLog("currentLabelExists")

        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                        hasParent(
                            hasContentDescription(RallyScreen.Accounts.name)
                        ),
                useUnmergedTree = true
            )
//            .onNodeWithContentDescription(RallyScreen.Accounts.name)
//            .onNodeWithText(RallyScreen.Accounts.name.uppercase())
//            .assertIsSelected()
//            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab))
            .assertExists()
    }

    @Test
    fun test2() {
        composeTestRule.setContent {
            RallyApp()
        }

        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .performClick()

        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()),
                useUnmergedTree = true
            )
            .assertExists()
    }
}