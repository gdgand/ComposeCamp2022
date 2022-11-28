package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
//        val count : MutableState<Int> = remember{mutableStateOf(0)}
        var count by rememberSaveable {
            mutableStateOf(0)
        }
        if (count > 0) {
//            var showTask by remember {
//                mutableStateOf(true)
//            }
//            if (showTask) {
//                WellnessTaskItem(taskName = "오늘 15분 이상 걸었나요?", onClose = { showTask = false })
//            }
            Text(
                text = "물 ${count}잔 마셨음.",
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Button(onClick = { count++ }, enabled = count < 10) {
                Text("한잔 마시기")
            }
            Button(onClick = {count = 0}, modifier = Modifier.padding(start = 8.dp)) {
                Text("물 초기화")
            }
        }
    }
}