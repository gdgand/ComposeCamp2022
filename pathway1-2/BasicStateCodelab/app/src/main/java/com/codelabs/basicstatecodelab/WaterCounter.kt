package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    // 컴포지션을 종료하면 기억된 상태가 삭제된다는 문제
    // LazyColumn 에 있는 항목의 경우 스크롤하면서 항목을 지나치면, 컴포지션을 완전히 종료, 더 이상 항목 표시되지 않음(체크가 풀림)
    // 컴포지션을 종료할때에도 체크 상태를 유지하기위해
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count, { count++ }, modifier)
}

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column(modifier = modifier.padding(16.dp)) {
//        // Changes to count are now tracked by Compose
//        var count by rememberSaveable { mutableStateOf(0) }
//        Text(text = "You've had $count glasses")
//        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
//            Text("Add one")
//        }
//    }
//}

@Composable
fun WellnessTaskItem(
    taskName: String, onClose: () -> Unit, modifier: Modifier = Modifier
) {
    var checkedState by rememberSaveable { mutableStateOf(false) }

    WellnessTaskItem(
        taskName = taskName,
        checked = checkedState,
        onCheckedChange = { newValue -> checkedState = newValue },
        onClose = onClose,
        modifier = modifier
    )
}