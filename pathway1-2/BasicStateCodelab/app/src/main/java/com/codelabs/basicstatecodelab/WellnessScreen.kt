package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    wellnessViewModel: WellnessViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

//    StatefulCounter()
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCloseTask = { task -> wellnessViewModel.remove(task) },
            onCheckedTask = { item, checked ->
                wellnessViewModel.changeTaskChecked(item = item, checked = checked)
            }
        )
    }
}