package com.codelabs.basicstatecodelab.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codelabs.basicstatecodelab.WellnessViewModel

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
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
fun StatefulCounter() {
  var count by remember { mutableStateOf(0) }

  StatelessCounter(count, { count++ })
}

@Composable
fun WellnessTaskItem(
  taskName: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  onClose: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier, verticalAlignment = Alignment.CenterVertically
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

data class WellnessTask(val id: Int, val label: String, val checked: MutableState<Boolean> = mutableStateOf(false))
@Composable
fun WellnessTasksList(
  list: List<WellnessTask>,
  onCheckedTask: (WellnessTask, Boolean) -> Unit,
  onCloseTask: (WellnessTask) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
  ) {
    items(
      items = list,
      key = { task -> task.id }
    ) { task ->
      WellnessTaskItem(
        taskName = task.label,
        checked = task.checked,
        onCheckedChange = { checked -> onCheckedTask(task, checked) },
        onClose = { onCloseTask(task) }
      )
    }
  }
}
@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    StatefulCounter()

    val list = remember { getWellnessTasks().toMutableStateList() }
    WellnessTasksList(list = list, onCloseTask = { task -> list.remove(task) })
  }
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }


