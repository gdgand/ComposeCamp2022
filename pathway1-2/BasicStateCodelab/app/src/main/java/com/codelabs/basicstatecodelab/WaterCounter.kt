package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    var count = 0

    Text(
        text = "You've had $count glasses."
    )
    Button(onClick = { count++ }) {
        Text("Add one")
    }
}

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    WaterCounter(modifier)
}