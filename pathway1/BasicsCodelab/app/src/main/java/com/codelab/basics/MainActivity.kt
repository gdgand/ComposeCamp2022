package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme
import kotlin.coroutines.Continuation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnbarding by remember {
        mutableStateOf(true)
    }

    Surface(modifier = modifier) {
        if(shouldShowOnbarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnbarding = false})
        } else {
            Greetings()
        }
    }
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit
) {

    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    val expanded = remember {
        mutableStateOf(false)
    }

    val extraPadding = if(expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(extraPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            ElevatedButton(onClick = { expanded.value = !expanded.value }) {
                Text(
                    text = if(expanded.value) "Show less" else "Show more",
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

//@Preview(showBackground = true, widthDp = 320)
//@Composable
//fun GreetingsPreview() {
//    BasicsCodelabTheme {
//        OnboardingScreen()
//    }
//}
//
//@Preview(showBackground = true, widthDp = 320, heightDp = 320)
//@Composable
//fun OnboardingPreview() {
//    BasicsCodelabTheme {
//        OnboardingScreen()
//    }
//}

//@Preview(showBackground = true, name = "Text preview")
//@Composable
//fun DefaultPreview() {
//    BasicsCodelabTheme {
//        MyApp()
//    }
//}