package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier : Modifier = Modifier){
    Column(modifier = modifier.padding(16.dp)) {

        var count by rememberSaveable{ mutableStateOf(0) }

        if(count > 0){
            Text(text = "you've had $count glasses")
        }
        Row(Modifier.padding(top = 8.dp)) {
            Button(onClick = { count++ }, enabled = count < 10) {
                Text(text = "Add one")
            }
            Button(onClick = { count = 0 },Modifier.padding(start = 8.dp)) {
                Text(text = "Clear water count")
            }
        }
    }

}

