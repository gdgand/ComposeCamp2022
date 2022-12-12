package com.codelabs.basicstatecodelab.ui.theme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Button
import androidx.compose.foundation.layout.Column

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
  Column(modifier = modifier.padding(16.dp)) {
    var count = 0
    Text("You've had $count glasses.")
    Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
      Text("Add one")
    }
  }
}

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
  WaterCounter(modifier)
}