package com.codelabs.basicstatecodelab.wellness

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask> get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }

    fun updateTaskChecked(item: WellnessTask, checked: Boolean){
        val index = _tasks.indexOf(item)
        if(index == -1) { return }
        _tasks[index] = item.copy(checked = checked)
    }
}