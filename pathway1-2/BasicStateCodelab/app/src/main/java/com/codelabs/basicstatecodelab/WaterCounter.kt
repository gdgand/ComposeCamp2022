package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var waterCount by rememberSaveable { mutableStateOf(0) }
    var juiceCount by rememberSaveable { mutableStateOf(0) }

    StatelessCounter(
        count = waterCount,
        onIncrement = { waterCount++ },
        modifier = modifier)
//    StatelessCounter(
//        count = juiceCount,
//        onIncrement = { juiceCount++ },
//        modifier = modifier
//    )
}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable { mutableStateOf(0) }

        if(count > 0) {
            WellnessTaskItem(
                task = WellnessTask(
                    id = 0,
                    label = "$count Have you taken your 15 minute walk today?"
                ),
                onClose = { },
                onCheckedChange = { task, checked -> },
                modifier = Modifier
            )
        }

        Row(modifier = Modifier.padding(top = 8.dp)) {
            Button(
                onClick = { count += 1 },
                enabled = count < 10
            ) {
                Text("Add one")
            }
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = { count = 0 },
            ) {
                Text("Clear water count")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun WaterCounterPreview() {
    WaterCounter()
}