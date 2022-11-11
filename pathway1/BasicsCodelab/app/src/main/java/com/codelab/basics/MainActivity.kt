package com.codelab.basics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
//    Surface(color = MaterialTheme.colors.background) {
//        GreetingColumn(name = "Android")
//    }

    Column(modifier = Modifier.padding(4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Log.e("GREETING", "CALLED")
    val expanded = remember { mutableStateOf(false) }  // Recomposition시 상태 기억; remember
    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
        color = MaterialTheme.colors.primary
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f).padding(bottom = extraPadding)) {
                Text(text = "Hello,")
                Text(text = name)
            }

            OutlinedButton(onClick = {
                expanded.value = !expanded.value
                Log.e("EXPAND", "${expanded.value}")

            }) {
                Text(text = if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp()
    }
}