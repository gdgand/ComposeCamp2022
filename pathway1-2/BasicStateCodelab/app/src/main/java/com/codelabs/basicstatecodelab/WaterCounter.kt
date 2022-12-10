package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//
//@Composable
//fun WaterCounter(modifier: Modifier = Modifier){
//    Column(
//        modifier = Modifier.padding(16.dp)
//    ) {
//        var count by rememberSaveable { mutableStateOf(0) } // 초기값이 0
//
////        if(count > 0){
////            var showTask by remember{ mutableStateOf(true) }
////            if(showTask){
////                WellnessTaskItem(
////                    taskName = "Have you taken yout 15minute walk today?",
////                    onClose = { showTask = false })
////            }
////            Text("You've had $count glasses.")
////        }
////        Row(
////            modifier = Modifier.padding(top = 8.dp)
////        ) {
////            Button(
////                onClick = { count++ },
////                enabled = count<10
////            ) {
////                Text(text = "Add one")
////            }
////            Button(
////                onClick = { count = 0 },
////                Modifier.padding(start = 8.dp)) {
////                Text("Clear water count")
////            }
////        }
//        if(count>0){
//            Text("You've had $count glasses.")
//        }
//        Button(onClick = {count++}, Modifier.padding(top = 8.dp), enabled = count<10 ){
//            Text("Add one")
//        }
//    }
//}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(16.dp)){
        if(count>0){
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count<10){
            Text("Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier){
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count, { count++ }, modifier)
}


//@Composable
//fun StatefulCounter(modifier: Modifier = Modifier){
//    var waterCount by rememberSaveable { mutableStateOf(0) }
//    var juiceCount by rememberSaveable { mutableStateOf(0) }
//
//    StatelessCounter(waterCount, { waterCount++ })
//    StatelessCounter(juiceCount, { juiceCount++ })
//}

//@Composable
//fun StatefulCounter(modifier: Modifier = Modifier){
//    var count by remember { mutableStateOf(0) }
//
//    StatelessCounter(count, { count++ })
//    AnotherStatelessMethod(count, { count *= 2})
//}




@Preview(showBackground = true)
@Composable
fun previewForWaterCounter(modifier: Modifier = Modifier){
    WellnessScreen()
}