package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
//    StatefulCounter()
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTaskList()
    }
}