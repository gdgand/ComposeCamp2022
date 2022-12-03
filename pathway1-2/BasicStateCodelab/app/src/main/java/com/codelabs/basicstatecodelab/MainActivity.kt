package com.codelabs.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WaterCounter()
                }
            }
        }
    }
}

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable {
            mutableStateOf(0)
        }

        var showTask by remember {
            mutableStateOf(true)
        }

        if (count > 0) {
//            var showTask by remember { // count 값이 0 이하면 삭제되어 넘을 때 다시 만들어짐
//                mutableStateOf(true)
//            }
            if (showTask) {
                WellnessTaskItem(taskName = "15 walk today?", onClose = { showTask = false })
            }
            Text(text = "current count : $count")
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {

            Button(
                onClick = { count++ },
                enabled = count < 10
            ) {
                Text(text = "count up")
            }

            Button(onClick = { count = 0 }) {
                Text(text = "clear count")
            }
        }

    }
}

@Composable
fun WellnessTaskItem(
    taskName: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = taskName, modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        IconButton(onClick = onClose) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = null)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicStateCodelabTheme {
        WaterCounter()
    }
}