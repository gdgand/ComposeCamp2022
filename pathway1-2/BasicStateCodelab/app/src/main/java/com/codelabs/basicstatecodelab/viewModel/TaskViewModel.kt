package com.codelabs.basicstatecodelab.viewModel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.codelabs.basicstatecodelab.item.Wellness

class TaskViewModel : ViewModel() {

    private val _tasks = getWellnessItems().toMutableStateList()
    val tasks: List<Wellness>
        get() = _tasks

    fun remove(item: Wellness) {
        _tasks.remove(item)
    }

    fun changeCheckedState(item: Wellness, checked: Boolean) =
        _tasks.find { it.id == item.id }?.let {
            it.checked = checked
        }

    private fun getWellnessItems() = List(30) { index -> Wellness(index, "Task # $index") }
}
