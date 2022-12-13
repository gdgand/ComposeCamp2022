package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask>,
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { it.id }
        ) {
            WellnessTaskItem(
                taskName = it.label,
                checked = it.checked,
                onCheckedChange = { checked -> onCheckedTask(it, checked) },
                onClose = { onCloseTask(it) }
            )
        }
    }
}