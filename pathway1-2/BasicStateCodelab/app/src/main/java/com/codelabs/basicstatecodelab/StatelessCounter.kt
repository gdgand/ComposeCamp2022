package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatelessCounter(
    modifier: Modifier = Modifier,
    count: Int,
    onIncrement: () -> Unit
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text(
                text = "You've had $count glasses.",
            )
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text(text = "Add one")
        }
    }
}