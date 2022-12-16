package com.example.compose.rally

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.rally.ui.accounts.AccountsScreen
import com.example.compose.rally.ui.accounts.SingleAccountScreen
import com.example.compose.rally.ui.bills.BillsScreen
import com.example.compose.rally.ui.overview.OverviewScreen

@Composable
fun RallyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Overview.route,
        modifier = modifier,
    ) {
        // graph 정의하기
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
            deepLinks = SingleAccount.deepLinks,
        ) { navBackStackEntry ->
            val accountType =
                navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)
            SingleAccountScreen(accountType)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) {
    val navController = this
    navController.navigate(route) {
        val startDestinationId = navController.graph.findStartDestination().id
        // 띄우는 화면이 이미 백스택에 있다면 그 백스택까지 팝한다. 불필요하게 큰 백스택을 막는다.
        popUpTo(startDestinationId) {
            saveState = false //TODO: 무슨 용도인지?
        }
        restoreState = false //TODO: 무슨 용도인지?

        // 같은 탭을 눌렀을 때 화면을 다시 띄우지 않는다.
        launchSingleTop = true
    }
}

private fun NavHostController.navigateToSingleAccount(accountType: String) =
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
