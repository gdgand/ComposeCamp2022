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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.samples.crane.base.Result
import androidx.compose.samples.crane.data.ExploreModel
import androidx.compose.samples.crane.ui.CraneTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

internal const val KEY_ARG_DETAILS_CITY_NAME = "KEY_ARG_DETAILS_CITY_NAME"

fun launchDetailsActivity(context: Context, item: ExploreModel) {
    context.startActivity(createDetailsActivityIntent(context, item))
}

@VisibleForTesting
fun createDetailsActivityIntent(context: Context, item: ExploreModel): Intent {
    val intent = Intent(context, DetailsActivity::class.java)
    intent.putExtra(KEY_ARG_DETAILS_CITY_NAME, item.city.name)
    return intent
}


// 화면에 표시할 데이터, 로드 및 오류 신호와 같은 모든 가능성을 다루는 클래스
data class DetailsUiState(
    val cityDetails: ExploreModel? = null,
    val isLoading: Boolean = false,
    val throwError: Boolean = false
)

@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                CraneTheme {
                    Surface {
                        DetailsScreen(
                            onErrorLoading = { finish() },
                            modifier = Modifier
                                .statusBarsPadding()
                                .navigationBarsPadding()
                        )
                    }
                }
            }
        }
    }
}

/**
 * 세부정보 화면 시작 방식: DetailsScreen Composable이 cityDetails를 viewModel에서 동기식으로 가져오고 결과가
 * 성공일 경우 DetailContent를 호출.
 * But, cityDetails는 UI 스레드를 로드하는 데 더 많은 비용이 들 수 있고, 코루틴을 사용하여 데이터 로드를 다른 스레드로 옮길
 * 수 있음. 코드 개선을 통해 load화면을 추가하고 DetailsContent를 표시.
 * 그 후 uiState에 따라 데이터를 표시하거나 로드 화면을 표시하거나, 오류를 보고한다.
 */
@Composable
fun DetailsScreen(
    onErrorLoading: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = viewModel()
) {
    val uiState by produceState(initialValue = DetailsUiState(isLoading = true)) {
        val cityDetailsResult = viewModel.cityDetails
        value = if (cityDetailsResult is Result.Success<ExploreModel>) {
            DetailsUiState(cityDetailsResult.data)
        }else {
            DetailsUiState(throwError = true)
        }
    }
    when {
        uiState.cityDetails != null -> {
            DetailsContent(uiState.cityDetails!!, modifier.fillMaxSize())
        }
        uiState.isLoading -> {
            Box(modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        else -> {onErrorLoading}
    }
}

@Composable
fun DetailsContent(
    exploreModel: ExploreModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Spacer(Modifier.height(32.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = exploreModel.city.nameToDisplay,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = exploreModel.description,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        CityMapView(exploreModel.city.latitude, exploreModel.city.longitude)
    }
}


@Composable
private fun CityMapView(latitude: String, longitude: String) {
    // The MapView lifecycle is handled by this composable. As the MapView also needs to be updated
    // with input from Compose UI, those updates are encapsulated into the MapViewContainer
    // composable. In this way, when an update to the MapView happens, this composable won't
    // recompose and the MapView won't need to be recreated.
    val mapView = rememberMapViewWithLifecycle()
    MapViewContainer(mapView, latitude, longitude)
}

@Composable
private fun MapViewContainer(
    map: MapView,
    latitude: String,
    longitude: String
) {
    val cameraPosition = remember(latitude, longitude) {
        LatLng(latitude.toDouble(), longitude.toDouble())
    }

    LaunchedEffect(map) {
        val googleMap = map.awaitMap()
        googleMap.addMarker { position(cameraPosition) }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
    }

    var zoom by rememberSaveable(map) { mutableStateOf(InitialZoom) }
    ZoomControls(zoom) {
        zoom = it.coerceIn(MinZoom, MaxZoom)
    }

    val coroutineScope = rememberCoroutineScope()
    AndroidView({ map }) { mapView ->
        // Reading zoom so that AndroidView recomposes when it changes. The getMapAsync lambda
        // is stored for later, Compose doesn't recognize state reads
        val mapZoom = zoom
        coroutineScope.launch {
            val googleMap = mapView.awaitMap()
            googleMap.setZoom(mapZoom)
            // Move camera to the same place to trigger the zoom update
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
        }
    }
}

@Composable
private fun ZoomControls(
    zoom: Float,
    onZoomChanged: (Float) -> Unit
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        ZoomButton("-", onClick = { onZoomChanged(zoom * 0.8f) })
        ZoomButton("+", onClick = { onZoomChanged(zoom * 1.2f) })
    }
}

@Composable
private fun ZoomButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        ),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.h5)
    }
}

private const val InitialZoom = 5f
const val MinZoom = 2f
const val MaxZoom = 20f
