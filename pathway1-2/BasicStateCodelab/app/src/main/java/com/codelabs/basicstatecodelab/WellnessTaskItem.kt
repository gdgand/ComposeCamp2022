package com.codelabs.basicstatecodelab

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme


@Composable
fun WellnessTaskItem(
    taskName: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            text = taskName
        )
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@Preview
@Composable
fun WellnessTaskItemPreview(){
    BasicStateCodelabTheme{
        WellnessTaskItem(
            onClose = { },
            taskName = "Have you taken your 15 minute walk today?"
        )
    }
}