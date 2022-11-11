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
import androidx.compose.ui.tooling.preview.Preview
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
        var count by rememberSaveable { mutableStateOf(0) }

        if (count > 0) {
            var showTask by remember { mutableStateOf(true) }
            if (showTask) {
                WellnessTaskItem(
                        taskName = "Have you taken your 15 minute walk today?",
                        onClose = { showTask = false },
                        checked = false,
                        onCheckedChange = {},
                )
            }
            Text(text = "You've had $count glassed.")
        }
        Row(
                modifier = Modifier.padding(top = 8.dp),
        ) {

            Button(
                    onClick = { count++ },
                    enabled = count < 10,
            ) {
                Text("Add one")
            }
            Button(
                    onClick = { count = 0 },
                    Modifier.padding(start = 8.dp)
            ) {
                Text("Clear water count")
            }
        }
    }
}

data class WellnessTask(
        val id: Int,
        val label: String,
)

private fun getWellnessTasks() = List(30) { WellnessTask(it, "Task #$it") }

@Composable
fun WellnessTasksList(
        modifier: Modifier = Modifier,
        list: List<WellnessTask> = remember { getWellnessTasks() },
) {
    LazyColumn(
            modifier = modifier,
    ) {
        items(list) {
            WellnessTaskItem(taskName = it.label)
        }
    }
}

@Composable
fun WellnessTaskItem(
        taskName: String,
        modifier: Modifier = Modifier,
) {
    var checkedState by rememberSaveable { mutableStateOf(false) }

    WellnessTaskItem(
            taskName = taskName,
            checked = checkedState,
            onCheckedChange = { checkedState = it },
            onClose = { },
            modifier = modifier,
    )
}

@Composable
fun WellnessTaskItem(
        taskName: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        onClose: () -> Unit,
        modifier: Modifier = Modifier,
) {
    Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
                modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                text = taskName,
        )
        Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange
        )
        IconButton(
                onClick = onClose,
        ) {
            Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
            )
        }
    }
}


@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var waterCount by rememberSaveable { mutableStateOf(0) }
//    var juiceCount by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(
            count = waterCount,
            onIncrement = { waterCount++ },
            modifier = modifier,
    )

//    StatelessCounter(
//            count = juiceCount,
//            onIncrement = { juiceCount++ },
//            modifier = modifier,
//    )
}

@Composable
fun StatelessCounter(
        count: Int,
        onIncrement: () -> Unit,
        modifier: Modifier = Modifier,
) {
    Column(
            modifier = modifier.padding(16.dp)
    ) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(
                modifier = Modifier.padding(top = 8.dp),
                onClick = onIncrement,
                enabled = count < 10
        ) {
            Text("Add one")
        }
    }
}

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        StatefulCounter(modifier)
        WellnessTasksList(modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun WellnessScreenPreview() {
    BasicStateCodelabTheme {
        WellnessScreen()
    }
}
