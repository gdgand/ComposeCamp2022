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
    Column(
            modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
            )
    ) {
        names.forEach { Greeting(name = it) }
    }
}

@Composable
fun Greeting(name: String) {
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
                    modifier = Modifier.weight(1f)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(
                    onClick = {}
            ) {
                Text(text = "Show more")
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
