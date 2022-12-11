package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
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
private fun MyApp() {
    val (isOnBoarding, setIsOnBoarding) = rememberSaveable { mutableStateOf(true) }
    if (isOnBoarding) {
        OnBoardingScreen { setIsOnBoarding(false) }
    } else {
        OnGreetingScreen()
    }
}

@Composable
private fun OnBoardingScreen(
    onContinue: () -> Unit = {}
) = Surface {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinue
        ) {
            Text("Continue")
        }
    }
}

@Composable
private fun OnGreetingScreen(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "Name($it)" }
) = LazyColumn(modifier = modifier) {
    items(
        items = names,
        key = { it }
    ) {
        Greeting(name = it)
    }
}

@Composable
private fun Greeting(
    modifier: Modifier = Modifier,
    name: String = ""
) = Surface(
    color = MaterialTheme.colors.primary,
    modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
) {
    val (isExpanded, setExpanded) = rememberSaveable { mutableStateOf(false) }
    val bottomPadding by animateDpAsState(
        targetValue = if (isExpanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Row(modifier = modifier.padding(24.dp)) {
        Column(
            modifier = Modifier
                .weight(1F)
                .padding(bottom = bottomPadding.coerceAtLeast(0.dp))
        ) {
            Text(text = "Hello")
            Text(text = name)
        }
        OutlinedButton(
            onClick = { setExpanded(!isExpanded) }
        ) {
            Text(
                text = if (isExpanded) {
                    "Show Less"
                } else {
                    "Show More"
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() = BasicsCodelabTheme {
    MyApp()
}


@Preview
@Composable
private fun GreetingPreview() = Greeting(name = "Android")