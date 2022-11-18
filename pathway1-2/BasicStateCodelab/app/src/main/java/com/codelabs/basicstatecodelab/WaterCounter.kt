package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        var count by remember { mutableStateOf(0) }

        if (count > 0) {
            var showTask by remember { mutableStateOf(true) }
            if (showTask) {
                WellnessTaskItem(
                    onClose = { showTask = false },
                    taskName = stringResource(id = R.string.wc_task)
                )
            }
            Text(stringResource(R.string.wc_counter, count))
        }
        Row(Modifier.padding(top = 8.dp)) {
            Button(
                onClick = { count++ },
                enabled = count < 10
            ) {
                Text(stringResource(id = R.string.wc_add))
            }
            Button(
                onClick = { count = 0 },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(stringResource(id = R.string.wc_clear))
            }
        }
    }
}