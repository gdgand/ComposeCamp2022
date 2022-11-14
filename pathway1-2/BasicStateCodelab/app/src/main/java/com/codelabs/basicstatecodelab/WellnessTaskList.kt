package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codelabs.basicstatecodelab.data.WellnessTask

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    onCloseTask: (WellnessTask) -> Unit,
    list: List<WellnessTask>
) {
    LazyColumn(modifier = modifier, state = rememberLazyListState()) {
        items(items = list, key = { task -> task.id }) { task ->
            WellnessTaskItem(
                taskName = task.label
            ) { onCloseTask(task) }
        }
    }
}