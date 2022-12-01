package com.codelabs.basicstatecodelab.ui.theme

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel


// 상태 변경에 반응 해야함
// 화면 or UI 상태는 화면에 표시할 내용을 나타냄
// 따라서 상태, 목록을 ViewModel로 이전
/*
data 소스 getWellnessTasks()를 ViewModel로 이전.
 */
class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }

    // ViewModel에서 선택된 상태의 새 값으로 수정할 작업을 수신하는 changeTaskChecked 메서드를 수신
    fun changeTaskChecked(item: WellnessTask, checked: Boolean) =
        tasks.find { it.id == item.id }?.let { task ->
            task.checked = checked
        }

}

private fun getWellnessTasks() = List(30) {i -> WellnessTask(i, "Task # $i")}