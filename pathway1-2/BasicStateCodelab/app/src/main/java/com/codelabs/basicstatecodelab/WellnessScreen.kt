package com.codelabs.basicstatecodelab

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        StatefulCounter()

        val list = remember { getWellnessTasks().toMutableStateList() }
        WellnessTasksList(list = list, onCloseTask = { task -> list.remove(task) })
    }
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }