package com.codelabs.basicstatecodelab

import androidx.compose.runtime.*

data class WellnessTask(
    val id: Int,
    val label: String,
    private val initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}