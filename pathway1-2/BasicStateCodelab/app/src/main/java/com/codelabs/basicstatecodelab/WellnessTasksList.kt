package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    onCloseTask: (WellnessTask) -> Unit,
    list: List<WellnessTask>
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(list,  key = { task -> task.id }) { task ->
            WellnessTaskItem(taskName = task.label, onClose = { onCloseTask(task)})
        }
    }
}