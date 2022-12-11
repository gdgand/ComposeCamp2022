package com.codelabs.basicstatecodelab.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codelabs.basicstatecodelab.StatefulCounter
import com.codelabs.basicstatecodelab.data.WellnessTasksList
import com.codelabs.basicstatecodelab.usecase.WellnessViewModel

@Composable
fun WellnessScreen(modifier: Modifier = Modifier, wellnessViewModel: WellnessViewModel = viewModel()) {
    Column(modifier = Modifier) {
        StatefulCounter()
        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCloseTask = { task -> wellnessViewModel.remove(task)},
        onCheckedTask = {task, checked ->
            wellnessViewModel.changedTaskChecked(task, checked)
        })
    }
}

