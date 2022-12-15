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
        val currentOnTimeout by rememberUpdatedState(onTimeout)
        // TODO: Make LandingScreen disappear after loading data
        // Compose의 side effect : Composable 함수 범위 밖에서 발생하는 앱 상테에 관한 변경사항
        // onTimeout 콜백 : 랜딩 화면 표시/숨기기를 위해 상태를 변경하는 것이 발생
        // onTimeout 호출 전에 코루틴을 통해 항목을 로드해야 하므로 코루틴의 context에서 상태 변경이 발생해야 함

        // 수명 주기 동안 1번만 트리거하기 위해서 상수를 키로 사용
        // onTimeout이 변경되면서 효과가 끝날 때 마지막 onTimeout이 호출된다는 보장 X -> rememberUpdatedState 사용
        LaunchedEffect(true) {
            delay(SplashWaitTime)  // 임시
            currentOnTimeout()
        }
        Image(painterResource(id = R.drawable.ic_crane_drawer), contentDescription = null)
    }
}
