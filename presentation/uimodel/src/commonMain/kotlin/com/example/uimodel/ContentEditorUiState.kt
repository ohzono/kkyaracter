package com.example.uimodel


data class ContentEditorUiState(
    val errorMessage: String? = null,
    val goToPickingSoundFile: Boolean = false,
    val inputCharacter: String? = null
)