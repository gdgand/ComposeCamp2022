package com.codelabs.basicstatecodelab.water

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
private fun WaterCounter(modifier: Modifier = Modifier) {
    var count = 0
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "You've had $count glasses.")
        Button(onClick = { count ++ }, modifier = modifier.padding(top = 8.dp)) {
            Text(text = "Add one")
        }
    }
}

@Composable
fun WaterScreen(modifier: Modifier = Modifier) {
    WaterCounter(modifier = modifier)
}
