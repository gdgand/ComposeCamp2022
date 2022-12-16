package com.codelabs.basicstatecodelab

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
            var showTask by rememberSaveable { mutableStateOf(true) }
            if (showTask) {

                WellnessTaskItem(
                    taskName = "Have you taken your 15 minute walk today?",
                    checked = false,
                    onCheckedChange = { checked ->

                    },
                    onClose = {
                        // 닫을때 할 행동
                        showTask = false
                    })
            }
            Text(
                text = "You've had $count galsess",
                modifier = modifier.padding(16.dp)
            )
        }

        Row(modifier = Modifier.padding(8.dp)) {
            Button(
                modifier = Modifier.padding(top = 8.dp),
                onClick = {
                    count++
                },
                enabled = count < 10
            ) {
                Text("Add One")
            }
            Button(
                onClick = {
                    count = 0
                },
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
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
            Text("Add One")
        }
    }
}

@Composable
fun StatefulCounter(
    modifier: Modifier = Modifier
) {
    var count by rememberSaveable {
        mutableStateOf(0)
    }
    StatelessCounter(count = count, onIncrement = { count++ }, modifier = modifier)
}