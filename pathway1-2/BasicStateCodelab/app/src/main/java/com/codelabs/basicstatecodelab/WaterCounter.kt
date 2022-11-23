package com.codelabs.basicstatecodelab

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
fun WaterCounter(modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        var count by rememberSaveable { mutableStateOf(0) }
        if (count > 0) {
            Text(text = "You've had $count glasses.")
        }
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = { count++ },
            enabled = count < 10
        ) {
            Text(text = "Add one")
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
            modifier = Modifier.padding(top = 8.dp),
            onClick =  onIncrement,
            enabled = count < 10
        ) {
            Text(text = "Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var waterCount by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count = waterCount, onIncrement = { waterCount++ })
}