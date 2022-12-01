package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun WellnessTaskList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask> = remember { getWellnessTasks() }
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(list) {
            item -> WellnessTaskItem(taskName = item.label)
        }
    }
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }