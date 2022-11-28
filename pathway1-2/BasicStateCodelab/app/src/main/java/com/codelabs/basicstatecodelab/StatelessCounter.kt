package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatelessCounter(count : Int, onIncrement : () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
        if(count > 0) {
            Text(text="물 $count 잔 마셨음.")
        }
        Button(
            onClick = onIncrement,
        modifier = Modifier.padding(top = 8.dp),
        enabled = count < 10) {
            Text(text = "한잔 마시기")
        }
    }
}