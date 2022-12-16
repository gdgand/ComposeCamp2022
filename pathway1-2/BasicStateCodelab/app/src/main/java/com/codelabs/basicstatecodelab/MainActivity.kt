package com.codelabs.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
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

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable {
            mutableStateOf(0)
        }

        if (count > 0) {
            Text(text = "current count : $count")
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {

            Button(
                onClick = { count++ },
                enabled = count < 10
            ) {
                Text(text = "count up")
            }

            Button(onClick = { count = 0 }) {
                Text(text = "clear count")
            }
        }

    }
}

@Composable
fun WellnessScreen(modifier: Modifier = Modifier, viewModel: WellnessViewModel = viewModel()) {
    Column(modifier = modifier) {
        StatefulCounter()

        WellnessTasksList(
            list = viewModel.tasks,
            onCheckedChange = { task, checked -> viewModel.changeTaskChecked(task, checked) },
            onCloseTask = { task -> viewModel.remove(task) }
        )
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable {
        mutableStateOf(0)
    }
    StatelessCounter(count = count, onIncrement = { count++ }, modifier = modifier)
}

@Composable
fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (count > 0) {

        Text(text = "current count : $count")
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(top = 8.dp)
    ) {

        Button(
            onClick = onIncrement,
            enabled = count < 10
        ) {
            Text(text = "count up")
        }
    }
}

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    onCheckedChange: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit,
    list: List<WellnessTask>
) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->
            StatelessWellnessTaskItem(
                task = task.title,
                checked = task.checked,
                onCheckedChange = { newCheckedState -> onCheckedChange(task, newCheckedState)},
                onClose = { onCloseTask(task) },
                modifier = modifier
            )
        }
    }
}

@Composable
fun StatelessWellnessTaskItem(
    task: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = task, modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )

        Checkbox(checked = checked, onCheckedChange = onCheckedChange)

        IconButton(onClick = onClose) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = null)
        }
    }
}
