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
    taskName: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(text = taskName,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )
        Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
        IconButton(onClick = { onClose() }) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "close"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WellnessTaskItemPreview() {
    BasicStateCodelabTheme {
        WellnessTaskItem("This is task", onCheckedChange = {}, onClose = {}, isChecked = false)
    }
}
