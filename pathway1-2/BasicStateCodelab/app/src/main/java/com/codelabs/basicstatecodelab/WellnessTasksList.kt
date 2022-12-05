package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private fun getWellnessTasks() = List(30) {i -> WellnessTask(id = i, label = "Task # $i")}

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list : List<WellnessTask>,
    onCloseTask : (WellnessTask) -> Unit,
    onCheckedTask: (WellnessTask, Boolean) -> Unit
){
    LazyColumn(modifier = modifier){
        items(
            items = list,
            key = {task -> task.id}
            ) { task ->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onClose = {onCloseTask(task)},
                onCheckedChange = {checked -> onCheckedTask(task, checked)}
            )
        }
    }
}