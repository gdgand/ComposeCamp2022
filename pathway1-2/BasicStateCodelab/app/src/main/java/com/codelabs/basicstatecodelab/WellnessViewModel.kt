package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.codelabs.basicstatecodelab.ui.wellness.WellnessTask
import com.codelabs.basicstatecodelab.ui.wellness.getWellnessTasksList

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasksList().toMutableStateList()
    val taks: List<WellnessTask>
        get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) =
        taks.find { it.id == item.id }?.let { task ->
            task.checked = checked
        }
}