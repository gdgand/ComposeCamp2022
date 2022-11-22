package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codelabs.basicstatecodelab.data.WellnessTask
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask>,
    onCloseTask: (WellnessTask) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->
            WellnessTaskItem(taskName = task.label, onClose = {onCloseTask(task)})
        }
    }
}

fun getWellnessTasks() = List(30)  {
    i -> WellnessTask(i, "Task # $i")
}

@Preview
@Composable
fun WellnessTasksListPreview() {
    BasicStateCodelabTheme {
        WellnessTasksList(list = getWellnessTasks(), onCloseTask = {  })
    }
}