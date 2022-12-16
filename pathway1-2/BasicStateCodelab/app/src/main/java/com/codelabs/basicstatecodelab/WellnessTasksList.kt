package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask> = remember { getWellnessTasks() },
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = rememberLazyListState()
    ) {
        items(
            key = { task -> task.id },
            items = list
        ) { task ->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = { checked ->
                    onCheckedTask.invoke(task, checked)
                },
                onClose = {
                    onCloseTask(task)
                }
            )
        }
    }
}

internal fun getWellnessTasks() = List(size = 30) { i ->
    WellnessTask(
        id = i,
        label = "Task # $i"
    )
}