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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.compose.rally.ext.navigateSingleTopTo
import com.example.compose.rally.ui.accounts.SingleAccountScreen
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
        val currentDestination = currentBackStack?.destination

        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = rallyTabRowScreens,
                    onTabSelected = { screen ->
                        navController.navigateSingleTopTo(screen.route)
                    },
                    currentScreen = rallyTabRowScreens.find { it.route == currentDestination?.route } ?: Overview
                )
            }
        ) { innerPadding ->
            RallyNavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
        }
    }
}

@Composable
fun RallyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) = NavHost(
    modifier = modifier,
    navController = navController,
    startDestination = Overview.route
) {
    composable(route = Overview.route) {
        OverviewScreen(
            onClickSeeAllAccounts = {
                navController.navigateSingleTopTo(Accounts.route)
            },
            onClickSeeAllBills = {
                navController.navigateSingleTopTo(Bills.route)
            }
        )
    }
    composable(route = Accounts.route) {
        Accounts.screen()
    }
    composable(route = Bills.route) {
        Bills.screen()
    }
    composable(
        route = "${SingleAccount.route}/${SingleAccount.accountTypeArg}",
        arguments = listOf(
            navArgument(name = SingleAccount.accountTypeArg) {
                type = NavType.StringType
            }
        ),
        deepLinks = listOf(
            navDeepLink {
                this.uriPattern = "rally://${SingleAccount.route}/{${SingleAccount.accountTypeArg}}"
            }
        )
    ) {
        val accountType = it.arguments?.getString(SingleAccount.accountTypeArg)
        SingleAccountScreen(accountType)
    }
}
