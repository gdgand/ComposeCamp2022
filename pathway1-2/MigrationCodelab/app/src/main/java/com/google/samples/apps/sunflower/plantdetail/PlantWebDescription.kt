package com.google.samples.apps.sunflower.plantdetail

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

@Composable
fun PlantWebDescription(htmlDescription: String) {
    /**
     * AndroidView 를 사용하는 경우 ? 커스텀 뷰를 이용해야하거나 Html 사용
     **/
    val description = remember(htmlDescription) {
        HtmlCompat.fromHtml(htmlDescription, HtmlCompat.FROM_HTML_MODE_COMPACT)
    } 
    
    AndroidView(factory = { context ->
        TextView(context).apply {
            movementMethod = LinkMovementMethod.getInstance()
        }
    }, update = {
        it.text = description
    })
}

@Preview
@Composable
private fun PreviewPlantWebDescription() {
    MaterialTheme {
        PlantWebDescription(htmlDescription = "HTML<br><br>description")
    }
}
