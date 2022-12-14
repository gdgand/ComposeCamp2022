package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Created by JeongJaeHwan on 2022/11/09
 **/
@Composable
fun StatelessCount(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
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
fun StatefulCounter(modifier: Modifier = Modifier) {
  var waterCount by rememberSaveable { mutableStateOf(0) }
  var juiceCount by rememberSaveable { mutableStateOf(0) }
  StatelessCount(waterCount, { waterCount++ }, modifier)
  StatelessCount(juiceCount, { juiceCount++ }, modifier)
}


@Composable
fun WellnessScreen(
  modifier: Modifier = Modifier,
  viewModel : WellnessViewModel = viewModel()
) {
  Column(modifier = modifier) {
    StatefulCounter()
    WellnessTasksList(
      list = viewModel.tasks,
      onCheckedTask = { task, checked ->
        viewModel.changeTaskChecked(task, checked)
      },
      onCloseTask = {
          task -> viewModel.remove(task)
      }
    )
  }
}