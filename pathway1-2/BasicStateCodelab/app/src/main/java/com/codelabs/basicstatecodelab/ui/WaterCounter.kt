package com.codelabs.basicstatecodelab.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        val count: MutableState<Int> = mutableStateOf(0)
        Text(text = "You've had $count glasses.")
        Button(onClick = { count.value++ },  Modifier.padding(top = 8.dp)) {
            Text("Add one")
        }
    }
}