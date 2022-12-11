package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

private fun getWellnessTasks() = List(30) { i -> _root_ide_package_.com.codelabs.basicstatecodelab.WellnessTask(i, "Task # $i") }

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    onCheckedTask: (_root_ide_package_.com.codelabs.basicstatecodelab.WellnessTask, Boolean) -> Unit,
    onCloseTask: (_root_ide_package_.com.codelabs.basicstatecodelab.WellnessTask) -> Unit,
    list: List<_root_ide_package_.com.codelabs.basicstatecodelab.WellnessTask> = remember { getWellnessTasks() }
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = list,
            key = { task -> task.id}
        ) { task ->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = { checked -> onCheckedTask(task, checked) },
                onClose = { onCloseTask(task)})
        }
    }
}