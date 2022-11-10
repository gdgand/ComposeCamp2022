package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @Description
 * @author yc.park (DEEP.FINE)
 * @since 2022-11-11
 * @version 1.0.0
 */

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
  Column(modifier = modifier.padding(16.dp)) {
    var count by remember { mutableStateOf(0) }
    if (count > 0) {
      var showTask by remember { mutableStateOf(true) }
      if (showTask) {
        WellnessTaskItem(
          onClose = { showTask = false },
          taskName = "Have you taken your 15 minute walk today?"
        )
      }
      Text("You've has ${count} glasses.")
    }

    Row(Modifier.padding(top = 8.dp)) {
      Button(onClick = { count++ }, enabled = count < 10) {
        Text("Add one")
      }
      Button(onClick = { count = 0 }, Modifier.padding(start = 8.dp)) {
        Text("Clear water count")
      }
    }
  }
}