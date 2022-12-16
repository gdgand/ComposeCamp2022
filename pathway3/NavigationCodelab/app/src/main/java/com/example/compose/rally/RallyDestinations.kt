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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.compose.rally.ui.accounts.AccountsScreen
import com.example.compose.rally.ui.accounts.SingleAccountScreen
import com.example.compose.rally.ui.bills.BillsScreen
import com.example.compose.rally.ui.overview.OverviewScreen

/**
 * Contract for information needed on every Rally navigation destination
 * 해당 컴포저블 화면으로 이동할 때는 각 화면에 특정 시점에 도달하고자 하므로 화면을 탐색 대상이라고 한다.
 * 대상은 RallyDestinations에 정의되어 있음.
 * 밑에서 정의한 3 객체는 해당 인터페이스에서 확장되며 탐색을 위한 각 대상에 필요한 정보를 포함한다.
 * 1. Topbar의 icon
 * 2. route 문자열(Compose Navigation에 필요한 대상 경로)
 * 3. 이 대상의 컴포저블을 나타내는 screen
 */
interface RallyDestination {
    val icon: ImageVector
    val route: String
    // val screen: @Composable () -> Unit
}


/**
 * Rally app navigation destinations
 */
object Overview : RallyDestination {
    override val icon = Icons.Filled.PieChart
    override val route = "overview"
    // override val screen: @Composable () -> Unit = { OverviewScreen() }
}

object Accounts : RallyDestination {
    override val icon = Icons.Filled.AttachMoney
    override val route = "accounts"
    // override val screen: @Composable () -> Unit = { AccountsScreen() }
}

object Bills : RallyDestination {
    override val icon = Icons.Filled.MoneyOff
    override val route = "bills"
   //  override val screen: @Composable () -> Unit = { BillsScreen() }
}

object SingleAccount : RallyDestination {
    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
    // part of the RallyTabRow selection
    override val icon = Icons.Filled.Money
    override val route = "single_account"
    // override val screen: @Composable () -> Unit = { SingleAccountScreen() }
    const val accountTypeArg = "account_type"
    // 경로 이동 작업.
    val routeWithArgs = "${route}/{${accountTypeArg}}"
    // 인수 목록 이동
    val arguments = listOf(
        navArgument(accountTypeArg) { type = NavType.StringType}
    )
    // 가독성을 위해 RallyActivity로 부터 이동
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://$route/{$accountTypeArg}" }
    )
}

// Screens to be displayed in the top RallyTabRow
// 객체로 정의된 3가지 기본 대상
val rallyTabRowScreens = listOf(Overview, Accounts, Bills)
