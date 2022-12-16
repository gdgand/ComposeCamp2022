package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text(text = "You've had $count glasses.")
        }
        Button(onClick = { onIncrement.invoke() }, enabled = count < 10) {
            Text(text = "Add One")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var waterCount by rememberSaveable { mutableStateOf(0) }
//    var juiceCount by remember { mutableStateOf(0) }

    StatelessCounter(waterCount, { waterCount++ }, modifier)
//    StatelessCounter(juiceCount, { juiceCount++ }, modifier)

}

@Composable
fun WaterCounter(modifier: Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable { mutableStateOf(0) }
        if (count > 0) {
            Text(text = "You've had $count glasses.")
        }
        Button(onClick = { count++ }, enabled = count < 10) {
            Text(text = "Add One")
        }
    }
}
//@Composable
//fun WaterCounter(modifier: Modifier) {
//    Column(modifier = modifier.padding(16.dp)) {
//        var count by remember { mutableStateOf(0) }
//        if (count > 0) {
//            var showTask by remember { mutableStateOf(true) }
//            if (showTask) {
//                WellnessTaskItem(taskName = "Have you taken your 15 minute walk today?", onClose = { showTask = false })
//            }
//            Text(text = "You've had $count glasses.")
//        }
//        Row(modifier = Modifier.padding(top = 8.dp)) {
//            Button(onClick = { count++ }, enabled = count < 10) {
//                Text(text = "Add One")
//            }
//            Button(onClick = { count = 0 }, modifier = Modifier.padding(start = 8.dp)) {
//                Text("Clear water count")
//            }
//        }
//    }
//}

@Composable
fun WellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = taskName, modifier = modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Checkbox(checked = checked, onCheckedChange = onCheckChanged)
        IconButton(onClick = { onClose.invoke() }) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
        }
    }
}