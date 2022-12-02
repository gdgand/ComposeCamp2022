# Advanced State in Jetpack Compose Codelab

This folder contains the source code for the
[Advanced State in Jetpack Compose Codelab](https://developer.android.com/codelabs/jetpack-compose-advanced-state-side-effects)
codelab.

The project is built in multiple git branches:
* `main` – the starter code for this project, you will make changes to this to complete the codelab
* `end` – contains the solution to this codelab

## NOTES

[https://developer.android.com/jetpack/compose/side-effects](https://developer.android.com/jetpack/compose/side-effects)

- Comsuming a Flow from the ViewModel
    - `collectAsState()` collects values from the `StateFlow` and represents the latest value via Compose's `State` API.
    - This will make the Compose code that reads that state value recompose on new emissions.
- LaunchedEffect and rememberUpdatedState
    - **LaunchedEffect** - run suspend functions in the scope of a composable
        - `LauchedEffect` enters the Composition, it launches a coroutine with the block of code passed as a parameter
        - The coroutine will be cancelled if `LaunchedEffect` leaves the composition
        - If `LaunchedEffect` is recomposed with different keys, the existing coroutine will be cancelled and the new suspend function will be launched in a new coroutine
    - **rememberUpdatedState** - reference a value in an effect that shouldn't restart if the value changes
        - `LaunchedEffect` restarts when one of the key parameters changes
        - However, in some situations you might want to capture a value in your effect, if it changes, you do not want the effect to restart
        - If access is required from inside the block of `LaunchedEffect`, but to prevent the `LaunchedEffect` from restarting, the value must be wrapped in the `rememberUpdatedState`
- rememberCoroutineScope
    - Obtain a composition-aware scope to launch a coroutine outside a composable
    - `rememberCoroutineScope` is a composable function that returns a CoroutineScope bound to the point of the Composition where it’s called
    - The scope will be cancelled when the call leaves the Composition.
- Creating a state holder
    - `rememberSaveable` API behaves similarly to `remember`, but the stored value also survives activity and process recreation
    - `rememberSaveable` does all this with no extra work for objects that can be stored inside a `Bundle`, but that’s not the case for the custom class we made
    - We need to tell `rememberSaveable` how to save and restore an instance of this class using a `Saver`
- DisposableEffect
    - MapView is a View and not a Composable, we want it to follow the lifecycle of the Activity where it’s used instead of the lifecycle of the Composition
    - We need `LifecycleEventObserver` to listen for lifecycle events
    - LifecycleEventObserver need to be able to add and also remove
    - `DisposableEffect` is meant for side effects that need to be cleaned up after the keys change or the composable leaves the Composition
- produceState
    - If we wanted to move the `uiState` mapping logic to the Compose world, we could use the `produceState` API
    - `produceState` allows you to convert non-Compose state into Compose State
    - It launches a coroutine scoped to the Composition that can push values into the returned `State` using the `value` property
    - As with LaunchedEffect, produceState also takes keys to cancel and restart the computation
- derivedStateOf
    - `derivedStateOf` is used when you want a Compose `State` that's derived from another `State`
    - Using this function guarantees that the calculation will only occur whenever one of the states used in the calculation changes


## [Optional] Google Maps SDK setup

Seeing the city on the MapView is not necessary to complete the codelab. However, if you want
to get the MapView to render on the screen, you need to get an API key as
the [documentation says](https://developers.google.com/maps/documentation/android-sdk/get-api-key),
and include it in the `local.properties` file as follows:

```
google.maps.key={insert_your_api_key_here}
```

When restricting the Key to Android apps, use `androidx.compose.samples.crane` as package name, and
`A0:BD:B3:B6:F0:C4:BE:90:C6:9D:5F:4C:1D:F0:90:80:7F:D7:FE:1F` as SHA-1 certificate fingerprint.

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
