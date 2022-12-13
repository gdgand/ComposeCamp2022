package com.codelabs.basicstatecodelab
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun StatefulCounter(modifier : Modifier = Modifier){
    var count by rememberSaveable {mutableStateOf(0)}
    //수직으로 배치
    StatelessCounter(count = count, onIncrement={count++}, modifier = modifier)

}

@Composable
//앱에서 재사용 가능
private fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {

        if (count > 0) {
            Text("You've had $count glasses.")
        }

        Button(
            onClick = onIncrement,
            enabled = count < 10,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add one")
        }
    }
}
