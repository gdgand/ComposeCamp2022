package androidx.compose.samples.crane.details

import androidx.compose.samples.crane.data.ExploreModel

data class DetailsUiState(
    val cityDetails: ExploreModel? = null,
    val isLoading: Boolean = false,
    val throwError: Boolean = false
)