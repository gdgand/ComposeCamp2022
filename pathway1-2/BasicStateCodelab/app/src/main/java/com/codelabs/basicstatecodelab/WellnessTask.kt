package com.codelabs.basicstatecodelab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * @Created by 김현국 2022/12/12
 * @Time 3:13 PM
 */

class WellnessTask(
    val id: Int,
    val label: String,
    initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}
