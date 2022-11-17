package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask>,
    onClose: (WellnessTask) -> Unit,
    onCheckedChange: (WellnessTask, Boolean) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(list, key = { it.id }) { wellnessTask ->
            WellnessTaskItem(
                taskName = wellnessTask.label,
                isChecked = wellnessTask.isChecked,
                onClose = { onClose(wellnessTask) },
                onCheckedChange = { isChecked -> onCheckedChange(wellnessTask, isChecked) }
            )
        }
    }
}
