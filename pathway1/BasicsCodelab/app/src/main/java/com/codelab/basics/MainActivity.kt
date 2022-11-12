package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

/***
 * Material 은 설정한 색상에 맞게 주변 child Compose 들이 자동으로 변한다
 * Modifier -> padding 값 등 수정할 수 있는 수정자를 의미
 ***/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "Hello!")
            Text(text = "$name!")
        }
    }
}


// 기본적으로 함수는 빈 수정자 ( Modifier ) 를 가지고 있는것이 좋다.
@Composable
private fun MyApp(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,

    ) {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greeting("Android")
    }
}