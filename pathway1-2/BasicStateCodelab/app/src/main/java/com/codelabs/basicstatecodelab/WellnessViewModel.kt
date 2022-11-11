package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

/**
 * @Description
 * @author yc.park (DEEP.FINE)
 * @since 2022-11-11
 * @version 1.0.0
 */
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
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }