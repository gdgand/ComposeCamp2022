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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
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

/*
//탐색 대상 사본이 하난만 실행되도록 하는 확장함수
//launchSingleTop = true - 앞서 언급했듯이, 백 스택 위에 대상의 사본이 최대 1개가 되도록 해 줍니다.
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }
    */

//popUpTo(startDestination) { saveState = true } - 탭을 선택했을 때 백 스택에 대규모 대상 스택이 빌드되지 않도록 그래프의 시작 대상을 팝업으로 만듭니다.
//restoreState = true: 이 탐색 동작이 이전에 PopUpToBuilder.saveState 또는 popUpToSaveState 속성에 의해 저장된 상태를 복원하는지 여부를 정합니다
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route){
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ){
            saveState = true // start destination 상태 저장
        }

        launchSingleTop = true
        restoreState = true // 같은 tab 선택시 유지
    }

@Composable
fun RallyApp() {
    RallyTheme {

        // NavController
        // 탐색대상의 백스택을 관리
        // route : 탐색대상으로 이동하는 딥링크
        val navController = rememberNavController()

        //백 스택 항목의 위에 무엇이 있는지를 확인

        val currentBackStack by navController.currentBackStackEntryAsState()
        // Fetch your currentDestination:
        val currentDestination = currentBackStack?.destination

        //var currentScreen: RallyDestination by remember { mutableStateOf(Overview) }
        val currentScreen = rallyTabRowScreens.find {it.route == currentDestination?.route} ?: Overview
        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = rallyTabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            RallyNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun RallyNavHost(
    navController : NavHostController,
    modifier: Modifier =  Modifier,
){
    // NavHost
    // 컨테이너 역할을 하며 그래프의 현재 대상을 표시하는 일을 담당
    // 여러 컴포저블 간에 이동하는 과정에서 NavHost 의 콘텐츠가 자동으로 재구성된다.
    // NavController 와 NavGraph 를 연결
    NavHost(
        navController = navController,
        startDestination = Overview.route,
        modifier = modifier
    ) {
        // 마지막 매개변수 builder: NavGraphBuilder.() -> Unit은 탐색 그래프를 정의하고 빌드하는 일을 담당합니다
        // 아래에 탐색정보를 정의한다. 탐색정보(NavGraph)
        composable(route = Overview.route) {
            //Overview.screen()
            //컴포즈 화면에 추가 정보(click event와 같은)를 직접 전달하기 위해
            //RallyDestination과 화면 객체는 icon, route와 같은 탐색 관련 정보만 저장하며 Compose UI와 분리한다.
            /*

            앞서 언급했듯이 navController 탐색 계층 구조의 최상위 수준에 유지하고, (OverviewScreen) 등에 직접 전달하는 대신)
            App 컴포저블의 수준으로 호이스팅하면
            실제 또는 모의 navController 인스턴스를 사용하지 않고도 OverviewScreen 컴포저블을 단독으로
            미리 보고 재사용 및 테스트할 수 있습니다.
             콜백을 전달하는 방식에서는 클릭 이벤트를 빠르게 변경할 수도 있습니다.
             */
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
        composable(route = Accounts.route){
            AccountsScreen(
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }
        composable(route = Bills.route){
            BillsScreen()
        }
        //사용자가 클릭한 정확한 계좌 유형에 관한 추가 정보를 제공해야 합니다. 이 작업은 인수를 통해 할 수 있습니다.
        //. 인수를 사용하면 제공된 인수에 따라 서로 다른 정보를 표시할 수 있습니다.
        //이름이 지정되 인수 {}, 변수이름 ${} 이름이 지정된 인수[변수이름] => 동적인 변수 이름을 이름으로 사용
        composable(
            route = SingleAccount.routeWithArgs,
            //이 컴포저블이 인수를 받을 것이란것을 알려준다.
            arguments = SingleAccount.arguments,
            deepLinks = SingleAccount.deepLinks

        ){ navBackStackEntry ->
            //Retrieve the passed argument

            //navBackStackEntry: 백스택에 있는 항목의 현재 경로 및 전달된 인수에 관한 정보를 저장하는 클래스인
            val accountType =
                navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)

            //pass accountType to SingleAccountScreen
            SingleAccountScreen(accountType)
        }
    }

}
private fun NavHostController.navigateToSingleAccount(accountType: String){
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
}