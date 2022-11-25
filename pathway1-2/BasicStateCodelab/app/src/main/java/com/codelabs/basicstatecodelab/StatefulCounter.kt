package com.codelabs.basicstatecodelab

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var waterCount by remember { mutableStateOf(0) }
    var juiceCount by remember { mutableStateOf(0) }
    StatelessCounter(waterCount, { waterCount++ })
    StatelessCounter(juiceCount, { juiceCount++ })
}