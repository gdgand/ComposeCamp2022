package com.codelab.basics

import android.content.res.Configuration.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

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
fun MyGreeting(name: String) {
    Surface(
        color = colors.primary,
        modifier = Modifier.padding(4.dp, 4.dp)
    ) {
        Card(elevation = 10.dp) {
            MyCard(name = name)
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    if (shouldShowOnboarding) {
        OnboardingScreen() { shouldShowOnboarding = false }
    } else {
        Greetings()
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(100) { "${it.plus(1)}" }
) {
    LazyColumn(modifier.padding(4.dp)) {
        items(names) {
            MyGreeting(name = it)
        }
    }
}

@Composable
fun MyCard(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }
//    val extraPadding = if (expanded.value) 48.dp else 0.dp
//    val extraPadding by animateDpAsState(
//        if (expanded) 48.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )

    Column(modifier = Modifier
        .padding(24.dp)
        .animateContentSize(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )) {
        Row() {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "Hi,")
                Text(text = name, style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore, null)
            }
        }
        if (expanded) {
            Text(text = "asdfasdfadsf".repeat(10))
        }
    }
}

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier, onClickContinue: ()->Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onClickContinue
        ) {
            Text("Continue")
        }
    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyCard(name = "asdf")
    }
}