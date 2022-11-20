package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column {
        StatefulCounter(modifier)

        WellnessTaskList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked -> wellnessViewModel.changeTaskChecked(task, checked)},
            onCloseTask = { task -> wellnessViewModel.remove(task) })
    }
}



@Preview(showBackground = true)
@Composable
fun WellnessScreenPreview(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column {
        StatefulCounter(modifier)

        WellnessTaskList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked -> wellnessViewModel.changeTaskChecked(task, checked)},
            onCloseTask = { task -> wellnessViewModel.remove(task) })
    }
}