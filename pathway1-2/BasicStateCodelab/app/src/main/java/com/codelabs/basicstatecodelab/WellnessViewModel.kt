package com.codelabs.basicstatecodelab

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class WellnessViewModel: ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) =
        tasks.find { it.id == item.id }?.let { task ->
            task.checked = checked
        }
}

data class WellnessTask(
    val id: Int,
    val label: String,
    val initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}
fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }