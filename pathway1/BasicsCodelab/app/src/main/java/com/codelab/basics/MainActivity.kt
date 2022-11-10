package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun OnboardingScreen() {
    var shouldShowOnboarding by remember {
        mutableStateOf(true)
    }

    androidx.compose.material.Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "안녕하세요! 기본 Codelab 입니다.!!!")
            Button(
                onClick = { shouldShowOnboarding = false },
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Text(text = "계속")
            }
        }
    }
}


@Composable
private fun MyApp() {
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen()
    } else {

        Surface(color = MaterialTheme.colors.background) {
            Greetings()
        }
    }

}

@Composable
fun Greetings(names: List<String> = listOf("World", "Compose", "Hello")) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}


@Composable
fun Greeting(name: String) {
    val expanded = remember { mutableStateOf(false) }
    val extraPadding = if (expanded.value == true) 48.dp else 0.dp

    Surface(color = MaterialTheme.colors.primary) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello ")
                Text(text = "$name!!!!!!!!")
            }
            OutlinedButton(onClick = {
                expanded.value = !expanded.value
            }) {
                Text(if (expanded.value) "작게보기" else "더보기")
            }
        }

    }
}

//<---------------------PREVIEW---------------->

@Preview(showBackground = true, widthDp = 320, heightDp = 160)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen()
    }
}

@Preview(showBackground = false, widthDp = 320)
@Composable
fun DefaultPreview(names: List<String> = listOf("World", "Compose")) {
    BasicsCodelabTheme {
        Column {

            for (name in names) {
                Greeting(name = name)
            }
        }
    }
}