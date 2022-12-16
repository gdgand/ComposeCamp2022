package com.bean.compose.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun WellnessTasksList(
//    list: List<WellnessTask> = remember { getWellnessTasks() } // recomposition이 발생해도 다시 getWellnessTaskss를 실행 안함, 네트워크 통신이라고 생각해보셈
    list: List<WellnessTask>,
    onCloseTask: (WellnessTask) -> Unit,
    onCheckChanged: (WellnessTask, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        //TODO: list에서 key가 하는 역할은 무엇인가? key가 적절히 제공되지 않을 때 차이는?
        // same id와 same content의 차이는 무엇인가?
        items(items = list, key = { task -> task.id }) { task ->
            // TaskItem은 WellnessTask를 몰라도 상관없다.
            WellnessTaskItem(
                taskName = task.label,
                onClose = { onCloseTask(task) },
                checked = task.checked,
                onCheckChanged = { checked -> onCheckChanged(task, checked) }
            )
        }
    }
}