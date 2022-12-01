package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    StatefulCounter(modifier)
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
fun StatefulCounter(modifier: Modifier = Modifier) {
    var waterCount by remember { mutableStateOf(0) }

    var juiceCount by remember { mutableStateOf(0) }

    StatelessCounter(waterCount, { waterCount++ }, modifier)
    StatelessCounter(juiceCount, { juiceCount++ }, modifier)
}