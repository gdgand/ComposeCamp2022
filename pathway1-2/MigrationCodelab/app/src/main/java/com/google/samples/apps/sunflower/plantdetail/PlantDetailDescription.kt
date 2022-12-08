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

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.google.android.material.composethemeadapter.MdcTheme
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

@Composable
fun PlantDetailDescription(
        plantDetailViewModel: PlantDetailViewModel,
) {
    val plant by plantDetailViewModel.plant.observeAsState()

    PlantDetailContent(plant = plant ?: return)
}

@Composable
fun PlantDetailContent(plant: Plant) {
    Surface {
        Column(Modifier.padding(dimensionResource(id = R.dimen.margin_normal))) {
            PlantName(name = plant.name)
            PlantWatering(wateringInterval = plant.wateringInterval)
            PlantDescription(plant.description)
        }
    }
}

@Composable
private fun PlantName(name: String) {
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
    Column(Modifier.fillMaxWidth()) {
        val centerWithPaddingModifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
                .align(Alignment.CenterHorizontally)

        val normalPadding = dimensionResource(id = R.dimen.margin_normal)

        Text(
                text = stringResource(R.string.watering_needs_prefix),
                color = MaterialTheme.colors.primaryVariant,
                fontWeight = FontWeight.Bold,
                modifier = centerWithPaddingModifier.padding(top = normalPadding)
        )

        val wateringIntervalText = pluralStringResource(id = R.plurals.watering_needs_suffix, count = wateringInterval, wateringInterval)
        Text(
                text = wateringIntervalText,
                modifier = centerWithPaddingModifier.padding(bottom = normalPadding),
        )
    }
}

@Composable
fun PlantDescription(description: String) {
    val htmlDescription = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    AndroidView(
            factory = { context ->
                TextView(context).apply {
                    movementMethod = LinkMovementMethod.getInstance()
                }
            },
            update = {
                it.text = htmlDescription
            }
    )
}

@Preview
@Composable
fun PlantNamePreview() {
    MdcTheme {
        PlantName("Apple")
    }
}

@Preview
@Composable
fun PlantWateringPreview() {
    MdcTheme {
        PlantWatering(wateringInterval = 7)
    }
}

@Preview
@Composable
fun PlantDetailContentPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    MdcTheme {
        PlantDetailContent(plant)
    }
}

@Preview
@Composable
private fun PlantDescriptionPreview() {
    MdcTheme {
        PlantDescription("HTML<br><br>description")
    }
}
