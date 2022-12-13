package androidx.compose.samples.crane.util

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.libraries.maps.MapView

fun getMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> throw IllegalStateException()
        }
    }