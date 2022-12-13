package com.codelabs.basicstatecodelab

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Created by okwon on 2022/12/08.
 * Description :
 */
data class WellnessTask(val id: Int, val label: String, val initialChecked: Boolean = false){
    var checked by mutableStateOf(initialChecked)
}

