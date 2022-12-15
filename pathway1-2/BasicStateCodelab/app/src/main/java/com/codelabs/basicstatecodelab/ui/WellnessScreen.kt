package com.codelabs.basicstatecodelab.ui

import WellnessViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codelabs.basicstatecodelab.data.WellnessTask
import com.codelabs.basicstatecodelab.data.WellnessTasksList

/*// Don't do this!

val list = remember { mutableStateListOf<WellnessTask>() }

list.addAll(getWellnessTasks())

대신 단일 작업으로 초깃값을 사용하여 목록을 만든 후 다음과 같이 remember 함수에 전달합니다.

// Do this instead. Don't need to copy

val list = remember {

mutableStateListOf<WellnessTask>().apply { addAll(getWellnessTasks()) }

}*/
@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()) {
    Column(modifier = modifier) {
        StatefulCounter()

        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
            },

            onCloseTask = { task -> wellnessViewModel.remove(task) })
    }
}