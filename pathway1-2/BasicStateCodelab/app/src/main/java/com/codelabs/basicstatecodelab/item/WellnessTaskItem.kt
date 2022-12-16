package com.codelabs.basicstatecodelab.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
private fun WellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = taskName,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        )
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        IconButton(onClick = onRemove) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }
}

@Composable
private fun WellnessTaskItem(
    label: String,
    checked: Boolean,
    onRemove: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    WellnessTaskItem(
        taskName = label,
        checked = checked,
        onCheckedChange = onCheckedChange,
        onRemove = onRemove,
        modifier = modifier
    )
}


@Composable
fun WellnessItems(
    items: List<Wellness>,
    onRemove: (Wellness) -> Unit,
    onCheckedChange: (Wellness, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(content = {
        items(
            items = items,
            key = { task -> task.id }
        ) { task ->
            WellnessTaskItem(
                checked = task.checked,
                label = task.label,
                modifier = modifier,
                onRemove = { onRemove(task) },
                onCheckedChange = { checked -> onCheckedChange(task, checked) }
            )
        }
    })
}

class Wellness(
    val id: Int,
    val label: String,
    initialCheck: Boolean = false
) {
    var checked by mutableStateOf(initialCheck)
}
