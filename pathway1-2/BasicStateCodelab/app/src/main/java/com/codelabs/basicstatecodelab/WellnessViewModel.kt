package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessViewModel : ViewModel() {
    private val _tasks = WellNessTask.getWellNessTasks().toMutableStateList()
    val tasks: List<WellNessTask> get() = _tasks

    fun remove(item: WellNessTask) = _tasks.remove(item)

    fun changeTaskChecked(item: WellNessTask, isCheck: Boolean) {
        tasks.find { it.id == item.id }?.let { task ->
            task.isCheck.value = isCheck
        }
    }
}