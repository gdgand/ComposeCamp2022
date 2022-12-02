/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelab.theming.ui.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.theming.R
import com.codelab.theming.data.Post
import com.codelab.theming.data.PostRepo
import com.codelab.theming.ui.start.theme.JetnewsTheme
import java.util.Locale

/**
 * 5. 색상
 * 기본적으로 Color 클래스를 통해 컴포넌트에 원색을 적용할 수 있다.
 * ``` kotlin
 * Surface(color = Color.LightGrey) { .. }
 * ```
 *
 * 테마 색상을 적용하기 위해서는 MaterialTheme에 접근하여 사용한다.
 * ``` kotlin
 * Surface(color = MaterialTheme.colors.primary) { ... }
 * ```
 *
 * copy 메서드를 통해 색상을 파생할 수 있다.
 * ``` kotlin
 * MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
 * ```
 *
 *
 */
@Composable
fun Home(
    darkTheme: Boolean = false
) {
    val featured = remember { PostRepo.getFeaturedPost() }
    val posts = remember { PostRepo.getPosts() }
    JetnewsTheme(
        darkTheme = darkTheme
    ) {
        Scaffold(
            topBar = { AppBar() }
        ) { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                item {
                    Header(stringResource(R.string.top))
                }
                item {
                    FeaturedPost(
                        post = featured,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    Header(stringResource(R.string.popular))
                }
                items(posts) { post ->
                    PostItem(post = post)
                    Divider(startIndent = 72.dp)
                }
            }
        }
    }
}

/**
 * 5. 색상
 * DarkTheme에서는 밝은 색상 영역을 피하도록 권장한다.
 * 일반적으로 LightTheme에서는 primary, DarkTheme에서는 surface를 지정한다.
 * primarySurface로 대응할 수 있다.
 */
@Composable
private fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.Palette,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        },
        title = {
            Text(text = stringResource(R.string.app_title))
        },
        backgroundColor = MaterialTheme.colors.primarySurface
    )
}

/**
 * 5. 색상
 * Text의 배경색을 background로 하드코딩하게되면 Darktheme에서 대비가 올바르게 되지 못함
 * Surface로 감싸 Surface에서 색상을 지정하는 것이 좋음
 */
@Composable
fun Header(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
        contentColor = MaterialTheme.colors.primary,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle2,
            modifier = modifier
                .fillMaxWidth()
                .semantics { heading() }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun FeaturedPost(
    post: Post,
    modifier: Modifier = Modifier
) {
    Card(modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* onClick */ }
        ) {
            Image(
                painter = painterResource(post.imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .heightIn(min = 180.dp)
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            val padding = Modifier.padding(horizontal = 16.dp)
            Text(
                text = post.title,
                style = MaterialTheme.typography.h6,
                modifier = padding
            )
            Text(
                text = post.metadata.author.name,
                style = MaterialTheme.typography.body2,
                modifier = padding
            )
            PostMetadata(post, padding)
            Spacer(Modifier.height(16.dp))
        }
    }
}

/**
 * 5. 색상
 * CompositionLocal을 통해 계층구조의 각 컴포넌트에 적당한 alpha값을 전달할 수 있다.
 * MaterialTheme은 기본으로 ContentAlpha.high로 설정된다.
 *
 * 6. 텍스트 스타일
 * AnnotatedString의 SpanStyle을 통해 텍스트 범위에 따른 스타일을 지정할 수 있다.
 */
@Composable
private fun PostMetadata(
    post: Post,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.body2,
) {
    val divider = "  •  "
    val tagDivider = "  "
    val tagStyle = MaterialTheme.typography.overline.toSpanStyle().copy(
        background = MaterialTheme.colors.primary.copy(alpha = 0.1f)
    )

    val text = buildAnnotatedString {
        append(post.metadata.date)
        append(divider)
        append(stringResource(R.string.read_time, post.metadata.readTimeMinutes))
        append(divider)
        post.tags.forEachIndexed { index, tag ->
            if (index != 0) {
                append(tagDivider)
            }
            withStyle(tagStyle) {
                append(" ${tag.uppercase(Locale.getDefault())} ")
            }
        }
    }

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = text,
            modifier = modifier,
            style = style
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostItem(
    post: Post,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier
            .clickable { /* todo */ }
            .padding(vertical = 8.dp),
        icon = {
            Image(
                painter = painterResource(post.imageThumbId),
                contentDescription = null
            )
        },
        text = {
            Text(text = post.title)
        },
        secondaryText = {
            PostMetadata(post)
        }
    )
}

@Preview("Post Item")
@Composable
private fun PostItemPreview() {
    val post = remember { PostRepo.getFeaturedPost() }
    Surface {
        PostItem(post = post)
    }
}

@Preview("Featured Post")
@Composable
private fun FeaturedPostPreview() {
    val post = remember { PostRepo.getFeaturedPost() }
    JetnewsTheme(darkTheme = false) {
        FeaturedPost(post = post)
    }
}

@Preview("Featured Post -Dark")
@Composable
private fun FeaturedPostDarkPreview() {
    val post = remember { PostRepo.getFeaturedPost() }
    JetnewsTheme(darkTheme = true) {
        FeaturedPost(post = post)
    }
}

@Preview("Home Dark")
@Composable
private fun HomeDarkPreview() {
    Home(darkTheme = true)
}

@Preview("Home")
@Composable
private fun HomePreview() {
    Home()
}

@Preview("AppBar")
@Composable
private fun AppBarPreview() {
    JetnewsTheme(darkTheme = true) {
        AppBar()
    }
}
