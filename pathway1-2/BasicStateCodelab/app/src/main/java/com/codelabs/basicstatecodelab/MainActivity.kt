package com.codelabs.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicStateCodelabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WellnessScreen()
                }
            }
        }
    }
}

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier
) {
    WaterCount(modifier)
}

@Composable
fun WellNessTaskItem(
    modifier: Modifier = Modifier,
    name : String,
    onClose: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null
            )
        }
    }
}

@Composable
fun WaterCount(
    modifier: Modifier = Modifier
) {
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        if (count > 0) {

            var isShowTask by remember { mutableStateOf(true) }

            if (isShowTask) WellNessTaskItem(name = "TASK NAME") { isShowTask = false }

            Text(
                text = "Count : $count",
                modifier = Modifier.padding(16.dp)
            )
        }

        Row(
            Modifier.padding(top = 8.dp)
        ) {

            Button(
                onClick = { count++ },
                enabled = count < 10
            ) {
                Text(text = "Add One")
            }

            Button(
                onClick = { count = 0 },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Clear")
            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicStateCodelabTheme {
        WellnessScreen()
    }
}