package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
        wellnessViewModel: WellnessViewModel = viewModel(),
        modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        StatefulCounter()

        WellnessTasksList(
                list = wellnessViewModel.tasks,
                onCheckedTask = { task, checked ->
                    wellnessViewModel.changeTaskChecked(task, checked)
                },
                onCloseTask = { task ->
                    wellnessViewModel.remove(task)
                }
        )
    }
}