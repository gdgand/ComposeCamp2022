package com.codelabs.basicstatecodelab.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

/**
 * Created by jihye
 * Date: 2022/11/24
 */
class WellnessTask(
    val id: Int,
    val label: String,
    initialChecked: Boolean = false,
) {
    var checked by mutableStateOf(initialChecked)
}