package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(task: WellnessTask) {
        _tasks.remove(task)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) {
        tasks.find { it.id == item.id }?.checked = checked
    }
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }