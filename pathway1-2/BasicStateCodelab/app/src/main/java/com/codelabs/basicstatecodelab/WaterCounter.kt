package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var waterCount by remember { mutableStateOf(0) }

    StatelessCounter(waterCount, { waterCount++ })
}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (count > 0) {
//            var showTask by remember { mutableStateOf(true) }
//            if (showTask) {
//                WellnessTaskItem(
//                    taskName = "Have you taken your 15 minute walk today?",
//                    onClose = { showTask = false }
//                )
//            }
            Text(
                text = "You've had $count glasses."
            )
        }
        Button(onClick = { onIncrement }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text(text = "Add one")
        }

//        Row(Modifier.padding(top = 8.dp)) {
//            Button(onClick = { count++ }, enabled = count < 10) {
//                Text(text = "Add one")
//            }
//            Button(onClick = { count = 0 }, Modifier.padding(start = 8.dp)) {
//                Text("Clear water count")
//            }
//        }
    }
}
