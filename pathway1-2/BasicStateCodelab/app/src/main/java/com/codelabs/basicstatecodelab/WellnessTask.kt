package com.codelabs.basicstatecodelab

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class WellnessTask(
    val id: Int,
    val label: String,
    initialChecked: Boolean = false
) {
    // 생성자에서 initialChecked 변수를 수신 -> 팩토리 메서드 mutableStateOf로 초기화
    var checked by mutableStateOf(initialChecked)
}