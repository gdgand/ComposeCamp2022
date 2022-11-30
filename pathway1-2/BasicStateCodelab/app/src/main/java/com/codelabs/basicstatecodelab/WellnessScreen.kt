package com.codelabs.basicstatecodelab

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * BasicStateCodelab
 * @author jaesung
 * @created 2022/11/19
 */

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Log.e("WS", "Recomposition!")
//    StatefulCounter(modifier = modifier)

    Column(modifier = modifier) {
        StatefulCounter()
        WellnessTasksList()
    }
}