package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text(text = "You've has $count glasses")
        }

        Button(
            onClick = { onIncrement() },
            modifier = Modifier.padding(top = 8.dp),
            enabled = count < 10
        ) {
            Text(text = "Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    //var juiceCount by rememberSaveable { mutableStateOf(0) }

    StatelessCounter(count = count, onIncrement = { count++ }, modifier = modifier)
    //StatelessCounter(count = juiceCount, onIncrement = { juiceCount++ }, modifier = modifier)
}