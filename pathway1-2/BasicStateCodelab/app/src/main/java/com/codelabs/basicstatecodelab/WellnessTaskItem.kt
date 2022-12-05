package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WellnessTaskItem(
    task: WellnessTask,
    modifier: Modifier = Modifier,
    onClose: (WellnessTask) -> Unit,
    onCheckedChange: (WellnessTask, Boolean) -> Unit
) {
//    var checkedState by rememberSaveable { mutableStateOf(false) }

    WellnessTaskItem(
        taskName = task.label,
        checked = task.checked.value,
        modifier = modifier,
        onClose = { onClose.invoke(task) },
        onCheckedChange = { onCheckedChange.invoke(task, it) }
    )
}

@Composable
private fun WellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onClose: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
   Row(
       modifier = modifier,
       verticalAlignment = Alignment.CenterVertically
   ) {
       Text(
           text = taskName,
           modifier = Modifier
               .weight(1f)
               .padding(horizontal = 16.dp),
       )

       Checkbox(
           checked = checked,
           onCheckedChange = { onCheckedChange.invoke(!checked) }
       )

       IconButton(onClick = { onClose.invoke() }) {
           Icon(Icons.Filled.Close, contentDescription = "Close")
       }
   }
}

@Preview(showBackground = true)
@Composable
private fun WellnessTaskItemPreview() {
    WellnessTaskItem(
        task = WellnessTask(
            id = 0,
            label = "TaskName",
            checked = mutableStateOf(true)
        ),
        onClose = { },
        onCheckedChange = { task, checked -> }
    )
}