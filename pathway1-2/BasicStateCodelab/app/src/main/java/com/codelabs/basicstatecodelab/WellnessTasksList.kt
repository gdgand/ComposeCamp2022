package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * @Description
 * @author yc.park (DEEP.FINE)
 * @since 2022-11-11
 * @version 1.0.0
 */
@Composable
fun WellnessTasksList(
  list: List<WellnessTask>,
  onCheckedTask: (WellnessTask, Boolean) -> Unit,
  onCloseTask: (WellnessTask) -> Unit,
  modifier: Modifier = Modifier,
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