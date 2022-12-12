package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WellnessTasksList(
    list: List<WellnessTask>,
    onCloseTask: (WellnessTask) -> Unit,
    onCheckedChange: (WellnessTask, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->
            WellnessTaskItem(
                taskName = task.label,
                onClose = { onCloseTask(task) },
                checked = task.checked,
                onCheckedChange = { checked -> onCheckedChange(task, checked) }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun WellnessTaskListPreview() {
//    BasicStateCodelabTheme {
//        val list = remember { getWellnessTasks().toMutableStateList() }
//        WellnessTasksList(
//            list = list,
//            onCloseTask = { task -> list.remove(task) }
//        )
//    }
//}