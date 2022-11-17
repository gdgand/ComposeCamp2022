package com.codelabs.basicstatecodelab

import android.os.Bundle
import android.system.Os.remove
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
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
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(modifier = modifier, count = count) { ++count }
}

@Composable
fun StatelessCounter(
    count: Int,
    modifier: Modifier = Modifier,
    onAddCountButtonClick: () -> Unit,
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { onAddCountButtonClick() }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }

//    7. 컴포지션의 remember
//    Column(modifier = modifier.padding(16.dp)) {
//        var count by remember { mutableStateOf(0) }
//
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
//        Row(
//            modifier = Modifier.padding(top = 8.dp)
//        ) {
//            Button(
//                enabled = count < 10,
//                onClick = { count++ },
//            ) {
//                Text("Add one")
//            }
//            Spacer(modifier = Modifier.width(8.dp))
//            Button(
//                enabled = count != 0,
//                onClick = { count = 0 },
//            ) {
//                Text("Clear water count")
//            }
//        }
//    }
}

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel(),
) {
    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onClose = { task ->
                wellnessViewModel.removeTask(task)
            },
            onCheckedChange = { task, isChecked ->
                wellnessViewModel.checkChanged(task, isChecked)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WaterCounterPreview() {
    BasicStateCodelabTheme {
        StatelessCounter(count = 0) {}
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Composable
fun WellnessScreenPreview() {
    BasicStateCodelabTheme {
        WellnessScreen()
    }
}
