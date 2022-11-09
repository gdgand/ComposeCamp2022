package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.material.Button
import androidx.compose.foundation.layout.Column

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        // Changes to count are now tracked by Compose
        // 그러나
        // 리컴포지션이 발생하면 count 변수가 다시 0으로 초기화되므로! 값이 항상 0 으로 표기된다.
        val count: MutableState<Int> = mutableStateOf(0)

        Text("You've had ${count.value} glasses.")
        Button(onClick = { count.value++ }, Modifier.padding(top = 8.dp)) {
            Text("Add one")
        }
    }
}