package com.codelabs.basicstatecodelab

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable  { mutableStateOf(0) }
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

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count, { count++ }, modifier)
//
//    var waterCount by remember { mutableStateOf(0) }
//    var juiceCount by remember { mutableStateOf(0) }
//
//    StatelessCounter(waterCount, { waterCount++ })
//    StatelessCounter(juiceCount, { juiceCount++ })

//
//    var count by remember { mutableStateOf(0) }
//
//    StatelessCounter(count, { count++ })
//    StatelessCounter(count, { count *= 2 })
}

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column(modifier = modifier.padding(16.dp)) {
//        // Changes to count are now tracked by Compose
////        val count: MutableState<Int> = remember { mutableStateOf(0) }
//        var count by remember {
//            mutableStateOf(0)
//        }
//
//        if(count > 0 ){
//            var showTask by remember {
//                mutableStateOf(true)
//            }
//            if(showTask){
//                WellnessTaskItem(
//                    taskName = "Have you taken your 15 minute walk today?",
//                    onClose = { showTask = false })
//            }
//            Text("You've had $count glasses.")
//        }
//
//        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled =  count <10) {
//            Text("Add one")
//        }
//        Button(onClick = { count = 0 }, Modifier.padding(start = 8.dp)) {
//            Text("Clear water count")
//        }
//    }
//}