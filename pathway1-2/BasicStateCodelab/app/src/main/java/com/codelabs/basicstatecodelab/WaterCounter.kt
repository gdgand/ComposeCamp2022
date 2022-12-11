package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme

@Composable
fun StatelessCounter(
    count: Int,
    onClickAddOne: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        if(count > 0) {
            Text(
                text = "You've had $count glasses."
            )
        }

        Button(
            onClick = onClickAddOne,
            enabled = count < 10
        ) {
            Text(text = "Add one")
        }
    }
}

@Composable
fun StateFulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable {
        mutableStateOf(0)
    }

    StatelessCounter(
        modifier = modifier,
        count = count,
        onClickAddOne = { count++ }
    )
}

@Preview(showBackground = true)
@Composable
fun WaterCounterPreview() {
    BasicStateCodelabTheme {
        StateFulCounter()
    }
}