package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun WellnessTaskList(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    list: List<WellnessTask>,
    onCloseClick: (WellnessTask) -> Unit,
    onCheckedTask: (WellnessTask, Boolean) -> Unit
) {

    LazyColumn(modifier = modifier) {
        items(list) { task ->
            WellnessTaskItem(
                taskName = task.label,
                onClose = { onCloseClick(task) },
                checked = task.checked,
                onCheckedChanged = { checked -> onCheckedTask(task, checked) })
        }
    }
}