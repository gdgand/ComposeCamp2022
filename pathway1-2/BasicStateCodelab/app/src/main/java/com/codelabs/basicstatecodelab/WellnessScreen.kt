package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun WellnessScreen(modifier: Modifier = Modifier,
                   wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task -> wellnessViewModel.remove(task)}
        )
    }
}


@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable { mutableStateOf(0) }
        if (count > 0) {
            var showTask by remember { mutableStateOf(true) }
            if (showTask) {
//                WellnessTaskItem(
//                    onClose = {showTask = false},
//                    taskName = "Have you taken your 15 minute walk today?"
//                )
            }
            Text(text = "You've had ${count} glasses.")
        }
        Row (Modifier.padding(top = 8.dp)){
            Button(onClick = { count++ }, enabled = count < 10) {
                Text("Add One")
            }
            Button(onClick = { count = 0 }, Modifier.padding(start = 8.dp)) {
                Text("Clear water count")
            }
        }

    }
}
@Composable
fun StatefulCounter() {
    var count by remember { mutableStateOf(0) }

    StatelessCounter(count, { count++ })
    StatelessCounter(count, { count *= 2 })
}
@Composable
fun StatelessCounter(count:Int, onIncrement: ()->Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glassess.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text(text = "Add one")
        }
    }
}

data class WellnessTask(val id: Int, val label: String, private val initialChecked: Boolean = false) {
    var checked by mutableStateOf(initialChecked)
}

@Composable
fun WellnessTasksList(
    list: List<WellnessTask>,
    onCloseTask: (WellnessTask) -> Unit,
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(list,key = {task -> task.id}) { task ->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onClose = { onCloseTask(task) },
                onCheckedChange = { checked -> onCheckedTask(task, checked)},
                modifier
            )
        }
    }
}
@Composable
fun WellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onClose: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = taskName
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}
class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }
    fun changeTaskChecked(item: WellnessTask, checked: Boolean) =
        tasks.find { it.id == item.id }?.let { task ->
            task.checked = checked
        }
    private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }
}
