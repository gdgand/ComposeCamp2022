package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // TODO: Add tests
    @Test
    fun rallyTopAppBarTest() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { /*TODO*/ },
                currentScreen = RallyScreen.Accounts
            )
        }
        //Thread.sleep(5000)
    }

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

        //UI 요소 찾기, 해당 속성 확인 및 작업 수행은 다음 패턴에 따라 테스트 규칙을 통해 수행
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsSelected()
    }


    //시맨틱 트리
    //Compose 테스트는 시맨틱 트리 라는 구조를 사용하여
    // 화면에서 요소를 찾고 해당 속성을 읽습니다.
    // TalkBack 과 같은 서비스에서 읽을 수 있도록 접근성 서비스에서도 사용하는 구조
    //현재 탭의 레이블이 대문자로 표시되는지 확인
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
        //노드에서 함수를 사용하여 시맨틱 트리를 인쇄
        //composeTestRule.onRoot().printToLog("currentLabelExists")

        //디버깅 성공
        //하지만 이 테스트는 그다지 유용하지 않음
        //더 깊이 들어가야함함
       /*composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            //.onNodeWithText(RallyScreen.Accounts.name.uppercase())
            .assertExists()*/
        //composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")
        //속성 병합, 병합 해제된 시멘트 트리
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