package com.codelabs.basicstatecodelab

import android.util.Log
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

/**
 * BasicStateCodelab
 * @author jaesung
 * @created 2022/11/19
 */

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Log.e("WC", "Recomposition!")
//
//    Column(modifier = modifier.padding(16.dp)) {
////        val count = mutableStateOf(0)  // Recomposition이 발생할 때마다 0으로 초기화됨
//
//        // remember를 통해 recomposition이 발생하더라도 이전 상태를 기억할 수 있음
//        var count by remember {
//            mutableStateOf(0)
//        }
//
//        // 여기서 count와 showTask는 기억된 값으로 Recomposition이 발생해도 상태를 잃지 않음
//        if (count > 0) {
//            var showTask by remember {
//                mutableStateOf(true)
//            }
//            if (showTask) {
//                WellnessTaskItem(
//                    taskName = "Have you taken your 15 minute walk today",
//                    onClose = { showTask = false }
//                )
//            }
//            Text("You've had $count glasses.")
//        }
//        Row(Modifier.padding(top = 8.dp)) {
//            Button(
//                onClick = { count++ },
//                enabled = count < 10
//            ) {
//                Text("Add one")
//            }
//
//            Button(
//                onClick = { count = 0 },
//                modifier = Modifier.padding(start = 8.dp)
//            ) {
//                Text("Clear water count")
//            }
//        }
//    }
//}

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        // rememberSaveable로 Config Change가 발생해도 상태 유지 가능
        var count by rememberSaveable { mutableStateOf(0) }
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WaterCounterPreview() {
    WaterCounter()
}