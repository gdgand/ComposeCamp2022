package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onContinuedClicked: () -> Unit
    ) {
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = modifier.padding(vertical = 24.dp),
            onClick = onContinuedClicked
        )
        {
            Text("Continue")
        }
    }
}
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by remember { mutableStateOf(true)}

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinuedClicked = { shouldShowOnboarding = false })
        }
        else Greatings()
    }
}

@Composable
fun Greatings(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
    ) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for(name in names) {
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    // remember 는 리컴포지션을 방지하는데 사용
    val expanded = remember { mutableStateOf(false) }
    val extraPadding = if(expanded.value) 48.dp else 0.dp

    // Surface 는 색상을 사용한다.
    // Padding 추가
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { expanded.value = !expanded.value }) {
                Text( if(expanded.value)"Show less" else "Show more" )
            }
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme{
        MyApp(Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greatings()
    }
}