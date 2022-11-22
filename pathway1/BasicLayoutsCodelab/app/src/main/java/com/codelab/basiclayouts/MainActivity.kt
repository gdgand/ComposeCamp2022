/*
 * Copyright 2022 The Android Open Source Project
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

package com.codelab.basiclayouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basiclayouts.ui.theme.MySootheTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MySootheApp() }
    }
}

// Step: Search bar - Modifiers
// 검색창을 구현 하기 위해 Compose Material에는 TextField라는 Composable이 있음.
// 텍스트 필드 값은 하드코딩, onValueChanger 콜백은 아무런 작업도 하지 않음.
/*
- modifier(수정자):
역할
1. Composable의 크기, 레이아웃, 동작, 모양을 변경
2. 접근성 라벨과 같은 정보 추가
3. 사용자 입력 처리
4. 요소 클릭, 스크롤, 드래그 가능 or 확대/축소를 가능하게 만드는것과 같은 높은 수준의 상호 작용 추가

 */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "" ,
        onValueChange = {},
        /*
        leadingIcon = 다른 Composable을 받는 매개변수 leadingIcon, 내부에는 Icon 설정 가능.
        Search 아이콘 설정, textField의 배경색을 surface로 설정.
         */
        leadingIcon = {
            Icon(
               imageVector = Icons.Default.Search,
               contentDescription = null
            )
        },
        // textField의 배경색을 MaterialTheme의 surface 색으로 설정.
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        // string.xml에 정의된 텍스트를 가져옴.
        placeholder = {
            Text(stringResource(id = R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp))     // textField의 높이 최소 56dp 설정
}

// Step: Align your body - Alignment
// 신체의 조화 - 정렬 구현,
/*
- 이미지의 높이 88dp, 텍스트 기준선과 이미지 기준선의 간격은 24dp, 기준선과 요소 하단 사이의 간격은 8dp
- 서체 스타일은 H3, 텍스트의 기준선 -> 문자가 놓여 있는 선을 가리킴.
해당 Composable 구현을 위해서는 Image와 text Composable이 필요한데 두 Composable을 세로 방향으로 배치.

- size와 clip, contentScale을 통해 Image Composable을 수정.
1. size : fillMaxWidth와 heightIn과 마찬가지로 특정 크기에 맞게 Composable을 조정.
2. clip : Composable의 모양을 조정.
3. 이미지의 크기를 올바르게 조절하기 위해서 Image의 contentScale을 사용.
ex) Fit, FillBounds, Crop 등이 있음.
 */
@Composable
fun AlignYourBodyElement(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    // 세로 방향으로 배치하기 위해 Column Scope로 작성.
    // 설명이 필요없는 장식용 이미지일경우 contentDescription 옵션을 null로 처리해줌.
    // 추가 설명이 필요할 경우에만 사용
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally     // 글자 정렬
    ) {
        Image(
              painter = painterResource(drawable),
              contentDescription = null,
              contentScale = ContentScale.Crop,
              modifier = Modifier
                  .size(88.dp)          // 사이즈 88dp
                  .clip(CircleShape)    // 원 모양으로
        )
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.h3,        // 서체를 h3로
            modifier = Modifier.paddingFromBaseline(    // 텍스트 요소의 기준선 간격을 업데이트
                top = 24.dp, bottom = 8.dp          // top : 24dp, bottom: 8dp
            )

        )
    }
}

// Step: Favorite collection card - Material Surface
@Composable
fun FavoriteCollectionCard(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,      // 수직 정렬
            modifier = Modifier.width(192.dp)
        ) {
            Image(
                painter = painterResource(drawable) ,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(56.dp)
            )
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}

// Step: Align your body row - Arrangements
/*
LazyRow의 하위 요소는 Composable이 아님.
대신 Composable을 목록 항목으로 내보내는 item 및 items와 같은 메서드를 제공하는 Lazy 목록 DSL을 사용.
alignment
 */
@Composable
fun AlignYourBodyRow(
    modifier: Modifier = Modifier
) {
   LazyRow(
       horizontalArrangement = Arrangement.spacedBy(8.dp),
       contentPadding = PaddingValues(horizontal = 16.dp) ,
       modifier = modifier
   ) {
       items(alignYourBodyData) { item ->
           AlignYourBodyElement(item.drawable, item.text)
       }
   }
}

// Step: Favorite collections grid - LazyGrid
@Composable
fun FavoriteCollectionsGrid(
    modifier: Modifier = Modifier
) {
    // Implement composable here
}

// Step: Home section - Slot APIs
@Composable
fun HomeSection(
    modifier: Modifier = Modifier
) {
    // Implement composable here
}

// Step: Home screen - Scrolling
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // Implement composable here
}

// Step: Bottom navigation - Material
@Composable
private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    // Implement composable here
}

// Step: MySoothe App - Scaffold
@Composable
fun MySootheApp() {
    // Implement composable here
}

private val alignYourBodyData = listOf(
    R.drawable.ab1_inversions to R.string.ab1_inversions,
    R.drawable.ab2_quick_yoga to R.string.ab2_quick_yoga,
    R.drawable.ab3_stretching to R.string.ab3_stretching,
    R.drawable.ab4_tabata to R.string.ab4_tabata,
    R.drawable.ab5_hiit to R.string.ab5_hiit,
    R.drawable.ab6_pre_natal_yoga to R.string.ab6_pre_natal_yoga
).map { DrawableStringPair(it.first, it.second) }

private val favoriteCollectionsData = listOf(
    R.drawable.fc1_short_mantras to R.string.fc1_short_mantras,
    R.drawable.fc2_nature_meditations to R.string.fc2_nature_meditations,
    R.drawable.fc3_stress_and_anxiety to R.string.fc3_stress_and_anxiety,
    R.drawable.fc4_self_massage to R.string.fc4_self_massage,
    R.drawable.fc5_overwhelmed to R.string.fc5_overwhelmed,
    R.drawable.fc6_nightly_wind_down to R.string.fc6_nightly_wind_down
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun SearchBarPreview() {
    MySootheTheme { SearchBar(Modifier.padding(8.dp)) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AlignYourBodyElementPreview() {
    MySootheTheme {
        AlignYourBodyElement(
            text = R.string.ab1_inversions,
            drawable = R.drawable.ab1_inversions,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FavoriteCollectionCardPreview() {
    MySootheTheme {
        FavoriteCollectionCard(
            text = R.string.fc2_nature_meditations,
            drawable = R.drawable.fc2_nature_meditations,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FavoriteCollectionsGridPreview() {
    MySootheTheme { FavoriteCollectionsGrid() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AlignYourBodyRowPreview() {
    MySootheTheme { AlignYourBodyRow() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun HomeSectionPreview() {
    MySootheTheme { HomeSection() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun ScreenContentPreview() {
    MySootheTheme { HomeScreen() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun BottomNavigationPreview() {
    MySootheTheme { SootheBottomNavigation(Modifier.padding(top = 24.dp)) }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MySoothePreview() {
    MySootheApp()
}
