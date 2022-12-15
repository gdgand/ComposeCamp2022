package com.codelab.basicstate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class WellnessTask(val id: Int, val label: String, initialChecked: Boolean) {
    var checked by mutableStateOf(initialChecked)
}
