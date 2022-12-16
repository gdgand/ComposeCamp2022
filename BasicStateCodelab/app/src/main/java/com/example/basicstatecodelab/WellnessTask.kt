package com.example.basicstatecodelab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

class WellnessTask(
    val id: Int,
    val label: String,
    initalChecked: Boolean = false
){
    var checked by mutableStateOf(initalChecked)
}
