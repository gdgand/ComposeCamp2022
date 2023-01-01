package com.example.compose.rally

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// ComposeTest 규칙 추가
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * 첫번째 테스트 작성하기
     * 테스트 함수를 만들고 @Test 주석을 추가, 먼저 이 함수에서 테스트하려는 Compose 컨텐츠를 설정
     * composeTestRule의 setContent를 사용, setContent는 Composable 매개변수를 본문으로 받음.
     * setContent, 내에서 현재 테스트 제목인 RallyNavHost를 설정하고 여기에 새 navController의 인스턴스를 전달
     */

    lateinit var navController: TestNavHostController

    /*
    앞으로 만들 각 테스트에서도 RallyNavHost를 동일한 방식으로 설정해야 하기 때문에 불필요한 반복을 방지하고 테스트를 간결하게
    유지하기 위해 초기화된 주석 처리를 @Before 함수로 추출
     */

    @Before
    fun setupRallyNavHost() {
        composeTestRule.setContent {
            navController =
                TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            RallyNavHost(navController = navController)
        }
    }

    @Test
    fun rallyNavHost_verifyOverviewStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }

    // UI 클릭 및 화면 contentDescription을 통해 테스트
    // 앱 구현을 테스트할 때는 UI를 클릭하는 방법을 사용
    @Test
    fun rallyNavHost_clickAllAccount_navigatesToAccounts() {
        composeTestRule.onNodeWithContentDescription("All Bills")
            .performScrollTo()
            .performClick()
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "bills")
    }
}