# Basic Layouts in Compose Codelab

This folder contains the source code for
the [Basic Layouts in Compose Codelab](https://developer.android.com/codelabs/jetpack-compose-layouts)

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

## Basic layouts in Compose

- Modifier
    - `fillMaxWidth(fraction: Float = 1.0f)` - match parent
    - `heightIn(min: Dp = Dp.Unspecified, max: Dp = Dp.Unspecified)`

- Image
    - ContentScale
    - <img width="401" alt="%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-11-12_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_12 12 40" src="https://user-images.githubusercontent.com/87517193/201391461-fc21b16a-9e78-4d0b-b25b-111dbd3811e9.png">
    
    
- LazyRow
    - `Arrangement.spacedBy(8.dp)`
        - Add a fixed space in between each child composable
    - `contentPadding = PaddingValues(horizontal = 16.dp)`
- LazyHorizontalGrid
- Slot APIs
- Use a Lazy layout when you have many elements in a list or large data sets to load
- Scaffold
    - **top-level configurable composable** for apps that implement Material design
