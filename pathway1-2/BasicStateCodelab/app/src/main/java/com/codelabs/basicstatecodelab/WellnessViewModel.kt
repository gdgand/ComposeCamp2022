package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessViewModel: ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val task:List<WellnessTask> = _tasks

    private fun getWellnessTasks() = List(30) {
        WellnessTask(it, "Task # $it")
    }

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) {
        _tasks.find {item.id == it.id}?.let {
            it.checked.value = checked
        }
    }
}