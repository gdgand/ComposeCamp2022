package com.codelabs.basicstatecodelab.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codelabs.basicstatecodelab.ui.component.StatefulCounter
import com.codelabs.basicstatecodelab.ui.component.WellnessTasksList

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList()
    }
}
