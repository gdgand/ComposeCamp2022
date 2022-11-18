package com.codelabs.basicstatecodelab.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

private fun getWellnessTasks() = List(30) { WellnessTask(id = it, "Task $it") }

@Preview
@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    lists: List<WellnessTask> = remember { getWellnessTasks() }
) {
    LazyColumn(modifier = modifier) {
        items(items = lists) { task ->
            WellnessTaskItem(taskName = task.label)
        }
    }
}