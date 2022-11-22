package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StatefulCounter(modifier)

        //긴 직렬화/역직렬화가 필요한 복잡한 데이터 구조나 대량의 데이터를 저장하는 데 rememberSaveable 을 사용하면 안된다.
//        val list = remember { getWellnessTasks().toMutableStateList() }
        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked -> wellnessViewModel.changeTaskChecked(task, checked) },
            onCloseTask = { task -> wellnessViewModel.remove(task) }
        )
    }
}