package com.example.kyaracter.android.ui.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.playing.SavePlayingDataUseCase
import com.example.uimodel.PickingSoundFileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PickingSoundFileViewModel(
    private val savePlayingDataUseCase: SavePlayingDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PickingSoundFileUiState())
    val uiState = _uiState.asStateFlow()

    fun onSelectedSoundFile(character: String, soundFilePath: String) {
        viewModelScope.launch {
            try {
                savePlayingDataUseCase.invoke(character, soundFilePath)
                _uiState.value = uiState.value.copy(goToPlaying = true)
            } catch (e: Exception) {
                _uiState.value = uiState.value.copy(errorMessage = "Error occurred.")
            }
        }
    }
    
    fun onFailedPicking() {
        _uiState.value = uiState.value.copy(errorMessage = "Failed to pick sound file")
    }
}