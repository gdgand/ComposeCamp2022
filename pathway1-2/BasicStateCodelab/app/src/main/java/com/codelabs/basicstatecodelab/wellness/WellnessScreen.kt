package com.codelabs.basicstatecodelab.wellness

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codelabs.basicstatecodelab.component.StatefulCounter

@Composable
fun WellnessScreen(
	modifier: Modifier = Modifier,
	wellnessViewModel: WellnessViewModel = viewModel()
) {
	Column(modifier = modifier) {
		StatefulCounter()

		WellnessTasksList(
			list = wellnessViewModel.tasks,
			onCloseTask = wellnessViewModel::remove,
			onCheckedTask = wellnessViewModel::changeTaskChecked
		)
	}
}