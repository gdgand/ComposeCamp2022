package com.codelabs.basicstatecodelab.ui.theme

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.codelabs.basicstatecodelab.WellnessTaskItem


// 목록을 구성하는 함수
    @Composable
    fun WellnessTasksList(
        list: List<WellnessTask>,
        onCheckedTask: (WellnessTask, Boolean) -> Unit,
        onCloseTask: (WellnessTask) -> Unit,
        modifier: Modifier = Modifier
    ) {
        // LazyColumn으로 미리 정의해둔 Dataclass를 토대로 리스트를 생성.
        LazyColumn(modifier = modifier) {
            items(
                items = list,
                key = {task -> task.id}
            ) { task ->
                WellnessTaskItem(
                    taskName = task.label,
                    checked = task.checked,
                    onCheckedChange = { checked -> onCheckedTask(task, checked) },
                    onClose = { onCloseTask(task) }
                )
            }
        }
    }

