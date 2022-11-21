package com.codelabs.basicstatecodelab

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier : Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable { mutableStateOf(0) }
        var showTask by remember { mutableStateOf(true) }
        Log.i("Dukoo", "recomposition $count")
        if (count > 0) {
            if (showTask) {
                WellnessTaskItem(
                    onClose = { showTask = false },
                    taskName = "Have you taken your 15 minutes walk today?"
                )
            }
            Text("You've had $count glasses.")
        }
        Row (
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Button(
                onClick = {
                    ++count
                },
                enabled = count < 10
            ) {
                Text("Add one")
            }
            Button(
                onClick = {
                    count = 0
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Clear water count")
            }
        }
    }
}
