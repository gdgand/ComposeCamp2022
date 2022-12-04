package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
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
            onCheckedTask = { task, checked -> wellnessViewModel.changeTaskChanged(task, checked) },
            onCloseTask = { task -> wellnessViewModel.remove(task) })
    }
}