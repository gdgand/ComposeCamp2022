package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
//    StatefulCounter()
    Column(modifier = modifier) {
        StatefulCounter()
        val list = remember { getWellnessTasks() }
        WellnessTaskList(
            list = list,
            onCloseTask = { task -> list.remove(task) }
        )
    }
}