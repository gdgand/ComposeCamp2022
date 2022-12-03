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
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView

/**
 * Remembers a MapView and gives it the lifecycle of the current LifecycleOwner
 */
// map 뷰는 composable 이 나닌 뷰이므로, 컴포지션의 수명 주기 대신 사용되는 액티비티의 수명 주기를 따르는 것이 좋다.
// 즉 수명 주기 이벤트를 수신 대기하고 map view 에서 올바른 메소드를 호출하기 위해 LivecycleEventObserver 를 만들어야 한다.
// 그런 다음 이 관찰자를 현재 액티비티의 수명 주기에 추가해야 한다.
@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    // TODO Codelab: DisposableEffect step. Make MapView follow the lifecycle
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

    // 관찰자는 현재 lifecycle에 추가되고, 현재 수명 주기가 변경되거나 이 컴포저블이 컴포지션을 종료할 때마다 삭제됩니다.
    // DisposableEffect의 key를 사용하여 lifecycle 또는 mapView가 변경되면 관찰자가 삭제되고 오른쪽 lifecycle에 다시 추가됩니다.
    // 조금 전의 변경사항에 따라 MapView는 항상 현재 LifecycleOwner의 lifecycle을 따르며, 그 동작은 View 환경에서 사용된 것과 똑같습니다
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(key1 = lifecycle, key2 = mapView) {
        //Make MapView follow the current lifecycle
        val lifecycleObserver = getMapLifecycleObserver(mapView)
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}

// 이 관찰자를 추가하는 것만으로는 충분하지 않습니다. 삭제할 수 있어야 합니다.
// DisposableEffect 는 키가 변경되거나 컴포저블이 컴포지션을 종료하면 정리되어야 하는 Side effect 를 위한 것입니다.
// 최종 rememberMapViewWithLifecycle 코드가 정확하게 이 작업을 수행합니다. 프로젝트에 다음 줄을 구현합니다.
private fun getMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    LifecycleEventObserver { _, event ->
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

fun GoogleMap.setZoom(
    @FloatRange(from = MinZoom.toDouble(), to = MaxZoom.toDouble()) zoom: Float
) {
    resetMinMaxZoomPreference()
    setMinZoomPreference(zoom)
    setMaxZoomPreference(zoom)
}
