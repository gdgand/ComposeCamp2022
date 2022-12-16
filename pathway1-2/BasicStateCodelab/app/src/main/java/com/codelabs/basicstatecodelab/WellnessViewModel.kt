package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun removeTask(task: WellnessTask) {
        _tasks.remove(task)
    }

    fun checkChanged(task: WellnessTask, isChecked: Boolean) {
        val newTask = task.copy(isChecked = isChecked)
        _tasks.indexOf(task)
            .let { index ->
                _tasks[index] = newTask
            }
    }

    private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }
}
