package com.codelabs.basicstatecodelab.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class WellnessTask(
    val id: Int,
    val label: String,
    /* var checked: Boolean = false */
    initialChecked: Boolean = false
) {

    var checked by mutableStateOf(initialChecked)

}
