package com.example.uimodel

import com.example.model.Kyara

data class PlayingUiState(
    val kyara: Kyara? = null,
    val loading: Boolean = false,
    val error: Throwable? = null,
    val goToCreatePage: Boolean = false,
    val onPlaying: Boolean = false
)