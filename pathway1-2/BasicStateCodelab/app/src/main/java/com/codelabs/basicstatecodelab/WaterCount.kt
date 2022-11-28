package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// 물잔 개수를 표시하는 Text 컴포저블이 포함된 WaterCount라는 구성 가능한 함수
// 물잔 개수는 count라는 값에 저장한다.

@Composable
fun WaterCount(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by remember { mutableStateOf(0) }
        Text(
            text = "You've had $count glasses.", modifier = modifier.padding(16.dp)
        )
        Button(onClick = { count++ }, Modifier.padding(top = 12.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
} // End of WaterCount