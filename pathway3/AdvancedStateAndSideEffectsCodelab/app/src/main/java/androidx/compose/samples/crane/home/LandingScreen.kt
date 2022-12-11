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

package androidx.compose.samples.crane.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.samples.crane.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000

@Composable
// 백그라운드에서 필요한 모든 데이터를 로드하는 데 사용될 수 있는 랜딩 화면
fun LandingScreen(modifier: Modifier = Modifier, onTimeout: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // TODO Codelab: LaunchedEffect and rememberUpdatedState step
        // TODO: Make LandingScreen disappear after loading data
        //Image(painterResource(id = R.drawable.ic_crane_drawer), contentDescription = null)

        // This will always refer to the latest onTimeout function that
        // LandingScreen was recomposed with
        val currentOnTimeout by rememberUpdatedState(onTimeout)

        // Start a side effect to load things in the background
        // and call onTimeout() when finished.
        // Passing onTimeout as a parameter to LaunchedEffect
        // is wrong! Don't do this. We'll improve this code in a sec.
        //컴포저블 내에 안전하게 정지함수 호출하려면 사용되는 api
        LaunchedEffect(true) {
            delay(SplashWaitTime) // Simulates loading things
            //ontimeout이 변경되면 효과를 다시 시작하지 않는 것이 좋음
            //효과가 끝날 때 마지막 onTimeout이 호출된다는 보장이 없음
            //따라서 이를 보장하려면 rememberUpdatedState API 사용
            currentOnTimeout()
        }
        Image(painterResource(id = R.drawable.ic_crane_drawer), contentDescription = null)
    }
}
