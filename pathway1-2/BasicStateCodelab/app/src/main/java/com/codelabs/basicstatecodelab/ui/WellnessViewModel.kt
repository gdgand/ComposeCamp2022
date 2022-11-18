package com.codelabs.basicstatecodelab.ui

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessViewModel : ViewModel() {
    private fun getWellnessTasks() = List(30) { WellnessTask(id = it, "Task $it") }

    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(task: WellnessTask) {
        _tasks.remove(task)
    }

    fun changeTaskChecked(wellnessTask: WellnessTask, checked: Boolean) =
        tasks.find { it.id == wellnessTask.id }?.let { task ->
            task.checked = checked
        }
}