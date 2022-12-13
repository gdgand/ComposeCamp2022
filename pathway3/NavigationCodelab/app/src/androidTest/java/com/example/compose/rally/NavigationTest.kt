package com.example.compose.rally

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import junit.framework.Assert.fail
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    //컴포즈 테스트 규칙 추가
    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController
    // Compose 코드를 작성하고 컴포저블을 추가할 수 있습니다.
    @Test
    fun rallyNavHost_verifyOverviewStartDestination(){
        composeTestRule.setContent {
            //Creates a TestNavHostController

            navController = TestNavHostController(LocalContext.current)
            //Set a ComposeNavigator to the navController so it can navigate through composables

            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            RallyNavHost(navController = navController)
        }
        //테스트 구현을 완료하라는 알림 역할
        //fail()
        //올바른 화면 컴포저블이 표시되는지 확인하려면 contentDescription을 사용하여 컴포저블이 표시되었는지 어설션하면 됩니다.
        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }
}