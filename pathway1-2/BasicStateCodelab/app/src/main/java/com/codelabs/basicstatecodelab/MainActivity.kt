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
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    StatefulCount(modifier)
    WellNessTasksList(
        list = wellnessViewModel.tasks,
        onCloseTask = { wellnessViewModel.remove(it) },
        onCheckedChange = { task, isCheck ->
            wellnessViewModel.changeTaskChecked(task, isCheck)
        }
    )
}

@Composable
fun WellNessTasksList(
    modifier: Modifier = Modifier,
    onCloseTask: (WellNessTask) -> Unit,
    onCheckedChange: (WellNessTask, Boolean) -> Unit,
    list : List<WellNessTask>
) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->
            WellNessTaskItem(
                name = task.label,
                isCheck = task.isCheck.value,
                onClose = { onCloseTask(task) },
                onCheckedChange = { onCheckedChange(task, it) }
            )
        }
    }
}

@Composable
fun WellNessTaskItem(
    modifier: Modifier = Modifier,
    name: String,
    onClose: () -> Unit,
    isCheck: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Checkbox(
            checked = isCheck,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null
            )
        }
    }
}

@Composable
fun StatefulCount(
    modifier: Modifier = Modifier
) {
    var waterCount by rememberSaveable { mutableStateOf(0) }
    var juiceCount by rememberSaveable { mutableStateOf(0) }

    StatelessCount(count = waterCount) { waterCount++ }
    StatelessCount(count = juiceCount) { juiceCount++ }
}

@Composable
fun StatelessCount(
    modifier: Modifier = Modifier,
    count: Int,
    onIncrement: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {

        if (count > 0) Text(text = "Count : $count")

        Button(
            onClick = onIncrement,
            enabled = count < 10,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Add One")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicStateCodelabTheme {
        WellnessScreen()
    }
}