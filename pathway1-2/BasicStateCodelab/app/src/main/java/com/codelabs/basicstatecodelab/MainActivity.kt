package com.codelabs.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable { mutableStateOf(0) }
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit, modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        //count를 표시하고 이를 늘릴때 함수를 호출한다.
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
    //상태를 소유함, count의 상태를 보유하고 함수 호출시 이를 수정함 -> count를 끌어올렸다.
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count, { count++ }, modifier)
}


@Composable
fun NewWaterCounter(modifier: Modifier = Modifier) {

    Column(modifier.padding(16.dp)) {
        var count by remember { mutableStateOf(0) }
        //상태의 변경을 추적하기는 하나 변경 이후에 해당 변수 값이 다시 0으로 초기화 되므로 이를 유지시켜주어야 함.
        if (count in 1..9) {
            var showTask by remember {
                mutableStateOf(true)
            }
            if (showTask) {
                WellnessTaskItem(
                    taskName = "Have you taken your 15 minute walk today?",
                    onClose = { showTask = false })
            }
            //count 가 0보다 클때 표시 여부를 결정,
            Text(
                text = "You've had $count glasses.",
                modifier = modifier.padding(16.dp)
            )
        } else if (count > 9) {
            Text(
                text = "Too much.",
                modifier = modifier.padding(16.dp)
            )
        } else {
            Text(
                text = "thirsty.",
                modifier = modifier.padding(16.dp)
            )
        }

        Button(
            onClick = { count++ }, modifier.padding(8.dp),
            enabled = (count < 10)
        ) {
            Text(text = "Drink Water")
        }
        Button(onClick = { count = 0 }, Modifier.padding(start = 8.dp)) {
            Text("Clear water count")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicStateCodelabTheme {
        WaterCounter()
    }
}