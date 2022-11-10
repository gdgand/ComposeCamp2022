package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @Description
 * @author yc.park (DEEP.FINE)
 * @since 2022-11-11
 * @version 1.0.0
 */

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
fun StatefulCounter() {
  var waterCount by rememberSaveable { mutableStateOf(0) }

  var juiceCount by rememberSaveable { mutableStateOf(0) }

  StatelessCounter(waterCount, { waterCount++ })
  StatelessCounter(juiceCount, { juiceCount++ })
}