package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) =
        tasks.find {
            it.id == item.id
        }?.let { task ->
            task.checked = checked
        }

    fun remove(item: WellnessTask): Boolean {
        return _tasks.remove(item)
    }
}