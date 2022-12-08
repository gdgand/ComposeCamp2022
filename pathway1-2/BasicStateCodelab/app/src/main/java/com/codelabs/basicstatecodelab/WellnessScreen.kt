package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel(),
) {
//    WaterCounter(modifier = modifier)
//    StatefulCounter(modifier = modifier)
//    StatefulCounter()
    Column(modifier = modifier) {
        StatefulCounter(modifier = modifier)

        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCloseTask = { task -> wellnessViewModel.remove(task) },
            onCheckedTask = { task, checked ->
                wellnessViewModel.changedTaskChecked(task, checked)
            }
        )
    }
}

private fun getWellnessTasks() = List(30) { i ->
    WellnessTask(i, "Task # $i")
}