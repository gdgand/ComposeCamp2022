package com.codelabs.basicstatecodelab.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if(count > 0) {
            Text("You`ve had $count glasses.")
        }
        Button(onClick = onIncrement, modifier = Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun StatefulCounter() {
    var waterCount by rememberSaveable {
        mutableStateOf(0)
    }
    var juiceCount by rememberSaveable {
        mutableStateOf(0)
    }
    StatelessCounter(count = waterCount, onIncrement = { waterCount++})
    StatelessCounter(count = juiceCount, onIncrement = { juiceCount++})
}
