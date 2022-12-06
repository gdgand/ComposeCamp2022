package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier



@Composable
fun WellnessTasksList(
    list : List<WellnessTask> = remember{ getWellnessTasks() },
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ){
        items(
            items = list,
            key = {task -> task.id}
        ){ task->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = {checked-> onCheckedTask(task,checked)},
                onClose =  {onCloseTask(task)}
            )
        }
    }
}