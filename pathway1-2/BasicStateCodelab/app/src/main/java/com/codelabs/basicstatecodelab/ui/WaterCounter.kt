package com.codelabs.basicstatecodelab.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable { mutableStateOf(0) }

        if (count > 0) {
            // Clear 에 의해서 count가 0이 되면 showTask는 recomposition 과정에서 사용되지 않게되어 Compose 저장 영역에서
            // 삭제 된다. 따라서 showTask 가 false로 변경했어도 다시 초기화 되어 true가 된다.
            var showTask by remember { mutableStateOf(true) }
            if (showTask) {
                WellnessTaskItem(
                    taskName = "Have you taken your 15 minute walk today?",
                    onClose = { showTask = false }
                )
            }
            Text(text = "You've had $count glasses.")
        }

        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = { count++ },
                enabled = count < 10
            ) {
                Text("Add one")
            }
            Button(
                onClick = { count = 0 },
                modifier = Modifier.padding(start = 8.dp),
            ) {
                Text("Clear water count")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WaterCounterPreview() {
    BasicStateCodelabTheme {
        WaterCounter()
    }
}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    if (count > 0) {
        Text(text = "You've had $count glasses.")
    }
    Button(onClick = onIncrement, modifier = Modifier.padding(top = 8.dp), enabled = count < 10) {
        Text(text = "Add one")
    }
}

@Composable
fun StatefulCounter() {
    var waterCount by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count = waterCount, onIncrement = { waterCount++ })
}

@Preview(showBackground = true)
@Composable
fun StatefulCounterPreview() {
    BasicStateCodelabTheme {
        StatefulCounter()
    }
}
