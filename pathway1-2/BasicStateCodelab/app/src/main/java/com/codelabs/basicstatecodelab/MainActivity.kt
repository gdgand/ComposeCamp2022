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

//@Composable
//fun WaterCounter(
//    modifier: Modifier = Modifier
//) {
//    Column(modifier = modifier.padding(16.dp)) {
//        var count by remember { mutableStateOf(0) }
//        if (count > 0) {
//            var showTask by remember { mutableStateOf(true) }
//            if (showTask) {
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
//                Text("Add one")
//            }
//            Button(onClick = { count = 0 }, Modifier.padding(start = 8.dp)) {
//                Text("Clear water count")
//            }
//        }
//    }
//}

@Composable
fun StatelessCounter(
    modifier: Modifier = Modifier,
    count: Int,
    onIncrement: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun StatefulCounter() {
    var waterCount by remember { mutableStateOf(1) }
    var juiceCount by remember { mutableStateOf(1) }

    Column {
        StatelessCounter(
            count = waterCount,
            onIncrement = {
                waterCount++
            }
        )

        StatelessCounter(
            count = juiceCount,
            onIncrement = {
                juiceCount *= 2
            }
        )
    }
}

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(
        modifier = modifier
    ) {
        StatefulCounter()

        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
                wellnessViewModel.remove(task)
            }
        )
    }
}

@Composable
fun WellnessTaskItem(
    modifier: Modifier = Modifier,
    taskName: String,
    onClose: () -> Unit
) {
    var checkedState by rememberSaveable { mutableStateOf(false) }

    WellnessTaskItem(
        modifier = modifier,
        taskName = taskName,
        checked = checkedState,
        onCheckedChange = { newValue -> checkedState = newValue },
        onClose = onClose
    )
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
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessViewModel.WellnessTask>,
    onCheckedTask: (WellnessViewModel.WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessViewModel.WellnessTask) -> Unit
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(list) { task ->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = { checked ->
                    onCheckedTask(task, checked)
                },
                onClose = {
                    onCloseTask(task)
                }
            )
        }
    }
}
