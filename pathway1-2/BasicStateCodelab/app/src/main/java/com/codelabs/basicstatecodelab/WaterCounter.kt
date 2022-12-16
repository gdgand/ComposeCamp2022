package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatefulCounter(modifier: Modifier) {
    var counter by remember { mutableStateOf(0) }
    var showTask by remember { mutableStateOf(true) }

    StatelessCounter(
        modifier = modifier,
        count = counter,
        onClickCount = { counter++ },
        showTask = showTask,
        onClearWaterCount = { counter = 0 })
}

@Composable
fun StatelessCounter(
    modifier: Modifier,
    count: Int,
    onClickCount: () -> Unit,
    showTask: Boolean,
    onClearWaterCount: () -> Unit,
) {
    Column(modifier = modifier) {
        if (count > 0) {
            Text(text = "You have $count glasses")
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onClickCount() },
                Modifier.padding(top = 8.dp),
                enabled = count < 10
            ) {
                Text(text = "Add one")
            }
            Button(onClick = { onClearWaterCount() }, Modifier.padding(top = 8.dp)) {
                Text(text = "Clear water count")
            }
        }

    }
}