package com.codelabs.basicstatecodelab

import androidx.compose.runtime.*

class WellnessTask(val id: Int, val label: String, initialChecked: Boolean = false) {
    var checked by mutableStateOf(initialChecked)
}