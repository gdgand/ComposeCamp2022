package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codelab.basics.MainActivity.Companion.HOME
import com.codelab.basics.MainActivity.Companion.ONBOARDING
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
//                    AppNavHost(modifier = Modifier.fillMaxSize())
                    MyApp()
                }
            }
        }
    }
    companion object {
        const val ONBOARDING = "onBoarding"
        const val HOME = "home"
    }
}

@Composable
private fun MyApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if(shouldShowOnboarding) {
        OnboardingScreen(
            onNavigateToHome = {
                shouldShowOnboarding = false
            }
        )
    } else {
        HomeScreen(onNavigateToMain = {
            shouldShowOnboarding = true
        })
    }
}

@Composable
private fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ONBOARDING
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ONBOARDING) {
            OnboardingScreen(
                onNavigateToHome = {
                    navController.navigate(HOME)
                }
            )
        }
        composable(HOME) {
            HomeScreen(
                onNavigateToMain = {
                    navController.navigate(ONBOARDING)
                }
            )
        }
    }
}

