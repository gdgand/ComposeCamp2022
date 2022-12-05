package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StatefulCounter()

        val list = wellnessViewModel.tasks
        WellnessTasksList(
            list = list,
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = {
                    task -> wellnessViewModel.remove(task)
            }
        )
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    val counter = rememberSaveable { mutableStateOf(0) }
    WaterCounter(
        count = counter.value,
        onIncrement = { counter.value++ })
}

private fun getWellnessTasks() = List(30) { i ->
    WellnessTask(i, "Task #${i}")
}