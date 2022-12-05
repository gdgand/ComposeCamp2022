package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList()
    }
    

}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    val counter = rememberSaveable { mutableStateOf(0) }
    WaterCounter(
        count = counter.value,
        onIncrement = { counter.value++ })
}