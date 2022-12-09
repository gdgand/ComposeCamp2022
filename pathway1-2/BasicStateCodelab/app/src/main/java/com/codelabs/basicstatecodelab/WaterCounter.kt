package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun StatelessWaterCounter(counter: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (counter > 0) {
            Text(
                text = stringResource(id = R.string.water_counter, counter)
            )
        }
        Button(
            modifier = Modifier.padding(top = 8.dp),
            enabled = counter < 10,
            onClick = onIncrement) {
            Text(text = stringResource(id = R.string.water_counter_button_label))
        }
    }
}

@Composable
fun StatefulWaterCounter(modifier: Modifier = Modifier) {
    var counter by rememberSaveable { mutableStateOf(0) }
    StatelessWaterCounter(counter = counter, onIncrement = { counter++ })
}

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var counter by rememberSaveable { mutableStateOf(0) }
        if (counter > 0) {
            Text(
                text = stringResource(id = R.string.water_counter, counter)
            )
        }
        Button(
            modifier = Modifier.padding(top = 8.dp),
            enabled = counter < 10,
            onClick = { counter++ }) {
            Text(text = stringResource(id = R.string.water_counter_button_label))
        }
    }
}