# Navigation in Jetpack Compose Codelab

This folder contains the source code for the
[Navigation in Jetpack Compose Codelab](https://developer.android.com/codelabs/jetpack-compose-navigation)
codelab.

## NOTES

- Integrate RallyTabRow with navigation
    - `navController.navigate(route) { launchSingleTop = true }`
    - Instead of copy pasting this flag, extract it into a helper extension

        ```kotlin
        fun NavHostController.navigateSingleTopTo(route: String) =
            this.navigate(route) { launchSingleTop = true }
        ```

    - `launchSingleTop = true`
        - This makes sure there will be at most one copy of a given destination on the top of the back stack
    - `popUpTo(startDestination) { saveState = true }`
        - pop up to the start destination of the graph to avoid building up a large stack of destinations on the back stack as you select tabs
        - 탭을 선택했을 때 백 스택에 대규모 대상 스택이 빌드되지 않도록 그래프의 시작 대상을 팝업으로 만듭니다
    - `restoreState = true`
        - determines whether this navigation action should restore any state previously saved by `PopUpToBuilder.saveState` or the `popUpToSaveState` attribute
        - Note that, if no state was previously saved with the destination ID being navigated to, **this has no effect**
        - 이 탐색 동작이 이전에 `PopUpToBuilder.saveState` 또는 `popUpToSaveState` 속성에 의해 저장된 상태를 복원하는지 여부를 정합니다.
        - 이동할 대상 ID를 사용하여 이전에 저장된 상태가 없다면 **이 옵션은 효과가 없습니다**
- Extract the NavHost into RallyNavHost
    - Every other nested composable screen should only obtain navigation callbacks, not the `navController` itself.

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

