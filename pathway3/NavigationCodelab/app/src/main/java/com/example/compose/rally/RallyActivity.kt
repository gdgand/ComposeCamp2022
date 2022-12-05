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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@Composable
fun RallyApp() {
    RallyTheme {
        // 탑레벨 컴포저블에 NavController를 위치시키면, 모든 컴포저블이 NavController에 접근할 수 있다.
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            rallyTabRowScreens.find { it.route == currentDestination?.route } ?: Overview
        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = rallyTabRowScreens,
                    onTabSelected = { newScreen ->
                        // navController.navigate(newScreen.route)
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Overview.route,
                modifier = Modifier.padding(innerPadding)
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
//                composable(
//                    route =
//                    "${SingleAccount.route}/{${SingleAccount.accountTypeArg}}",
//                    arguments = listOf(
//                        navArgument(SingleAccount.accountTypeArg) { type = NavType.StringType }
//                    )
//                ) {
//                    SingleAccountScreen()
//                }
                composable(
                    route = SingleAccount.routeWithArgs,
                    arguments = SingleAccount.arguments,
                    deepLinks = SingleAccount.deepLinks
                ) { navBackStackEntry ->
                    // argument 꺼내오기
                    val accountType =
                        navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)
                    SingleAccountScreen(accountType)
                }
            }
        }
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // popUpTo(startDestination) { saveState = true }
        // 그래프 출발지로 pop. 큰 스택을 다시 구성하는 것을 피하기 위해.
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }

        // 스택 탑에 있는 친구와 동일하면 다시 쌓지 않기
        // launchSingleTop 옵션을 사용하지 않으면 동일한 탭을 여러번 눌렀을 때
        // 같은 화면이 계속해서 쌓이게 됨
        launchSingleTop = true

        // restoreState = true
        // 이전의 상태로 복구해야 하는지 옵션
        // 이전에 저장한 상태가 없다면 true로 설정해도 아무런 효과가 없다.
        restoreState = true
    }

private fun NavHostController.navigateToSingleAccount(accountType: String) {
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
}




