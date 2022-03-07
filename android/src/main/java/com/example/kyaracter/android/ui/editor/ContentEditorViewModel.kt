package com.example.kyaracter.android.ui.editor

import androidx.lifecycle.ViewModel
import com.example.uimodel.ContentEditorUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContentEditorViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(ContentEditorUiState())
    val uiState = _uiState.asStateFlow()

    fun onEnterCharacter(character: String) {
        when {
            character.isEmpty() -> {
                _uiState.value = _uiState.value.copy(errorMessage = "Please input text.")
            }
            character.length > MaxInputLength -> {
                _uiState.value =
                    _uiState.value.copy(errorMessage = "No more than 1 character can be entered.")
            }
            else -> {
                _uiState.value =
                    _uiState.value.copy(goToPickingSoundFile = true, inputCharacter = character)
            }
        }
    }

    companion object {
        const val MaxInputLength = 1
    }
}