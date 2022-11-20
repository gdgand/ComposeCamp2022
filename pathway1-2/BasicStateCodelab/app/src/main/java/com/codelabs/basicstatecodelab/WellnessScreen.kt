package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(width = 300.dp, height = 100.dp)) {
        Column(modifier = modifier.padding(16.dp)) {
            if (count > 0) {
                Text("You've had $count glasses.")
            }
            Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
                Text("Add one")
            }
        }
    }
}
