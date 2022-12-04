package com.codelabs.basicstatecodelab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * @Description
 * @author yc.park (DEEP.FINE)
 * @since 2022-11-11
 * @version 1.0.0
 */
class WellnessTask(
  val id: Int,
  val label: String,
  initialChecked: Boolean = false
) {
  var checked by mutableStateOf(initialChecked)
}