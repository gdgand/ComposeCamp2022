package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

/**
 * ComposeTestRule() 추가
 * 테스트 중인 Compose 컨텐츠를 설정하고 상호 작용 가능
 * Compose를 사용하면 구성 요소를 개별적으로 테스트가 가능하여 단순화 가능
 * 테스트에 사용할 Compose UI 선택이 가능한데 이는 setContent() 메소드로 수행
  */

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // TODO: Add tests
    /*
    setContent 내에서 RallyTopAppBar를 호출하고 파라미터 추가
    RallyTopAppBar는 우리가 제어하는 fakeData를 전달할 수 있도록 제공하기 쉬운 세가지 매개 변수를 제공
     */
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
        //Thread.sleep(5000)      // 추가해 진행 사항 파악
        // 여기서 테스트 실패
        /*
        semantics tree를 이용해 이를 디버깅 하는 방법 사용
        Compose Test는 semantics Tree 라는 구조를 사용해 화면에서 요소를 찾고 해당 속성을 읽음.

        Compose는 Text와 같은 일부 Composable에서 이러한 Semantics 속성을 자동으로 노출.
        탭 내부에 Text가 표시되는지 여부를 확인하기 위해 useUnmergedTree = true를 전달하는 트리를 구성
        */

        /**
         * 모든 finder에는 useUnmergedTree라는 매개 변수가 있음.
         */

        // composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

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

