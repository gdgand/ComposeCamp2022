package com.yong.animation_guide

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt

@Composable
fun DefaultBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .background(Color.Blue)
    )
}

@Composable
fun RandomColorBox(
    modifier : Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .background(color = color)
    )
}

@Composable
fun ErrorScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // [START_EXCLUDE]
        Text("Error", fontSize = 18.sp)
        // [END_EXCLUDE]
    }
}

@Composable
fun LoadedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // [START_EXCLUDE]
        Text("Loaded", fontSize = 18.sp)
        Image(
            painterResource(id = R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentDescription = "dog",
            contentScale = ContentScale.Crop
        )
        // [END_EXCLUDE]
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Loading", fontSize = 18.sp)
    }
}

@Composable
fun ScreenLanding(onItemClicked: (Int) -> Unit) {
    val randomColor = listOf(Color.Blue, Color.Green, Color.Red, Color.Gray, Color.Yellow)

    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            itemsIndexed(randomColor) { index, content ->
                RandomColorBox(
                    modifier = Modifier.clickable { onItemClicked(index) },
                    color = content
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenDetails(index: Int, onBackClicked: () -> Unit) {
    val randomColor = listOf(Color.Blue, Color.Green, Color.Red, Color.Gray, Color.Yellow)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Box Details")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClicked() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->


        Column(modifier = Modifier.padding(padding)) {
            RandomColorBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                color = randomColor[index]
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text("Box details", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        }
    }
}
