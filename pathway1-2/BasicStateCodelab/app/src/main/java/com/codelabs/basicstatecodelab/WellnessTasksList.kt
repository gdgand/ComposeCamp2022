package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


@Composable
fun WellnessTasksList(
    list : List<WellnessTask>,
    onCloseTask : (WellnessTask) -> Unit,
    onCheckedTask : (WellnessTask,Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier){
        items(list,
        key = {task -> task.id}) {task ->
            WellnessTaskItem(taskName = task.label,
                checked = task.checked,
                onClose = {onCloseTask(task)},
                onCheckedChange = {checked -> onCheckedTask(task,checked)}
                )
        }
    }
}
private fun getWellnessTasks() = List(30) {i -> WellnessTask(i, "Task # $i")}