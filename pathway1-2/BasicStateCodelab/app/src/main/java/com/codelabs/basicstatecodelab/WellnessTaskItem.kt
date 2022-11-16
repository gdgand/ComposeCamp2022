package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WellnessTaskItem(
    taskName: String,
    isChecked: Boolean,
    onCheckChanged: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            text = taskName
        )
        Checkbox(checked = isChecked, onCheckedChange = onCheckChanged)
        IconButton(onClick = onClose) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@Composable
fun WellnessTaskItem(taskName: String, modifier: Modifier = Modifier) {
    var checkedState by rememberSaveable { mutableStateOf(false) }

    WellnessTaskItem(
        taskName = taskName,
        isChecked = checkedState,
        onCheckChanged = { newValue -> checkedState = newValue },
        onClose = {}, // we will implement this later!
        modifier = modifier,
    )
}