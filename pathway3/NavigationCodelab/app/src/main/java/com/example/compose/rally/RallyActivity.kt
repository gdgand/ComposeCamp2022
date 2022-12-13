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
import com.example.compose.rally.SingleAccount.arguments
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

        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        // Fetch your currentDestination:
        val currentDestination = currentBackStack?.destination

        // Change the variable to this and use Overview as a backup screen if this returns null
        val currentScreen = rallyTabRowScreens.find { it.route == currentDestination?.route } ?: Overview
        //        var currentScreen: RallyDestination by remember { mutableStateOf(Overview) }

        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = rallyTabRowScreens,
                    // Pass the callback like this,
                    // defining the navigation action when a tab is selected:
                    onTabSelected = { newScreen ->
                        navController
                            .navigateSingleTopTo(newScreen.route) // 탭을 누를 때마다 사본이 생성되는 것을 하나만 실행되게
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
/*            Box(Modifier.padding(innerPadding)) {
                currentScreen.screen()
            }*/
            /*NavHost(navController = navController,
                startDestination = Overview.route,
                modifier = Modifier.padding(innerPadding)){
                // builder parameter
                composable(route = Overview.route){
//                    Overview.screen()
                    OverviewScreen(
                        onClickSeeAllAccounts = {
                            navController.navigateSingleTopTo(Accounts.route)
                        },
                        onClickSeeAllBills = {
                            navController.navigateSingleTopTo(Bills.route)
                        },
                        onAccountClick = {accountType ->
                            navController.navigateToSingleAccount(accountType)
                        }
                    )
                }
                composable(route = Accounts.route){
//                    Accounts.screen()
                    AccountsScreen(
                        onAccountClick = { accountType ->
                            navController.navigateToSingleAccount(accountType)
                        }
                    )
                }
                composable(route = Bills.route){
//                    Bills.screen()
                    BillsScreen()
                }
                composable(
                    route = SingleAccount.routeWithArgs,
                    arguments = SingleAccount.arguments,
                    *//*deepLinks = listOf(navDeepLink {
                        uriPattern = "rally://${SingleAccount.route}/{${SingleAccount.accountTypeArg}}"
                    })*//*
                    deepLinks = SingleAccount.deepLinks
                ){
                    val accountType = it.arguments?.getString(SingleAccount.accountTypeArg)
                    SingleAccountScreen(accountType)
                }
                //SingleAccountScreen으로 이동하면 계좌 유형을 표시하는 데 필요한 정보를 갖게 되었습니다
            }*/
            RallyNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun RallyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = Overview.route,
        modifier = modifier
    ){
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


// 탭을 누를 때마다 사본이 생성되는 것을 하나만 실행되게
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
//        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
//            saveState = true
//        }
        launchSingleTop = true
//        restoreState = true
    }


/*
동일한 navigateSingleTopTo 확장 함수 내에서 사용할 수 있는 몇 가지 추가 옵션을 살펴보겠습니다.

launchSingleTop = true - 앞서 언급했듯이, 백 스택 위에 대상의 사본이 최대 1개가 되도록 해 줍니다.
Rally 앱의 경우에는 동일한 탭을 여러 번 탭해도 동일한 대상의 사본이 여러 개 실행되지 않습니다.
popUpTo(startDestination) { saveState = true } - 탭을 선택했을 때 백 스택에 대규모 대상 스택이 빌드되지 않도록 그래프의 시작 대상을 팝업으로 만듭니다.
Rally의 경우에는 대상에서 뒤로 화살표를 누르면 백 스택 전체가 '개요'로 팝업됩니다.
restoreState = true: 이 탐색 동작이 이전에 PopUpToBuilder.saveState 또는 popUpToSaveState 속성에 의해 저장된 상태를 복원하는지 여부를 정합니다. 이동할 대상 ID를 사용하여 이전에 저장된 상태가 없다면 이 옵션은 효과가 없습니다.
Rally의 경우에는 동일한 탭을 다시 탭하면 이전 데이터와 사용자 상태가 다시 로드되지 않고 화면에 그대로 유지됩니다.동일한 navigateSingleTopTo 확장 함수 내에서 사용할 수 있는 몇 가지 추가 옵션을 살펴보겠습니다.

launchSingleTop = true - 앞서 언급했듯이, 백 스택 위에 대상의 사본이 최대 1개가 되도록 해 줍니다.
Rally 앱의 경우에는 동일한 탭을 여러 번 탭해도 동일한 대상의 사본이 여러 개 실행되지 않습니다.
popUpTo(startDestination) { saveState = true } - 탭을 선택했을 때 백 스택에 대규모 대상 스택이 빌드되지 않도록 그래프의 시작 대상을 팝업으로 만듭니다.
Rally의 경우에는 대상에서 뒤로 화살표를 누르면 백 스택 전체가 '개요'로 팝업됩니다.
restoreState = true: 이 탐색 동작이 이전에 PopUpToBuilder.saveState 또는 popUpToSaveState 속성에 의해 저장된 상태를 복원하는지 여부를 정합니다. 이동할 대상 ID를 사용하여 이전에 저장된 상태가 없다면 이 옵션은 효과가 없습니다.
Rally의 경우에는 동일한 탭을 다시 탭하면 이전 데이터와 사용자 상태가 다시 로드되지 않고 화면에 그대로 유지됩니다.
 */

private fun NavHostController.navigateToSingleAccount(accountType: String){
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
}