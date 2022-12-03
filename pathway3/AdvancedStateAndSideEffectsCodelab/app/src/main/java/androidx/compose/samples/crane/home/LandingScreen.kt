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
fun LandingScreen(modifier: Modifier = Modifier, onTimeout: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // TODO Codelab: LaunchedEffect and rememberUpdatedState step
        // TODO: Make LandingScreen disappear after loading data
//        LaunchedEffect(onTimeout) {
//            delay(SplashWaitTime) // Simulates loading things
//            onTimeout()
//            // LaunchedEffect와 같은 일부 부작용 API는 다양한 수의 키를 매개변수로 사용하여 이러한 키 중 하나가 변경될 때마다 효과를 다시 시작합니다.
//            // onTimeout이 변경되면 효과를 다시 시작하지 않는 것이 좋습니다.
//        }

        // 부작용이 진행되는 동안 onTimeout이 변경되면 효과가 끝날 때 마지막 onTimeout이 호출된다는 보장이 없습니다.
        // 캡처하고 새 값으로 업데이트하여 이를 보장하려면 rememberUpdatedState API를 사용합니다.
        val currentOnTimeout by rememberUpdatedState(onTimeout)
        LaunchedEffect(true) {
            delay(SplashWaitTime)
            currentOnTimeout()
        }
        Image(painterResource(id = R.drawable.ic_crane_drawer), contentDescription = null)
    }
}
