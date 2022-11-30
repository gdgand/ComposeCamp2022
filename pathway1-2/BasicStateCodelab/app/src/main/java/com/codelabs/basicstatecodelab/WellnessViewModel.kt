package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

/**
 * BasicStateCodelab
 * @author jaesung
 * @created 2022/11/30
 */

private fun getWellnessTasks() = List(30) { i ->
    WellnessTask(i, "Task # $i")
}

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask> get() = _tasks

    fun removeTask(item: WellnessTask) {
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) {
        tasks.find { it.id == item.id }?.let { task ->
            task.checked.value = checked
        }
    }
}