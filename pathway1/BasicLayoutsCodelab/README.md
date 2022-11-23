# Basic Layouts in Compose Codelab

This folder contains the source code for
the [Basic Layouts in Compose Codelab](https://developer.android.com/codelabs/jetpack-compose-layouts)

<br>

## Search Bar
- modifier는 매개변수로 받아서 TextField에 전달하는 것이 Compose 권장 사항
- 컴포저블 호출자가 디자인과 분위기 수정할 수 있어 유연성 높아지고 재사용 가능
```kotlin
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
               imageVector = Icons.Default.Search,
               contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        placeholder = {
            Text(stringResource(id = R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
    )
}
```
- TextField에 다른 컴포저블을 받을 수 있는 leadingIcon 존재

<br>

## Alignment
- Column
    - Start, CenterHorizontally, End
- Row
    - Top, CenterVertically, Bottom
- Box
    - TopStart, TopCenter, TopEnd, CenterStart, Center, CenterEnd, BottomStart, BottomCenter, BottomEnd

<br>

## Slot API
- 개발자가 원하는 대로 채울 수 있도록 UI에 빈 공간 남겨둠.
- 유연한 UI 개발 가능
- Scaffold : Material Design을 구현하는 앱을 위한 구성 가능한 최상위 수준 컴포저블 제공

<br>

## Scroll
- 일반적으로 LazyRow, LazyHorizontalGrid와 같은 Lazy 레이아웃은 자동 스크롤 동작 추가
- 하지만, Lazy 레이아웃은 데이터가 많거나 로드할 데이터가 많아서 동시에 보여질 경우 성능 이슈 시에 사용
- 데이터가 많지 않을 경우는 Column, Row 사용하고 스크롤 동작 추가
- verticalScroll 또는 horizontalScroll 수정자 사용, 스크롤 현재 상태를 포함하며 외부에서 스크롤 상태 수정하는 데 사용되는 ScrollState 필요

<br>

<br>

## License

```
Copyright 2022 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Image asset attributions

[fc1_short_mantras.jpg](https://www.pexels.com/photo/body-of-water-view-1825206/) - Elizaveta Kozorezova
[fc2_nature_meditations.jpg](https://www.pexels.com/photo/photo-of-green-leaves-3571551/) - Nothing Ahead
[fc3_stress_and_anxiety.jpg](https://www.pexels.com/photo/aerial-view-of-body-of-water-1557238/) - Jim
[fc4_self_massage.jpg](https://www.pexels.com/photo/photography-of-stones-1029604/) - Scott Webb
[fc5_overwhelmed.jpg](https://www.pexels.com/photo/white-clouds-3560044/) - Ruvim
[fc6_nightly_wind_down.jpg](https://www.pexels.com/photo/time-lapse-photo-of-stars-on-night-924824/) - Jakub Novacek
[ab1_inversions.jpg](https://www.pexels.com/photo/low-angle-view-of-woman-relaxing-on-beach-against-blue-sky-317157/) - Chevanon Photography
[ab2_quick_yoga.jpg](https://www.pexels.com/photo/photo-of-woman-stretching-her-body-1812964/) - Agung Pandit Wiguna
[ab3_stretching.jpg](https://www.pexels.com/photo/photo-of-women-stretching-together-4056723/) - Cliff Booth
[ab4_tabata.jpg](https://www.pexels.com/photo/fashion-man-people-art-4662438/) - Elly Fairytale
[ab5_hiit.jpg](https://www.pexels.com/photo/man-wearing-white-pants-under-blue-sky-999309/) - The Lazy Artist Gallery
[ab6_pre_natal_yoga.jpg](https://www.pexels.com/photo/woman-doing-yoga-396133/) - freestocks.org