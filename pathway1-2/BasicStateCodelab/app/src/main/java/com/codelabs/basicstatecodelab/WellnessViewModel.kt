package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(wellnessTask: WellnessTask) = _tasks.remove(element = wellnessTask)

    fun changeTaskChecked(wellnessTask: WellnessTask, checked: Boolean) =
        tasks.find { it.id == wellnessTask.id }?.let {
            it.checked = checked
        }

    private fun getWellnessTasks() = List(30) { WellnessTask(it, "Task # $it") }
}
