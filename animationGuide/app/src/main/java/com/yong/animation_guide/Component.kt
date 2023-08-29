package com.yong.animation_guide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultBox(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.size(100.dp).background(Color.Blue)) {

    }
}