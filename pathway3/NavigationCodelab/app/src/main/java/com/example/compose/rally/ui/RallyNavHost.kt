package com.example.compose.rally.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.rally.*
import com.example.compose.rally.ui.accounts.AccountsScreen
import com.example.compose.rally.ui.accounts.SingleAccountScreen
import com.example.compose.rally.ui.bills.BillsScreen
import com.example.compose.rally.ui.overview.OverviewScreen

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
                onClickSeeAllAccounts = { navController.navigateSingleTop(Accounts.route) },
                onClickSeeAllBills = { navController.navigateSingleTop(Bills.route) },
                onAccountClick = { type ->
                    navController.navigateToSingleAccount(type)
                }
            )
        }

        composable(route = Accounts.route) {
            AccountsScreen(
                onAccountClick = { type ->
                    navController.navigateToSingleAccount(type)
                }
            )
        }
        composable(route = Bills.route) {
            BillsScreen()
        }

        composable(
            route = SingleAccount.routeWithArgs,
            arguments = SingleAccount.arguments,
            deepLinks = SingleAccount.deeplinks
        ) { navBackStack ->
            val accountType = navBackStack.arguments?.getString(SingleAccount.accountTypeArg)
            SingleAccountScreen(accountType)
        }
    }
}

fun NavController.navigateSingleTop(route: String) = navigate(route) {
    launchSingleTop = true
    popUpTo(graph.findStartDestination().id) { saveState = true }
    restoreState = true
}

private fun NavHostController.navigateToSingleAccount(type: String) {
    navigateToSingleAccount("${SingleAccount.route}/$type")
}