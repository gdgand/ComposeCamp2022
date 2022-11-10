package com.codelabs.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items

/**
 * @Description
 * @author yc.park (DEEP.FINE)
 * @since 2022-11-11
 * @version 1.0.0
 */
private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }

@Composable
fun WellnessTasksList(
  modifier: Modifier = Modifier,
  list: List<WellnessTask> = remember { getWellnessTasks() }
) {
  LazyColumn(
    modifier = modifier
  ) {
    items(list) { task ->
      WellnessTaskItem(taskName = task.label)
    }
  }
}