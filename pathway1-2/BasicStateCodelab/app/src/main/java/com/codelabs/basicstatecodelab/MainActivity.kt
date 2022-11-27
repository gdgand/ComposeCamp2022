package com.codelabs.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codelabs.basicstatecodelab.data.WellnessTask
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme
import com.codelabs.basicstatecodelab.utils.WellnessTasksList

class MainActivity : ComponentActivity() {
    private var b: Int = 0
        private set
        get

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicStateCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WellnessScreen()
                }
            }
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier){
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count, { count++ }, modifier)
}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(16.dp)) {
        if(count > 0){
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun WellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = taskName
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
){
    Column(modifier = modifier) {
        StatefulCounter(modifier)
        //val list = remember { getWellnessTasks().toMutableStateList() }
        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked -> wellnessViewModel.changeTaskChecked(task, checked)},
            onCloseTask = { task -> wellnessViewModel.remove(task)}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WellnewssScreenPreview(){
    BasicStateCodelabTheme {
        WellnessScreen()
    }
}

////////////////////// BackLog /////////////////////////

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier){
//    Column(modifier = modifier.padding(16.dp)){
//        var count by rememberSaveable { mutableStateOf(0) }
//        if(count > 0){
//            Text("You've had $count.value glasses.")
//        }
//        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
//            Text(text = "Add one")
//        }
//    }
//}

//@Composable
//fun WaterCounterRememberRemove(modifier: Modifier = Modifier){
//    Column(modifier = modifier.padding(16.dp)){
//        var count by remember{ mutableStateOf(0) }
//
//        if(count > 0){
//            var showTask by remember { mutableStateOf(true) }
//            if(showTask){
//                WellnessTaskItem(
//                    onClose = { showTask = false },
//                    taskName = "Have you taken your 15 minute walk today?"
//                )
//            }
//            Text("You've had $count glasses.")
//        }
//
//        Row(Modifier.padding(top = 8.dp)) {
//            Button(onClick = { count++ }, enabled = count < 10) {
//                Text(text = "Add one")
//            }
//            Button(onClick = { count = 0 }, Modifier.padding(start = 8.dp)) {
//                Text("Clear water count")
//            }
//        }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun WaterCounterRememberRemovePreview(){
//    WaterCounterRememberRemove()
//}

//@Preview(showBackground = true)
//@Composable
//fun WellnessTaskItemPreview(){
//    WellnessTaskItem("This is a task", {})
//}

//@Composable
//fun WellnessTaskItem(
//    taskName: String, onClose: () -> Unit, modifier: Modifier = Modifier){
//    var checkedState by rememberSaveable { mutableStateOf(false) }
//
//    WellnessTaskItem(
//        taskName = taskName,
//        checked = checkedState,
//        onCheckedChange = { newValue -> checkedState = newValue },
//        onClose = onClose,
//        modifier = modifier
//    )
//}