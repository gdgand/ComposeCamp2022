package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WaterScreen(
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column {
        WaterCounter()
        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onClose = wellnessViewModel::remove,
            onCheckedChange = wellnessViewModel::changeTaskChecked
        )
    }
}


@Composable
fun WaterCounter(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by remember { mutableStateOf(0) }
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}


@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count, { count++ }, modifier)
}


@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask> = remember { List(30) { i -> WellnessTask(i, "Task # $i") } },
    onClose: (WellnessTask) -> Unit,
    onCheckedChange: (WellnessTask, Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(list) { task ->
            WellnessTaskItem(taskName = task, onClose = onClose, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
fun WellnessTaskItem(
    taskName: WellnessTask,
    modifier: Modifier = Modifier,
    onClose: (WellnessTask) -> Unit,
    onCheckedChange: (WellnessTask, Boolean) -> Unit

) {
    WellnessTaskItem(
        wellnessTask = taskName,
        checked = taskName.checked,
        onCheckedChange = { newValue -> onCheckedChange(taskName, newValue) },
        onClose = onClose, // we will implement this later!
        modifier = modifier,
    )
}

@Composable
fun WellnessTaskItem(
    wellnessTask: WellnessTask,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: (WellnessTask) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = wellnessTask.label
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = { onClose(wellnessTask) }) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}