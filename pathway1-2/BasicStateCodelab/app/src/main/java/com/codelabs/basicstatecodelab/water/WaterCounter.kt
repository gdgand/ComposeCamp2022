package com.codelabs.basicstatecodelab.water

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codelabs.basicstatecodelab.item.WellnessItems
import com.codelabs.basicstatecodelab.viewModel.TaskViewModel

@Composable
private fun WaterCounter(
    count: Int,
    onIncrease: () -> Unit,
    onCleared: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text(text = "You've had $count glasses.")
        }

        Row(Modifier.padding(top = 8.dp)) {
            Button(
                onClick = onIncrease,
                enabled = count < 10
            ) {
                Text(text = "Add one")
            }

            Button(
                onClick = onCleared,
                modifier = modifier.padding(start = 8.dp),
                enabled = count < 10
            ) {
                Text(text = "Clear water count")
            }
        }

    }
}

@Composable
private fun WaterScreen(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    WaterCounter(
        count = count,
        modifier = modifier,
        onIncrease = { count++ },
        onCleared = { count = 0 }
    )
}

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = viewModel()
) {

    Column(modifier = modifier) {
        WaterScreen()
        WellnessItems(
            items = viewModel.tasks,
            onRemove = { item -> viewModel.remove(item) },
            onCheckedChange = { task, checked -> viewModel.changeCheckedState(task, checked) }
        )
    }
}
