package com.example.compose.rally.ext

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.compose.rally.SingleAccount

fun NavHostController.navigateSingleTopTo(route: String) = navigate(route) {
    popUpTo(graph.findStartDestination().id) {
        saveState = true
    }

    launchSingleTop = true
    restoreState = true
}

fun NavHostController.navigateToSingleAccount(accountType: String) {
    navigateSingleTopTo("${SingleAccount.route}/$accountType")
}