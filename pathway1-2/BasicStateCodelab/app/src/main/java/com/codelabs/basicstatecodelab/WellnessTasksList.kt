package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private fun getWellnessTasks() = List(30) { i ->
    WellnessTask(i, "Task # $i")
}

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask>,
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->

            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked.value,
                onCheckedChange = {
                    onCheckedTask(task, it)
                },
                onClose = { onCloseTask(task) }
            )
        }
    }
}