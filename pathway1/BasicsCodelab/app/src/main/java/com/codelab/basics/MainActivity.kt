package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
    val expanded = remember { mutableStateOf(false) }
    val extraPadding = if (expanded.value) 48.dp else 0.dp
    Surface(
        color = colors.primary,
        modifier = Modifier.padding(4.dp, 4.dp)
    ) {
        Row(Modifier.padding(24.dp, 24.dp, 24.dp, 24.dp + extraPadding)) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "Hi,")
                Text(text = name)
            }
            Button(onClick = { expanded.value = !expanded.value }) {
                Text(text = if (expanded.value) "Show Less" else "Show More")
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("Android", "compose")
) {
    Surface(
        modifier = modifier.padding(4.dp),
        color = colors.background
    ) {
        Column {
            names.forEach {
                MyGreeting(name = it)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp()
    }
}