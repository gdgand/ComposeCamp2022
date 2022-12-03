package com.codelabs.basicstatecodelab

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * Created by leeseulbee on 2022/11/11.
 */
private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task #$i")}

@SuppressLint("NotConstructor")
@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask> = remember { getWellnessTasks() },
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask)-> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = rememberLazyListState() // 미쳤다.. Global한 스크롤 상태 유지에 이거면 끝.
    ) {
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = { checked -> onCheckedTask(task, checked )},
                onClose = { onCloseTask(task) })
        }
    }
}