package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    viewModel: WellnessViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val list = remember { viewModel.tasks }

    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList(
            list = list,
            onCloseTask = { viewModel.remove(it) },
            onCheckedChangeTask = { task, checked ->
                viewModel.changeTaskChecked(task, checked)
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun WellnessScreenPreview() {
    WellnessScreen()
}