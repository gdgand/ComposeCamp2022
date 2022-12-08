package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme

@Composable
fun WellnessTaskItem(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    taskName: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            taskName, Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Checkbox(checked, onCheckedChange)
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, "Close")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WellnessTaskItemPreview() {
    BasicStateCodelabTheme {
        WellnessTaskItem(
            checked = false,
            onCheckedChange = {},
            onClose = {},
            taskName = "Have you taken 15 minute walk today?"
        )
    }
}
