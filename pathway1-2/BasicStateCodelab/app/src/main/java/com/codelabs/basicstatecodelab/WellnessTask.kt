package com.codelabs.basicstatecodelab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * WellnessTask를 데이터 클래스가 아닌 클래스가 되도록 변경합니다. WellnessTask가 생성자에서 기본값이 false인 initialChecked 변수를 수신하도록 하면 팩토리 메서드 mutableStateOf로 checked 변수를 초기화하여 initialChecked를 기본값으로 사용할 수 있습니다.
 */
class WellnessTask(
    val id: Int,
    val label: String,
    initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}