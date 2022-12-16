package com.bean.compose.basicstatecodelab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bean.compose.basicstatecodelab.ui.theme.BasicStateCodelabTheme
import androidx.compose.runtime.*

// stateful
@Composable
fun WellnessTaskItem(
    modifier: Modifier = Modifier,
    taskName: String,
    onClose: () -> Unit,
) {
    // composition에서 전부 out되는 상황에서도 값을 살리기에 좋음
    var checked by rememberSaveable { mutableStateOf(false) }

    WellnessTaskItem(
        modifier = modifier,
        taskName = taskName,
        checked = checked, // state가 아래로감.
        onCheckChanged = { newValue -> checked = newValue }, // event가 올라옴
        onClose = onClose
    )
}

// stateless
@Composable
fun WellnessTaskItem(
    modifier: Modifier = Modifier,
    taskName: String,
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit,
    onClose: () -> Unit
) {
    Surface {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = taskName, modifier = Modifier.weight(1f))
            Checkbox(checked = checked, onCheckedChange = onCheckChanged)
            IconButton(onClick = onClose) {
                Icon(Icons.Filled.Close, contentDescription = "Close")
            }
        }
    }
}

@Preview(widthDp = 320)
@Composable
fun TaskItemPreview() {
    BasicStateCodelabTheme {
        WellnessTaskItem(Modifier, "This is a task", {})
    }
}