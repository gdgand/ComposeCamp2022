package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask> = remember { getWellnessTasks() },
    onCloseTask: (WellnessTask) -> Unit,
    onCheckedTask: (WellnessTask, Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = rememberLazyListState()
    ) {
        items(list) { task ->
            WellnessTaskItem(
                taskName = task.label,
                onClose = { onCloseTask(task) },
                checked = task.checked,
                onCheckedChange = { checked -> onCheckedTask(task, checked) },
                modifier = modifier
            )
        }
    }
}


//data class WellnessTask(val id: Int, val label: String, var checked: MutableState<Boolean> = mutableStateOf(false))
class WellnessTask(
    val id: Int,
    val label: String,
    initialChecked: Boolean = false){
    var checked by mutableStateOf(initialChecked)
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }