package com.codelab.basiclayouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

@Composable
fun OnboardingScreen(
	onContinueClicked: () -> Unit
) {
	Surface {
		Column(
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text("Welcome to the Basics Codelab!")
			Button(
				onClick = onContinueClicked,
				modifier = Modifier.padding(vertical = 24.dp)
			) {
				Text("Continue")
			}
		}
	}
}

@Composable
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
fun OnboardingPreview() {
	BasicsCodelabTheme {
		OnboardingScreen {}
	}
}