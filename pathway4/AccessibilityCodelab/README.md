# Accessibility in Jetpack Compose Codelab

This folder contains the source code for
the [Accessibility in Jetpack Compose Codelab](https://developer.android.com/codelabs/jetpack-compose-accessibility)

## NOTES

- Infinite animations are a special case that Compose tests understand so they're not going to keep the test busy

- Touch target size 48dp

- The order of modifier functions is significant.
    - Since each function makes changes to the Modifier returned by the previous function, the sequence affects the final result.
    - In this case, we apply the padding before setting the size, but after applying the clickable modifier.
    - This way the padding will be added to the size, and the whole element will be clickable.
- In our use case, there is an easier way to make sure the touch target is at least 48dp.
    - We can make use of the Material component `IconButton` that will handle this for us.

## License

```
Copyright 2021 The Android Open Source Project

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
