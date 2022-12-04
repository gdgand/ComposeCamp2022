package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    wellnessViewModel: WellnessViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    Column {
        StatefulCounter()

//        val list = remember { getWellnessTasks().toMutableStateList() }
//        val list = remember {
//            mutableStateListOf<WellnessTask>().apply {
//                addAll(getWellnessTasks())
//            }
//        }
        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCloseTask = { task -> wellnessViewModel.remove(task) }
        )
    }
}

//private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }