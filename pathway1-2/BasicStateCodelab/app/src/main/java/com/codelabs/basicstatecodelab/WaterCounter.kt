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

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column(modifier.padding(16.dp)) {
//
//        var count by remember { mutableStateOf(0) }
//
//        if (count > 0) {
//            var showTask by remember {
//                mutableStateOf(true)
//            }
//
//            if (showTask) {
//                WellnessTaskItem(taskName = "Have you taken your 15 minute walk today?", onClose = {
//                    showTask = false
//                })
//            }
//            Text(text = "You've had $count glasses.")
//
//        }
//
//        Row {
//
//            Button(
//                onClick = { count++ },
//                modifier = modifier.padding(top = 16.dp),
//                enabled = count < 10
//            ) {
//                Text("Add one")
//            }
//
//            Button(
//                onClick = { count = 0 },
//                modifier = modifier.padding(start = 16.dp, top = 16.dp)
//            ) {
//                Text("Clear water count")
//            }
//        }
//    }
//}

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable { mutableStateOf(0) }
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count, { count++ }, modifier)

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