package com.codelabs.basicstatecodelab.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable { mutableStateOf(0) }

        if (count > 0) {
//            var showTask by remember { mutableStateOf(true) }
//            if (showTask) {
//                WellnessTaskItem(
//                    taskName = "Have you take your 15 minute walk today?",
//                    onClose = { showTask = false }
//                )
//            }
            Text(text = "You've had $count glasses.")
        }

        Row(Modifier.padding(top = 8.dp)) {
            Button(
                onClick = { count++ },
                enabled = count < 10
            ) {
                Text("Add one")
            }
            Button(
                onClick = { count = 0 },
                Modifier.padding(start = 8.dp)
            ) {
                Text("Clear water count")
            }
        }
    }
}

@Composable
fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(
            onClick = onIncrement,
            modifier = Modifier.padding(top = 8.dp),
            enabled = count < 10
        ) {
            Text("Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(
        count = count,
        onIncrement = { count++ },
        modifier = modifier
    )

//    var waterCount by rememberSaveable { mutableStateOf(0) }
//    StatelessCounter(
//        count = waterCount,
//        onIncrement = { waterCount++ }
//    )
//
//    var juiceCount by rememberSaveable { mutableStateOf(0) }
//    StatelessCounter(
//        count = juiceCount,
//        onIncrement = { juiceCount++ }
//    )
}