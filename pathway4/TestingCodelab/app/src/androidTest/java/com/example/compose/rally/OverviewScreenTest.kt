package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.compose.rally.ui.overview.OverviewBody
import org.junit.Rule
import org.junit.Test


/**
 * 작성하는 모든 Test는 테스트 대상과 적절하게 동기화가 되어야 한다.
 * 동기화가 없으면 테스트느 표시 되기 전에 해당 요소를 찾거나 불필요한 대기가 있을 수 있음.
 * 다음과 같이 새로운 test 생성
 * 테스트 시 실패 발생
 * Compose가 영구적으로 사용중 이므로 테스트와 동기화할 방법이 없음을 알린다.
 *
 */
class OverviewScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun overviewScreen_alertsDisplayed() {
        composeTestRule.setContent {
            OverviewBody()
        }

        composeTestRule
            .onNodeWithText("Alerts")
            .assertIsDisplayed()
    }
}