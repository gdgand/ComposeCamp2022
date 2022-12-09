package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    //WaterCounter(modifier = modifier)
    //StatefulCounter(modifier)

    Column(modifier = modifier) {
        StatefulCounter()

        //WellnessTaskItemList()
        //val list = wellnessViewModel.tasks
        WellnessTaskItemList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked -> wellnessViewModel.changeTaskChecked(task, checked) },
            onCloseTask = { task -> wellnessViewModel.remove(task) })
    }
}

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable { mutableStateOf(0) }
        if (count > 0) {
            //var showTask by remember { mutableStateOf(true) }
//            if (showTask) {
//                WellnessTaskItem(
//                    taskName = "Have you taken your 15 minute walk today?",
//                    onClose = { showTask = false })
//            }
            Text(text = "You've had ${count} glasses.")
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {

            Button(
                onClick = { count++ },
                enabled = count < 10
            ) {
                Text(text = "Add one")
            }
//            Button(onClick = { count = 0 }, modifier = Modifier.padding(start = 8.dp)) {
//                Text(text = "Clear water count")
//            }
        }
    }
}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text(text = "You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text(text = "Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var watercount by rememberSaveable {
        mutableStateOf(0)
    }

    var juiceCount by rememberSaveable {
        mutableStateOf(0)
    }
    //Column {
    StatelessCounter(count = watercount, onIncrement = { watercount++ })
//        StatelessCounter(count = watercount, onIncrement = { watercount *= 2 })
//    }

}