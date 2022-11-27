package com.codelabs.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicStateCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WellnessScreen()
                }
            }
        }
    }
}

@Composable
fun WaterCount(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        val count: MutableState<Int> = remember { mutableStateOf(0) }

        Text(text = "you've had ${count.value} glasses")
        Button(
            onClick = { count.value++ },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Add one.")
        }
    }
}

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    WaterCount(modifier)
}

@Preview(showBackground = true)
@Composable
fun WellnessScreenPreview() {
    BasicStateCodelabTheme {
        WellnessScreen(Modifier)
    }
}