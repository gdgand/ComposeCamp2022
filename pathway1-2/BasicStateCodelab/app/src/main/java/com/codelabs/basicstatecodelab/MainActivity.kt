package com.codelabs.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme

class MainActivity : ComponentActivity() {
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
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by remember { mutableStateOf(0) }
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
//    Column(modifier = modifier.padding(16.dp)) {
//        var count by remember { mutableStateOf(0) }
//        if (count > 0) {
//            var showTask by remember { mutableStateOf(true) }
//            if (showTask) {
//                WellnessTaskItem(
//                    taskName = "Have you taken your 15 minute walk today?",
//                    onClose = { showTask = false }
//                )
//            }
//
//            Text(text = "You've had $count glasses.")
//        }
//
//        Row(
//            modifier = Modifier.padding(top = 8.dp)
//        ){
//            Button(
//                onClick = { count++ },
//                enabled = count < 10
//            ) {
//                Text(text = "Add One")
//            }
//            Button(
//                onClick = { count = 0 },
//                modifier = Modifier.padding(start = 8.dp)
//            ) {
//                Text(text = "Clear water count")
//            }
//        }
//    }
}

@Composable
fun WellnessTaskItem(
    modifier: Modifier = Modifier,
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = taskName,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close"
            )
        }
    }
}

//@Composable
//fun WellnessTaskItem(
//    taskName: String,
//    modifier: Modifier = Modifier,
//    onCheckedTask: (WellnessTask, Boolean) -> Unit,
//    onCloseTask: (WellnessTask) -> Unit
//) {
//    var checkedState by rememberSaveable { mutableStateOf(false) }
//
//    WellnessTaskItem(
//        taskName = taskName,
//        checked = checkedState,
//        onCheckedChange = { newValue -> checkedState = newValue },
//        onClose = onClose,
//        modifier = modifier,
//    )
//}

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask>,
    onCloseTask: (WellnessTask) -> Unit,
    onCheckedTask: (WellnessTask, Boolean) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { it.id }
        ) { task ->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = { onCheckedTask(task, it) },
                onClose = { onCloseTask(task) }
            )
        }
    }
}

@Composable
fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(all = 16.dp)) {
        if (count > 0) {
            Text(text = "You've had $count glasses.")
        }
        Button(
            onClick = onIncrement,
            modifier = Modifier.padding(top = 8.dp),
            enabled = count < 10
        ) {
            Text(text = "Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    // var count by rememberSaveable { mutableStateOf(0) }
    var waterCount by remember { mutableStateOf(0) }
    var juiceCount by remember { mutableStateOf(0) }

    //StatelessCounter(count = waterCount, onIncrement = { waterCount++ })
    StatelessCounter(count = juiceCount, onIncrement = { juiceCount++ })
}

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    // WaterCounter(modifier)
    // StatefulCounter(modifier = modifier)
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCloseTask = { wellnessViewModel.remove(wellnessTask = it) },
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(
                    wellnessTask = task,
                    checked = checked
                )
            }
        )
    }
}

@Preview
@Composable
fun WellnessTaskItemPreview() {

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicStateCodelabTheme {
        WaterCounter()
    }
}
