package com.bean.compose.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    // ViewModels are recommended to be used at screen-level composables
    viewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList(
            list = viewModel.tasks,
            onCloseTask = { task -> viewModel.remove(task) },
            onCheckChanged = { task, checked -> viewModel.check(task, checked)}
        )
    }
}

