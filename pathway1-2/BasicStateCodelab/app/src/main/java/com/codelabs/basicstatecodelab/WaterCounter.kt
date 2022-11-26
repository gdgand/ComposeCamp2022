package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    var count by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        if (count > 0) {
            var showTask by remember {
                mutableStateOf(true)
            }
            if (showTask) {
                WellnessTaskItem(
                    taskName = "Have you taken your 15 minute walk today?",
                    onClose = { showTask = false })
            }
            Text(
                "You have $count glasses",
            )
        }

        Row(Modifier.padding(top = 8.dp)) {
            Button(
                onClick = { count += 1 },
                enabled = count < 10
            ) {
                Text(text = "Add one")
            }
            Button(onClick = { count = 0 }, Modifier.padding(start = 8.dp)) {
                Text("Clear water count")
            }
        }
    }
}