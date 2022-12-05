package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessViewModel : ViewModel() {
    private val _task = getWellnessTasks().toMutableStateList()
    val task : List<WellnessTask> get() = _task
    private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }

    fun remove(wellnessTask: WellnessTask){
        _task.remove(wellnessTask)
    }

    fun changeTaskChecked(item:WellnessTask, checked : Boolean){
        task.find {
            it.id == item.id
        }?.let{ task ->
            task.checked.value = checked
        }
    }
}