package com.codelabs.basicstatecodelab

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier

/**
 * BasicStateCodelab
 * @author jaesung
 * @created 2022/11/19
 */

/**
 * toMutableStateList()를 통해 관찰가능한 MutableList를 만들 수 있다.
 *
 * mutableStateListOf()를 통해서도 관찰가능한 MutableList를 만들 수 있다.
 * 하지만, apply를 통해 목록 생성과 동시에 작업을 해야 리컴포지션시 중복된 항목 추가를 방지할 수 있다.
 *
 * ``` kotlin
 * // 잘못된 방법
 * val list = remember { mutableStateListOf<WellnessTask> }
 * list.addAll(getWellnessTasks())
 *
 * // 옳은 방법
 * val list = remember { mutableStateListOf<WellnessTask>().apply {
 *     addAll(getWellnessTasks())
 * }}
 * ```
 */
private fun getWellnessTasks() = List(30) { i ->
    WellnessTask(i, "Task # $i")
}

/**
 * list를 rememberSaveable로 하게되면 Exception이 발생한다.
 * Custom Saver를 제공해야한다..???
 *
 */
@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Log.e("WS", "Recomposition!")
//    StatefulCounter(modifier = modifier)

    Column(modifier = modifier) {
        StatefulCounter()

        val list = remember { getWellnessTasks().toMutableStateList() }
        WellnessTasksList(
            list = list,
            onCloseTask = { task -> list.remove(task)}
        )
    }
}