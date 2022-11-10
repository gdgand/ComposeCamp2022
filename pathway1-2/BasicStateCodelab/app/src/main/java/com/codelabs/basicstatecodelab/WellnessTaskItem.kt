package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by JeongJaeHwan on 2022/11/09
 **/

@Composable
fun WellnessTaskItem(
  taskName: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  onClose: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = taskName,
      modifier = Modifier
        .weight(1f)
        .padding(start = 16.dp)
    )

    Checkbox(
      checked = checked,
      onCheckedChange = onCheckedChange
    )

    IconButton(onClick = onClose) {
      Icon(
        imageVector = Icons.Filled.Close,
        contentDescription = "Close"
      )
    }
  }
}

data class WellnessTask(
  val id: Int,
  val label: String,
  val initialChecked: Boolean = false
) {
  var checked by mutableStateOf(initialChecked)
}

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
      key = { item: WellnessTask -> item.id }
    ) { task ->

      WellnessTaskItem(
        taskName = task.label,
        checked = task.checked,
        onCheckedChange = { checked -> onCheckedTask(task, checked) },
        onClose = { onCloseTask(task) },
        modifier = modifier,
      )
    }
  }
}