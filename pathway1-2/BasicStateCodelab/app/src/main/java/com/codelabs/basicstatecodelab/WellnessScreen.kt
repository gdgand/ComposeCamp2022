package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
    ) {
    Column(modifier = modifier){
        StatefulCounter()

        val list = remember {getWellnessTasks().toMutableStateList()}
        WellnessTasksList(list = wellnessViewModel.tasks,
            onCloseTask = {task -> wellnessViewModel.remove(task)},
            onCheckedTask = {task, checked ->
                wellnessViewModel.changeTaskChecked(task,checked)
            }

            )
    }
}

private fun getWellnessTasks() = List(30) {i -> WellnessTask(i, "Task # $i")}

