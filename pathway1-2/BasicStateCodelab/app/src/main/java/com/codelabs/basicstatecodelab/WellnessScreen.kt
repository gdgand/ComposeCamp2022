package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * @Description
 * @author yc.park (DEEP.FINE)
 * @since 2022-11-11
 * @version 1.0.0
 */

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    StatefulCounter()
    WellnessTasksList()
  }
}