package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by remember { mutableStateOf(0) }
        if (count > 0) {
            Text(
                text = "너는 ${count} 잔의 물을 마셨다.",
                modifier = modifier.padding(16.dp)
            )
        }
        Button(
            onClick =  { ++count },
            modifier = modifier.padding(top = 8.dp),
            enabled = count < 10
        ) {
            Text("잔 추가")
        }
    }
}