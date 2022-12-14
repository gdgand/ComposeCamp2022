package com.codelabs.basicstatecodelab.usecase

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.codelabs.basicstatecodelab.data.WellnessTask

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun changedTaskChecked(item: WellnessTask, checked: Boolean) =
        tasks.find { it.id == item.id }?.let { task ->
            task.checked = checked
        }
    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }

