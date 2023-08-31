package com.yong.animation_guide

sealed interface UiState {
    object Loading : UiState
    object Loaded : UiState
    object Error : UiState
}
