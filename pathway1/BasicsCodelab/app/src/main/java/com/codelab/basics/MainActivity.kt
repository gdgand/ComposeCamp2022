package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
private fun MyApp(names: List<String> = listOf("World", "Compose")) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            key(name) {
                Greeting(name = name)
            }
        }
    }
}

@Composable
private fun Greeting(name: String) {
    val (expanded, onExpandedChange) = remember { mutableStateOf(false) }
    val extraPadding by remember(expanded) { derivedStateOf { if (expanded) 48.dp else 0.dp } }
    val buttonText by remember(expanded) { derivedStateOf { if (expanded) "Show less" else "Show more" } }

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { onExpandedChange(!expanded) }
            ) {
                Text(text = buttonText)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp()
    }
}