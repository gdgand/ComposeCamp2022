package com.codelabs.basicstatecodelab

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class WellNessTask(
    val id: Int,
    val label: String,
    var isCheck: MutableState<Boolean> = mutableStateOf(false)
) {
    companion object {
        fun getWellNessTasks() = List(30) {
            WellNessTask(it,"Task : $it")
        }
    }
}