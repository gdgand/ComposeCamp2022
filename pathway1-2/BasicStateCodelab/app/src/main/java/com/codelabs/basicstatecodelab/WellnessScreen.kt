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

        // rememberSaveable()을 사용한다면 런타임 에러 발생
        // cannot be saved using the current SaveableStateRegistry.
        // The default implementation only supports types which can be stored inside the Bundle.
        // Please consider implementing a custom Saver for this class and pass it to rememberSaveable().
        // -> 긴 직렬화 or 역직렬화가 필요한 복잡한 데이터 구조 or 대량의 데이터는 사용하면 안됨
        //
        // val list = remember { getWellnessTasks().toMutableStateList() }

        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCloseTask = { task -> wellnessViewModel.remove(task) })
    }
}

