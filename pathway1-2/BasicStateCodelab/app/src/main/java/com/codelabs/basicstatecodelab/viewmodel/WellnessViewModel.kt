package com.codelabs.basicstatecodelab.viewmodel

import androidx.lifecycle.ViewModel
import com.codelabs.basicstatecodelab.data.WellnessTask

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "할일 VM # $i") }


class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableList();
    val tasks: List<WellnessTask> get() = _tasks;
    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) = tasks.findLast {
        it.id == item.id
    }?.let { task -> task.checked = checked }
}
