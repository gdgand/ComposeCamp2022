package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<_root_ide_package_.com.codelabs.basicstatecodelab.WellnessTask>
        get() = _tasks

    fun remove(item: _root_ide_package_.com.codelabs.basicstatecodelab.WellnessTask) {
        _tasks.remove(item)
    }

    //선택된 상태의 새값으로 수정할 작업을 수신
    fun changeTaskChecked(item: _root_ide_package_.com.codelabs.basicstatecodelab.WellnessTask, checked: Boolean) =
        tasks.find { it.id == item.id }?.let { task ->
            task.checked = checked
        }
}

private fun getWellnessTasks() = List(30) { i -> _root_ide_package_.com.codelabs.basicstatecodelab.WellnessTask(i, "Task # $i") }
