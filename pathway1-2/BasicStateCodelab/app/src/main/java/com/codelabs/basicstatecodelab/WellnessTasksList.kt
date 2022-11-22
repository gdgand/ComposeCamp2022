package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

private fun getWellnessTasks() = List(30) {
    WellnessTask(it, "Task #$it")
}

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    tasksList: List<WellnessTask> = remember { getWellnessTasks() }
) {
    LazyColumn(modifier = modifier) {
        items(tasksList) {
            WellnessTaskItem(taskName = it.label)
        }
    }
}