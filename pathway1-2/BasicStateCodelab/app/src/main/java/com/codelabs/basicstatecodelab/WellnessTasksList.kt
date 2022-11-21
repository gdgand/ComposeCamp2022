package com.codelabs.basicstatecodelab.data

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codelabs.basicstatecodelab.WellnessTaskItem
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask> = remember { getWellnessTasks() }
) {
    LazyColumn(modifier = modifier) {
        items(list) { task ->
            WellnessTaskItem(taskName = task.label)
        }
    }
}

private fun getWellnessTasks() = List(30)  {
    i -> WellnessTask(i, "Task # $i")
}

@Preview
@Composable
fun WellnessTasksListPreview() {
    BasicStateCodelabTheme {
        WellnessTasksList()
    }
}