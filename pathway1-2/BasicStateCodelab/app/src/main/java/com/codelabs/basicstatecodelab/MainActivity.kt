package com.codelabs.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * 경고: setContent 내 MainActivity에서 사용되는 앱 테마는 프로젝트 이름에 맞게 지정됩니다.
         * 이 Codelab에서는 프로젝트 이름이 BasicStateCodelab이라고 가정합니다.
         * Codelab에서 코드를 복사하여 붙여넣는 경우 ui/Theme.kt 파일에서 사용할 수 있는 테마 이름으로 BasicStateCodelab을 업데이트해야 합니다.
         */
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
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicStateCodelabTheme {
        Greeting("Android")
    }
}