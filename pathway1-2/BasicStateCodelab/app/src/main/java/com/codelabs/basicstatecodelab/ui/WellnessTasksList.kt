package com.codelabs.basicstatecodelab.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

fun getWellnessTasks() = List(30) { WellnessTask(id = it, "Task $it") }

@Composable
fun WellnessTasksList(
    onCloseTask: (WellnessTask) -> Unit,
    lists: List<WellnessTask>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(items = lists, key = { task -> task.id }) { task ->
            WellnessTaskItem(
                taskName = task.label,
                onClose = { onCloseTask(task) }
            )
        }
    }
}