package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(modifier: Modifier = Modifier,
wellnessViewModel: WellnessViewModel = viewModel()) {
    Column(modifier) {
        StatefulCounter()

        WellnessTasksList(list = wellnessViewModel.tasks, onCloseTask = {
            wellnessViewModel.remove(it)
        }, onCheckedTask = { task, checked ->
            wellnessViewModel.changeTaskChecked(task, checked)
        })
    }
}

