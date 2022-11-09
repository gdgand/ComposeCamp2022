package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codelab.basics.MainActivity.Companion.HOME
import com.codelab.basics.MainActivity.Companion.MAIN
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavHost(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
    companion object {
        const val MAIN = "main"
        const val HOME = "home"
    }
}

@Composable
private fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MAIN
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MAIN) {
            MainScreen(
                onNavigateToHome = {
                    navController.navigate(HOME)
                }
            )
        }
        composable(HOME) {
            HomeScreen(
                onNavigateToMain = {
                    navController.navigate(MAIN)
                }
            )
        }
    }
}

