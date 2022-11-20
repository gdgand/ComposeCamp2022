package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        StateFulCounter()

        val list = remember { getWellnessTasks().toMutableStateList() }
        WellnessTaskList(list = list, onClose = {task -> list.remove(task)})
    }
}

private fun getWellnessTasks() = List(30) {
    WellnessTask(it, "Task # $it")
}