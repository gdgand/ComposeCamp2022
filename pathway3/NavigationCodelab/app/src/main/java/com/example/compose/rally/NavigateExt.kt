package com.example.compose.rally // ktlint-disable filename

import androidx.navigation.NavController

fun NavController.navigateSingleTopTo(string: String) {
    navigate(string) {
        launchSingleTop = true
    }
}
