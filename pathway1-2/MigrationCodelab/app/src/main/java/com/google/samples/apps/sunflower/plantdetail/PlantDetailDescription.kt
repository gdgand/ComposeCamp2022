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
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

@Composable
fun PlantDetailDescription(
    plantDetailViewModel: PlantDetailViewModel
) {
    val plant by plantDetailViewModel.plant.observeAsState()

    plant?.let {
        PlantDetailContent(plant = it)
    }
}

@Composable
fun PlantDetailContent(plant: Plant) {
    Surface {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_normal))) {
            PlantName(name = plant.name)
            PlantWatering(wateringInterval = plant.wateringInterval)
            PlantDescription(plant.description)
        }
    }
}

@Composable
fun PlantName(name: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth() // match_constraint (start, end constraint parent)
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small)) //layout_margin
            .wrapContentWidth(Alignment.CenterHorizontally), // gravity center_horizontal
        style = MaterialTheme.typography.h5, // textAppearanceHeadline5
        text = name
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PlantWatering(
    modifier: Modifier = Modifier,
    wateringInterval: Int
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.watering_needs_prefix),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primaryVariant, // colorAccent
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.margin_small),
                    top = dimensionResource(id = R.dimen.margin_normal),
                    end = dimensionResource(id = R.dimen.margin_small)
                )
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = pluralStringResource(id = R.plurals.watering_needs_suffix, count = wateringInterval, wateringInterval), //@BindingAdapter("wateringText")
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.margin_small),
                    end = dimensionResource(id = R.dimen.margin_small)
                )
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun PlantDescription(description: String) {
    val htmlDescription = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    AndroidView(
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.margin_small),
                top = dimensionResource(id = R.dimen.margin_small),
                bottom = dimensionResource(id = R.dimen.margin_small)
            ),
        factory = {
            TextView(it).apply {
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
    MaterialTheme {
        PlantName(name = "Apple")
    }
}

@Preview
@Composable
fun PlantWateringPreview() {
    MaterialTheme {
        PlantWatering(wateringInterval = 7)
    }
}

@Preview
@Composable
private fun PlantDescriptionPreview() {
    MaterialTheme {
        PlantDescription("HTML<br><br>description")
    }
}

@Preview
@Composable
private fun PlantDetailContentPreview() {
    val plant = Plant("id", "Apple", "HTML<br><br>description", 3, 30, "")
    MaterialTheme {
        PlantDetailContent(plant)
    }
}

