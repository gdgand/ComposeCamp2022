# Migrating to Jetpack Compose

This folder contains the source code for the [Migrating to Jetpack Compose codelab](https://developer.android.com/codelabs/jetpack-compose-migration).

The codelab which migrates parts of [Sunflower](https://github.com/android/sunflower)'s Plant
details screen to Jetpack Compose is built in multiple GitHub branches:

* `main` is the codelab's starting point.
* `end` contains the solution to this codelab.

## Pre-requisites
* Experience with Kotlin syntax, including lambdas.
* Knowing the [basics of Compose](https://developer.android.com/codelabs/jetpack-compose-basics/).

## Getting Started
1. Install the latest Android Studio [canary](https://developer.android.com/studio/preview/).
2. Download the sample.
3. Import the sample into Android Studio.
4. Build and run the sample.


## Screenshots

![List of plants](screenshots/phone_plant_list.png "A list of plants")
![Plant details](screenshots/phone_plant_detail.png "Details for a specific plant")
![My Garden](screenshots/phone_my_garden.png "Plants that have been added to your garden")

## License

```
Copyright (C) 2020 The Android Open Source Project

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
```
## NOTES

- xml
    
    ```xml
    <androidx.compose.ui.platform.ComposeView
                    android:id="@+id/compose_view"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"/>
    ```
    
- PlantDetailFragment.kt
    
    ```kotlin
    binding.apply {
    		composeView.setContent {
    				MaterialTheme {
    						PlantDetailDescription()
    				}
    		}
    }
    ```
    
- ViewModel, LiveData
    - `PlantDetailDescription(plantDetailViewModel)`
- HTML을 지원하지 않음
    - AndroidView를 이용해서 우회
- ViewCompositionStrategy
    - Compose는 ComposeView가 창에서 분리될 때마다 Composition을 삭제한다.
    - 여러가지 이유로 Fragment에서 ComposeView가 사용될 때에는 바람직하지 않다.
