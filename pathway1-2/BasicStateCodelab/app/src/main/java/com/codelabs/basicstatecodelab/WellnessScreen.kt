package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier

/**
 * 참고: 모든 Composable 함수에 기본 Modifier를 제공하는 것이 좋습니다.
 * 재사용성이 높아지기 때문입니다. 모든 필수 매개변수 다음에 매개변수 목록의 첫 번째 선택적 매개변수로 표시되어야 합니다.
 */
@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        StatefulCounter(modifier)

        val list = remember { getWellnessTasks().toMutableStateList() }
        WellnessTasksList(
            list = list,
            onCloseTask = { task -> list.remove(task)}
        )
    }
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i")}