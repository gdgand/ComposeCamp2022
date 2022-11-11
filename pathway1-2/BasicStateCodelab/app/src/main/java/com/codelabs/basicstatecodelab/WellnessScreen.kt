package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * @Description
 * @author yc.park (DEEP.FINE)
 * @since 2022-11-11
 * @version 1.0.0
 */

@Composable
fun WellnessScreen(
  modifier: Modifier = Modifier,
  wellnessViewModel: WellnessViewModel = viewModel()
) {
  Column(modifier = modifier) {
    StatefulCounter()

    WellnessTasksList(
      list = wellnessViewModel.tasks,
      onCheckedTask = { task, checked ->
        wellnessViewModel.changeTaskChecked(task, checked)
      },
      onCloseTask = { task ->
        wellnessViewModel.remove(task)
      }
    )
  }
}