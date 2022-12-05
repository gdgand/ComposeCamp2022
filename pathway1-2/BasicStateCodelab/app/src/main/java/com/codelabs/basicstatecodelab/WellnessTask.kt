package com.codelabs.basicstatecodelab

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

//data class WellnessTask(var id : Int, var label : String, var checked : MutableState<Boolean> = mutableStateOf(false))
class WellnessTask(
    var id : Int,
    var label : String,
    initialChecked : Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}