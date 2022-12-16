/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.rally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.rally.ui.accounts.AccountsScreen
import com.example.compose.rally.ui.accounts.SingleAccountScreen
import com.example.compose.rally.ui.bills.BillsScreen
import com.example.compose.rally.ui.components.RallyTabRow
import com.example.compose.rally.ui.overview.OverviewScreen
import com.example.compose.rally.ui.theme.RallyTheme

/**
 * This Activity recreates part of the Rally Material Study from
 * https://material.io/design/material-studies/rally.html
 */
class RallyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
//        var currentScreen: RallyDestination by remember { mutableStateOf(Overview) }
        /**
         * NavController는 항상 컴포저블 계층 구조의 최상위 수준에서 만들고 배치해야함.
         * 이렇게해야 참조하는 모든 Composable이 접근 가능
         * 이 지점: Root Composable, 앱 전체의 진입점
         */
        val navController = rememberNavController()
        /**
         * RallyTabRow내에서 선택한 탭을 펼치고 접는 것이 더이상 동작하지 않음
         * Compose Navigation을 사용하여 이 동작을 다시 설정하려면 각 시점에 현재 표시된 대상이 무엇인지를 알아야 한다.
         * 따라서, 백 스택에서 현재 대상의 실시간 업데이트를 State의 형식으로 받아보기 위해
         * navController.currentBackStackEntryAsState()를 사용하여 현재 destination:을 살펴 본다.
         */

        val currentBackStack by navController.currentBackStackEntryAsState()

        val currentDestination = currentBackStack?.destination

        /*
         currentScreen을 업데이트 하기 위해 rallyTabRowScreens 목록을 순환하여 일치하는 경로를 찾은 다음 그
         RallyDestination을 반환해야 한다. -> .find() 함수 사용
         */

        val currentScreen = rallyTabRowScreens.find { it.route == currentDestination?.route }
            ?: Overview

        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = rallyTabRowScreens,
                    // NavController 안내, 탭선택시 navigate 호출
                    onTabSelected = { newScreen ->
                            navController.navigateSingleTopTo(newScreen.route)
                                    },
                    currentScreen = currentScreen,
                )
            }
        ) { innerPadding ->
            /*
             1. Box -> NavHost로 변경
             2. navController를 전달하여 NavHost에 연결
             */
            RallyNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
            NavHost(
                navController = navController,
                startDestination = Overview.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                // OverView 추가후 고유 문자열 route 설정
                // 같은 패턴으로 3개의 기본 화면 Composable을 3개의 대상으로 추가
                composable(route = Overview.route) {
                    // 탐색 콜백에 경로 전달
                    // navController는 버튼 클릭시 올바른 대상으로 이동하기 위한 정확한 대상에 대한 경로 정보를 갖음.
                    OverviewScreen(
                        onClickSeeAllAccounts = {
                                navController.navigateSingleTopTo(Accounts.route)
                                                },
                        onClickSeeAllBills = {
                            navController.navigateSingleTopTo(Bills.route)
                        },

                        onAccountClick = { accountType ->
                            navController.navigateToSingleAccount(accountType)
                        }

                    )
                }
                composable(route = Accounts.route) {
                    AccountsScreen(
                        onAccountClick = { accountType ->
                            navController.navigateToSingleAccount(accountType)

                        }
                    )
                }
                composable(route = Bills.route) {
                    BillsScreen()
                }
                // 개별 계좌의 표시를 처리할 새 대상을 그래프에 추가
                /**
                 * SingleAccountScreen 도착 대상 설정하기
                 * SingleAccountScreen에 도착하면 이 대상이 열렸을 때 정확히 어떤 계좌 유형을 표시해야 하는지 알 수
                 * 있도록 추가 정보가 필요함. 이때 인수를 사용해 이러한 종류의 정보 전달이 가능.
                 * {account_type} 인수 필요, accountTypeArg 문자열로 이미 정의
                 */
                composable(
                    route = SingleAccount.routeWithArgs,
                    // 이 Composable이 인수를 받아야 한다는 사실을 알려줌.
                    // Composable 함수는 기본적으로 인수 목록을 받기 때문에 원하는 개수 만큼 정의 가능.
                    // 깔끔하고 가독성 있게 유지
                    arguments = SingleAccount.arguments,

                    /*
                    - 딥 링크 매개 변수 추가
                    - 매니페스트 rally://singleaccount의 intent-filter에 정의된 것과 일치하는 uriPattern을 전달.
                     */
                    // RallyDestinations에 정의하여 NavHost에서 호출
                   deepLinks = SingleAccount.deepLinks
                // 전달된 인수 값을 가져오는 작업 진행
                ) { navBackStackEntry ->
                    val accountType =
                        navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)
                    SingleAccountScreen(accountType)
                }
            }
        }
    }
}

/**
 * 추가로 RallyActivity를 테스트 가능하게 만들고 깔끔하게 유지하기 위해, 현재 NavHost 와 도우미 함수를 RallyApp
 * Composable에서 자체 컴포저블 함수로 추출하여 이름을 RallyNavHost라고 지정
 * RallyApp은 NavController가 직접 사용할 수 있는 유일한 Composable, 다른 모든 중첩된 컴포저블 화면은 NavController
 * 자체가 아닌 탐색 콜백만 받아와야 함.
 */
@Composable
fun RallyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Overview.route,
        modifier = modifier
    ) {
        composable(route = Overview.route) {
            OverviewScreen(
                onClickSeeAllAccounts = {
                    navController.navigateSingleTopTo(Accounts.route)
                },
                onClickSeeAllBills = {
                    navController.navigateSingleTopTo(Bills.route)
                },
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }
        composable(route = Accounts.route) {
            AccountsScreen(
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }
        composable(route = Bills.route) {
            BillsScreen()
        }
        composable(
            route = SingleAccount.routeWithArgs,
            arguments = SingleAccount.arguments,
            deepLinks = SingleAccount.deepLinks
        ) { navBackStackEntry ->
            val accountType =
                navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)
            SingleAccountScreen(accountType)
        }
    }
}

// 확장 함수
// 특정 계좌 유형을 탭하면 그 즉시 이 계좌 유형 문자열을 사용하여 탐색 인수로 쉽게 전달.
private fun NavHostController.navigateToSingleAccount(accountType: String) {
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
}

// 대상의 단일 사본 실행
// launchSingleTop 플래그를 navController.navigate() 동작으로 전달.
/**
 * 탐색 옵션 및 백 스택 상태 제어
 * navigateSingleTopTo확장 함수 내에서 사용할 수 있는 몇가지 추가 옵션
 * launchSingleTop = true 앞서 언급했듯이, 백 스택 위에 대상의 사본이 최대 1개가 되게 해준다.
 * popUpTo(startDestination) { saveState = true} = 탭을 선택했을 때 백 스택에 대규모 대상 스택이 빌드되지 않도록
   그래프의 시작 대상을 팝업으로 만든다.
 * restoreState = true: 이 탐색 동작이 이전에 PopUpToBuilder.saveState or popUpToSaveState 속성에 의해 저장된
 * 상태를 복원하는지 여부를 정한다.
 */
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
//        popUpTo(
//            this@navigateSingleTopTo.graph.findStartDestination().id
//        ) {
//            saveState = true
//        }
        launchSingleTop = true
//        restoreState = true
}
