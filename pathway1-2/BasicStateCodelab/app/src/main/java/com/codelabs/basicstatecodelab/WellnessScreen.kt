package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier

@Composable
fun WellnessScreen(modifier: Modifier = Modifier, wellnessViewModel: WellnessViewModel) {
//    WaterCounter(modifier = modifier)
    Column(modifier = modifier) {
        StatefulCounter(modifier)
        val list =  wellnessViewModel.tasks
        WellnessTasksList(
            list = list,
            onCloseTask = { task -> wellnessViewModel.remove(task)},
            onCheckedTask = {task,checked -> wellnessViewModel.changeTaskChanged(task,checked)}
        )
    }

}
