package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier


fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }.toMutableStateList()

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    onCloseTask: (WellnessTask) -> Unit,
    list: MutableList<WellnessTask> = remember { getWellnessTasks() }
) {

    LazyColumn(modifier = modifier) {
        items(items = list, key = { task -> task.id }) { task ->
            WellnessTaskItem(taskName = task.label, onClose = { onCloseTask(task) })
        }
    }
}