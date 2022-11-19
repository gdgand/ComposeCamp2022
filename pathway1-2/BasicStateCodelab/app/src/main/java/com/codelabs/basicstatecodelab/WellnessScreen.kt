package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    StatefulCounter(modifier)

    val list = remember { getWellnessTasks().toMutableStateList() }
    WellnessTaskList(list = list, onCloseTask = { task -> list.remove(task) })
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i")}

@Preview(showBackground = true)
@Composable
fun WellnessScreenPreview(modifier: Modifier = Modifier) {
    Column {
        StatefulCounter()

        val list = remember { getWellnessTasks().toMutableStateList() }
        WellnessTaskList(list = list, onCloseTask = { task -> list.remove(task) })
    }
}