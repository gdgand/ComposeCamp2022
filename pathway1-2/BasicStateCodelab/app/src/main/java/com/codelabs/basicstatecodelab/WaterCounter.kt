package com.codelabs.basicstatecodelab

import WellnessTaskItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by remember { mutableStateOf(0) }
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

/**
 * 상태를 소유함 (count 보유)
 */
@Composable
fun StatefulCounter() {
    // 서로 다른 두 개의 독립 상태
    var waterCount by rememberSaveable { mutableStateOf(0) }
//    var juiceCount by remember { mutableStateOf(0) }

    // 상태 수정
    StatelessCounter(waterCount, { waterCount++ })
//    StatelessCounter(juiceCount, { juiceCount++ })
}

@Preview
@Composable
fun WaterCounterPreview() {
    BasicStateCodelabTheme {
        WaterCounter(Modifier)
    }
}