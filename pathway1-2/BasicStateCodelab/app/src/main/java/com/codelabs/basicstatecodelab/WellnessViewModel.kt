package com.codelabs.basicstatecodelab

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.codelabs.basicstatecodelab.tmp.WellnessTask

/**
 * Written by StephenLeeDev on 2022/11/29.
 */

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }