package com.codelabs.basicstatecodelab

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class WellnessTask(
    val id: Int,
    val label: String,
    initialChecked : Boolean = false
) {
    var checked: MutableState<Boolean> = mutableStateOf(initialChecked)
}