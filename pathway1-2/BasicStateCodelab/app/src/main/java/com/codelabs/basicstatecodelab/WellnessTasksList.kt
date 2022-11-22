package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }

@Composable
fun WellnessTasksList(
    list: List<WellnessTask>,
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Lazy Column 내부
    // 구성 가능한 함수 rememberLazyListState 는 rememberSaveable 을 사용하여 목록의 초기 상태를 만듬
    // 활동이 다시 생성되면 스크롤 상태는 아무런 코딩을 하지 않아도 유지됨
    // 지연 구성요소의 경우 이 LazyListState 를 끌어 올려 이 사용 사례를 지원
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = { checked -> onCheckedTask(task, checked)},
                onClose = { onCloseTask(task) }
            )
        }
    }
}