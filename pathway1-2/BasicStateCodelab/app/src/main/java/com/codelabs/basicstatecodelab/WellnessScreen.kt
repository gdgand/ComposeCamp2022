package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * @Created by 김현국 2022/12/12
 * @Time 2:57 PM
 */

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task -> wellnessViewModel.remove(task) }
        )
    }
}
