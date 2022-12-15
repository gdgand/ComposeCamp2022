package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel


class WellnessViewModel : ViewModel() {
    val tasks: List<WellnessTask>
        get() = _tasks
    private val _tasks = getWellnessTasks().toMutableStateList()

    fun setTaskChecked(task: WellnessTask, checked: Boolean) = tasks.find {
        it.id == task.id
    }?.let {
        it.checked = checked
    }

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }

    private fun getWellnessTasks() = List(30) {
        WellnessTask(it, "Task #$it")
    }
}