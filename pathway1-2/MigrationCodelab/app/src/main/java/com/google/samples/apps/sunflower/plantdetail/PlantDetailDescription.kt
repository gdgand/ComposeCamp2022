/*
 * Copyright 2020 Google LLC
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

package com.google.samples.apps.sunflower.plantdetail

import android.content.res.Configuration
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.google.android.material.composethemeadapter.MdcTheme
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

/**
 * 7. ViewModel 및 LiveData
 * observeAsState를 통해 LiveData의 value를 옵저브할 수 있음
 */
@Composable
fun PlantDetailDescription(
    plantDetailViewModel: PlantDetailViewModel
) {
    val plant by plantDetailViewModel.plant.observeAsState()

    plant?.let {
        PlantDetailContent(it)
    }
}

@Composable
fun PlantDetailContent(
    plant: Plant
) {
    Surface {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_normal))) {
            PlantName(name = plant.name)
            PlantWatering(wateringInterval = plant.wateringInterval)
            PlantDescription(description = plant.description)
        }
    }

}

/**
 * 6. XML -> Composable
 * dimensionResource나 stringResource를 통해 resources에 정의한 xml 접근 가능
 * wrapContentWidth를 통해 가로 정렬
 * fillMaxWidth <-> width match_parent
 */
@Composable
fun PlantName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.margin_small))
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PlantWatering(wateringInterval: Int) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val centerWithPaddingModifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .align(Alignment.CenterHorizontally)

        val normalPadding = dimensionResource(id = R.dimen.margin_normal)

        Text(
            text = stringResource(id = R.string.watering_needs_prefix),
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = centerWithPaddingModifier.padding(top = normalPadding)
        )
        
        val wateringIntervalText = pluralStringResource(id = R.plurals.watering_needs_suffix, count = wateringInterval, wateringInterval)

        Text(
            text = wateringIntervalText,
            modifier = centerWithPaddingModifier.padding(bottom = normalPadding)
        )
    }
}

/**
 * 9. Compose -> View
 * Compose를 통해 표현할 수 없는 View System 컴포넌트는 AndroidView르 통해 프로그래매틱하게 구현할 수 있다.
 */
@Composable
private fun PlantDescription(description: String) {
    val htmlDescription = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    AndroidView(
        factory = { context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = { textView ->
            textView.text = htmlDescription
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PlantNamePreview() {
    val plant = Plant("id", "Apple", "description", 3, 30, "")

    MdcTheme {
        PlantDetailContent(plant = plant)
    }
}

@Preview(showBackground = true)
@Composable
private fun PlantWateringPreview() {
    MdcTheme {
        PlantWatering(wateringInterval = 7)
    }
}

@Preview(showBackground = true)
@Composable
private fun PlantDescriptionView() {
    MdcTheme {
        PlantDescription(description = "HTML<br><br>description")
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlantDetailContentDarkPreview() {
    val plant = Plant("id", "Apple", "description", 3, 30, "")

    MdcTheme {
        PlantDetailContent(plant = plant)
    }
}
