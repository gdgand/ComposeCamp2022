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
fun WellnessScreen(modifier: Modifier = Modifier) {
    WaterCounter(modifier) {

    }
}

@Composable
private fun WaterCounter(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        var count = 0
        Text(text = "You've had $count glasses.")
        Button(
            onClick = onClick,
            modifier = modifier.padding(top = 8.dp)
        ) {
            Text(text = "Add one")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicStateCodelabTheme {
        WellnessScreen()
    }
}
