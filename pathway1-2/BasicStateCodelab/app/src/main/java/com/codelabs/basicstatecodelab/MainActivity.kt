package com.codelabs.basicstatecodelab

import android.os.Bundle
import android.service.controls.Control.StatelessBuilder
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

        var showTask by remember {
            mutableStateOf(true)
        }

        if (count > 0) {
            if (showTask) {
                WellnessTaskItem(task = "15 walk today?")
            }
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
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList()
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
    list: List<WellnessTask> = remember { getWellnessTasks() }
) {
    LazyColumn(modifier = modifier) {
        items(list) { task ->
            WellnessTaskItem(task.title)
        }
    }
}

@Composable
fun WellnessTaskItem(task: String, modifier: Modifier = Modifier) {
    var checkedState by rememberSaveable {
        mutableStateOf(false)
    }

    StatelessWellnessTaskItem(
        task = task,
        checked = checkedState,
        onCheckedChange = { newCheckedState -> checkedState = newCheckedState },
        onClose = {},
        modifier = modifier
    )
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

data class WellnessTask(
    val id: Int,
    val title: String
)

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }
