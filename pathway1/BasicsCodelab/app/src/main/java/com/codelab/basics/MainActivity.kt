package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme
import java.util.logging.Logger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}

@Composable
//fun Greetings(names: List<String> = listOf("World", "Compose")) {
fun Greetings(names: List<String> = List(1000) { "$it" }) {
    Surface(
//        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
//            for (name in names) {
//                Greeting(name)
//            }
            LazyColumn {
                item { Text("Header") }
                items(names) { name ->
                    Greeting(name)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    var expended by remember { mutableStateOf(false) }
//    val extraPadding = if (expended) 48.dp else 0.dp
    val extraPadding by animateDpAsState(
        targetValue = if (expended) 48.dp else 0.dp,
//        animationSpec = tween(
//            durationMillis = 2000
//        )
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
//                    .padding(bottom = extraPadding)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello,")
//                Text(text = name)
//                Text(text = name, style = MaterialTheme.typography.h4)
                Text(text = name, style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
                if (expended) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }
            }
//            OutlinedButton(onClick = { expended = !expended }) {
//                Text(if (expended) "Show less" else "Show more")
//            }
            IconButton(onClick = { expended = !expended }) {
                Icon(
                    imageVector = if (expended) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expended) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }

                )
            }
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    // TODO: This state should be hoisted

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
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
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Preview(showBackground = true, widthDp = 320, heightDp = 320, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
//        Greeting("Android")
        MyApp()
    }
}