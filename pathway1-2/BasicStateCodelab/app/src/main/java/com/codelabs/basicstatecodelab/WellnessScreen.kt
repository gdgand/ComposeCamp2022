package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    viewModel: WellnessViewModel = viewModel()
) {
//    WaterCounter(modifier)
//    StatefulCounter(modifier)
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList(list = viewModel.tasks,
            onCheckedTask = { task, checked ->
                viewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task -> viewModel.remove(task) })
    }
}