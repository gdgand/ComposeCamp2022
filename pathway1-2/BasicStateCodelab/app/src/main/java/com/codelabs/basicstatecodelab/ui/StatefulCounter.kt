package com.codelabs.basicstatecodelab.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun StatefulCounter(
    modifier: Modifier = Modifier
) {
    var waterCount by rememberSaveable { mutableStateOf(0) }

    Column {
        StatelessCounter(count = waterCount, onIncrement = { waterCount++ }, modifier = modifier)
    }
}