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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * BasicStateCodelab
 * @author jaesung
 * @created 2022/11/30
 */

/**
 * 10. 목록 사용
 * WellnessTaskItem을 Stateless하게 만든다.
 * 체크박스의 상태와 이벤트를 컴포저블의 파라미터로 전달해 Stateless하게 만듦
 *
 * Stateful한 WellnessTaskItem이라면?
 * ```kotlin
 * @Composable
 * fun WellnessTaskItem(...) {
 *     Row(..) {
 *         val checkState by remember { mutableStateOf(false) }
 *         ...
 *         Checkbox(
 *             checked = checkState
 *             onCheckedChange = { newValue ->
 *                 checkedState = newValue
 *             }
 *         )
 *         ...
 *     }
 * }
 * ```
 *
 */
@Composable
fun WellnessTaskItem(
    taskName: String,
    onClose: () -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = taskName,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@Composable
fun WellnessTaskItem(
    taskName: String,
    modifier: Modifier = Modifier,
) {
    var checkedState by rememberSaveable { mutableStateOf(false) }

    WellnessTaskItem(
        taskName = taskName,
        onClose = { /*TODO*/ },
        checked = checkedState,
        onCheckedChange = { newValue -> checkedState = newValue},
        modifier = modifier
    )
}


@Preview(showBackground = true, name = "WellnessTaskItem")
@Composable
fun WellnessTaskItemPreview() {
    var checkState by remember { mutableStateOf(false) }
    WellnessTaskItem(
        taskName = "KKK",
        onClose = { /*TODO*/ },
        checked = checkState,
        onCheckedChange = { newValue ->
            checkState = newValue
        }
    )
}