package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
        var count by remember { mutableStateOf(0) }
        if (count > 0) {
            var showTask by remember { mutableStateOf(true) }
            if (showTask) {
//                WellnessTaskItem(
//                    onClose = { },
//                    taskName = "Have you taken your 15 minute walk today?"
//                )
            }
            Text("You've had $count glasses.")
        }

        Button(onClick = { count++ }, enabled = count < 10) {
            Text("Add one")
        }
    }
}