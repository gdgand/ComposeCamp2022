package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    StatefulCounter(modifier)
    WellnessTaskList()
}

@Preview(showBackground = true)
@Composable
fun WellnessScreenPreview(modifier: Modifier = Modifier) {
    Column {
        StatefulCounter()
        WellnessTaskList()
    }
}