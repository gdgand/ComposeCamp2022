package com.codelabs.basicstatecodelab

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * BasicStateCodelab
 * @author jaesung
 * @created 2022/11/19
 */

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Log.e("WC", "Recomposition!")

    Column(modifier = modifier.padding(16.dp)) {
//        val count = mutableStateOf(0)  // Recomposition이 발생할 때마다 0으로 초기화됨

        // remember를 통해 recomposition이 발생하더라도 이전 상태를 기억할 수 있음
        var count by remember {
            mutableStateOf(0)
        }

        if (count > 0) {
            Text(text = "You've had $count glasses")
        }
        Button(
            onClick = { count++ },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add one")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WaterCounterPreview() {
    WaterCounter()
}