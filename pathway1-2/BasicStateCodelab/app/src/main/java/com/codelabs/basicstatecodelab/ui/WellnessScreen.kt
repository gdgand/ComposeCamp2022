package com.codelabs.basicstatecodelab.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import com.codelabs.basicstatecodelab.WellnessViewModel

@Composable
fun WellnessScreen(modifier: Modifier = Modifier, viewModel: WellnessViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Column(modifier = modifier) {
        StatefulCounter(modifier)

        WellnessTaskList(
            list = viewModel.tasks,
            onCloseTask = { task -> viewModel.remove(task) },
            onCheckedTask = { task, checked ->
                viewModel.changeTaskChecked(task, checked)
            }
        )
    }
}
