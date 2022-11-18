package com.codelabs.basicstatecodelab.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        StatefulCounter()

        val lists = remember { getWellnessTasks().toMutableStateList() }
        WellnessTasksList(
            lists = lists,
            onCloseTask = { task -> lists.remove(task) }
        )
    }
}