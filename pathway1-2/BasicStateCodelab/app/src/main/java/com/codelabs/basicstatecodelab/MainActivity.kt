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
    modifier: Modifier = Modifier
) {
    StatefulCount(modifier)
    WellNessTasksList()
}

@Composable
fun WellNessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellNessTask> = remember { WellNessTask.getWellNessTasks() }
) {
    LazyColumn(modifier = modifier) {
        items(list) { task ->
            WellnessTaskItem(taskName = task.label)
        }
    }
}

@Composable
fun WellnessTaskItem(
    modifier: Modifier = Modifier,
    taskName: String
) {
    var isCheckState by rememberSaveable { mutableStateOf(false) }

    WellNessTask(
        name = taskName,
        onClose = { /*TODO*/ },
        isCheck = isCheckState,
        onCheckedChange = { isCheckState = !isCheckState }
    )
}

@Composable
fun WellNessTask(
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

data class WellNessTask(
    val id: Int,
    val label: String
) {
    companion object {
        fun getWellNessTasks() = List(30) {
            WellNessTask(it,"Task : $it")
        }
    }
}