package com.codelabs.basicstatecodelab.ui

import androidx.lifecycle.ViewModel

class WellnessViewModel: ViewModel() {
    private val _tasks = getWellnessTasks().toMutableList()
    val tasks: List<WellnessTask> get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) =
        tasks.find {
            it.id == item.id
        }?.let { task ->
            task.checked = checked
        }
}

fun getWellnessTasks() = List(30) { i -> WellnessTask(i, label = "Task # $i")}
