package com.example.compose.rally.navi

import androidx.navigation.NavHostController
import com.example.compose.rally.Accounts
import com.example.compose.rally.Bills
import com.example.compose.rally.SingleAccount

class Router {
    companion object {
        fun navAccountScreen(navigationController: NavHostController) {
            navigationController.navigateSingleTopTo(Accounts.route)
        }
        fun navBillScreen(navigationController: NavHostController) {
            navigationController.navigateSingleTopTo(Bills.route)
        }
        fun navSingleAccountScreen(navigationController: NavHostController, argument: String) {
            navigationController.navigateSingleTopTo(
                SingleAccount.getDestination(argument)
            )
        }
    }
}