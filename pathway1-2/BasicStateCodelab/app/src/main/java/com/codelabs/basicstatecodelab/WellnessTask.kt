package com.codelabs.basicstatecodelab

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class WellnessTask(
    val id: Int,
    val label: String,
    val initialChecked: MutableState<Boolean> = mutableStateOf(false)
) {
    var     checked by mutableStateOf(initialChecked)
}

// fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }
//
// @Composable
// fun WellnessTasksList(
//    modifier: Modifier = Modifier,
//    list: List<WellnessTask> = getWellnessTasks(),
//    onCloseTask: (WellnessTask) -> Unit
// ) {
//    LazyColumn(
//        modifier = modifier
//    ) {
//        items(list) { task ->
//            WellnessTaskItem(taskName = task.label, onClose = { onCloseTask.invoke(task) })
//        }
//    }
// }
