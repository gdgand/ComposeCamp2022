package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel() // viewModel()은 기존 ViewModel을 반환하거나 지정된 범위에서 새 ViewModel을 생성합니다.
) {
    Column(modifier = modifier) {
        StatefulCounter()

        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCloseTask = { task -> wellnessViewModel.remove(task) })
    }
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }