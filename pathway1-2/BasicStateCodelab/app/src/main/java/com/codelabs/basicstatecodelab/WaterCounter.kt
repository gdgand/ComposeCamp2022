package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    StatefulCounter(modifier = modifier)
}


@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {


            Text(
                text = "${count}컵 마셨습니다.",
                modifier = modifier.padding(16.dp)
            )

        }
        Row(Modifier.padding(top = 8.dp)) {
            Button(onClick = onIncrement, enabled = count < 10) {
                Text("컵 추가")
            }

        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count = count, onIncrement = { count++ }, modifier = modifier)

}