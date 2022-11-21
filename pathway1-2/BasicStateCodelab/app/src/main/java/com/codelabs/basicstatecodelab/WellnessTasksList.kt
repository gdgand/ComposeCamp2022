package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask>,
    onCloseTask: (WellnessTask) -> Unit,
    onCheckedTask: (WellnessTask, Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = list,
            key = {
                task -> task.id
            }
        ) { item ->
            WellnessTaskItem(
                taskName = item.label,
                checked = item.checked,
                onCheckedChange = {
                    onCheckedTask(item, it)
                },
                onClose = {
                    onCloseTask(item)
                },
            )
        }
    }
}
