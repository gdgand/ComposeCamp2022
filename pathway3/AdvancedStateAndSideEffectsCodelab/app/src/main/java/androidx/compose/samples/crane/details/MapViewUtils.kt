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

package androidx.compose.samples.crane.details

import android.os.Bundle
import androidx.annotation.FloatRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.samples.crane.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView

/**
 * Remembers a MapView and gives it the lifecycle of the current LifecycleOwner
 * 함수가 수명주기에 연결되어 있지 않음, 단순히 MapView를 기억하고 onCreate를 호출.
 * MapView가 올바른 수명주기를 따르지 않기 때문에 문제가 된다.
 * 따라서 앱이 언제 Background로 이동하는지, 뷰가 언제 일시 중지가 되어야 하는지 알 수 없음.
 */
/**
 * Observer를 현재 수명 주기에 추가, LifecycleOwner를 LocalLifecycleOwner 컴포지션 로컬과 함께 사용해 Observer를
 * 가져옴. But, Observer를 삭제할 수 있어야 함.
 * Composition을 종료하는 시기를 알려줄 수 있어야 함. 따라서 필요한 API => DisposableEffect
 * 이 API는 키가 변경되거나 Composable이 Composition을 종료할 경우 정리되어야 하는 부분을 위함.
 */
@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    // TODO Codelab: DisposableEffect step. Make MapView follow the lifecycle
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

    /**
     * Observer는 현재 lifecycle에 추가되고, 현재 수명주기가 변경되거나 이 Composable이 Composition을 종료할 때 마다
     * 삭제됨. DisposableEffect의 key를 사용하고 lifecycle or mapView가 변경되면 Observer가 삭제되고 오른쪽
     * lifeCycle에 다시 추가된다.
     * MapView는 항상 현재 LifecycleOwner의 lifecycle을 따르며, 그 동작은 View에서의 동작과 똑같다.
     */
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(key1 = lifecycle, key2 = mapView) {
        val lifecycleObserver = getMapLifecycleObserver(mapView)
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    return mapView
}

fun GoogleMap.setZoom(
    @FloatRange(from = MinZoom.toDouble(), to = MaxZoom.toDouble()) zoom: Float
) {
    resetMinMaxZoomPreference()
    setMinZoomPreference(zoom)
    setMaxZoomPreference(zoom)
}

/**
 * MapView는 Composable이 아닌 뷰 이기 때문에 Composition의 수명 주기 대신 Activity 생명 주기를 따르는 것이 좋음.
 * 즉, 생명 주기 이벤트를 수신 대기하고 MapView에서 올바른 메서드를 호출하기 위해 LifecycleEventObserver를 구현
 * 그 후 이 Observer를 현재 활동 수명주기에 추가.
 * 특정 이벤트에 따라 MapView에서 해당 메서드를 호출하는 LifecycleEventObserver를 반환하는 함수
 */

private fun getMapLifecycleObserver(mapView: MapView): LifecycleObserver =
    LifecycleEventObserver {_, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> throw java.lang.IllegalStateException()
        }

    }
