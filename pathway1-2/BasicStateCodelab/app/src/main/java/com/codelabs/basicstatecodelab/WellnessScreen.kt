package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StateFulCounter()

        WellnessTaskList(
            list = wellnessViewModel.task,
            onClose = {task -> wellnessViewModel.remove(task)},
            onCheck = {task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
            }
        )
    }
}