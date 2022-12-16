package com.codelabs.basicstatecodelab

import androidx.lifecycle.ViewModel

class WellnessViewModel : ViewModel() {

    private val _task = getWellnessTasks().toMutableList()
    val task: List<WellnessTask>
        get() = _task

    fun remove(task: WellnessTask) {
        _task.remove(task)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) = task.find { it.id == item.id }?.let { task ->
        task.checked = checked
    }

    private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }
}