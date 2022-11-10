package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                BasicMyApp()
            }
        }
    }
}

@Composable
private fun BasicMyApp(names: List<String> = listOf("Android", "Malibin")) {
    var shouldShowOnboarding: Boolean by remember { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Welcome to Basics Codelab!")
            Button(
                    modifier = Modifier.padding(vertical = 24.dp),
                    onClick = onContinueClicked,
            ) {
                Text(text = "Continue")
            }
        }
    }
}

@Composable
private fun Greetings(names: List<String> = listOf("Android", "Malibin")) {
    val expandedState = remember { mutableStateOf(false) }
    val expandedPadding = if (expandedState.value) 48.dp else 0.dp

    Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
                modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
        ) {
            Column(
                    modifier = Modifier
                            .weight(1f)
                            .padding(bottom = expandedPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(
                    onClick = { expandedState.value = !expandedState.value }
            ) {
                Text(text = if (expandedState.value) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        BasicMyApp()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    ComposeBasicCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}
