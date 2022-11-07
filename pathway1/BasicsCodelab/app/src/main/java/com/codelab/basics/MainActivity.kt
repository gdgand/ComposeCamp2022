package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basiclayouts.OnboardingScreen
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
private fun MyApp() {
	var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

	if (!shouldShowOnboarding) Greetings() else OnboardingScreen {
		shouldShowOnboarding = false
	}
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" }) {
	LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
		items(names) { name ->
			CardContent(name = name)
		}
	}
}

@Composable
private fun CardContent(name: String) {
	val (expanded, setExpand) = remember { mutableStateOf(false) }

	Row(
		modifier = Modifier
			.padding(12.dp)
			.animateContentSize(
				animationSpec = spring(
					stiffness = Spring.StiffnessLow,
					dampingRatio = Spring.DampingRatioMediumBouncy
				)
			)
	) {
		Column(
			modifier = Modifier
				.weight(1f)
				.padding(12.dp)
		) {
			Text(text = "Hello, ")
			Text(
				text = name,
				style = MaterialTheme.typography.h4.copy(
					fontWeight = FontWeight.ExtraBold
				)
			)
			if (expanded) Text(
				text = ("Composem ipsum color sit lazy, " +
						"padding theme elit, sed do bouncy. ").repeat(4)
			)
		}
		IconButton(
			onClick = { setExpand(!expanded) }
		) {
			Icon(
				imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
				contentDescription = if (expanded) {
					stringResource(R.string.show_less)
				} else {
					stringResource(R.string.show_more)
				}
			)
		}
	}
}

@Preview(
	widthDp = 320,
	showBackground = true,
	uiMode = UI_MODE_NIGHT_YES,
	name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
private fun DefaultPreview() {
	BasicsCodelabTheme {
		MyApp()
	}
}