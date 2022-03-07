package com.example.kyaracter.android.ui.playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.playing.DeletePlayingDataUseCase
import com.example.domain.playing.LoadPlayingDataUseCase
import com.example.domain.playing.SavePlayingDataUseCase
import com.example.uimodel.PlayingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class PlayingViewModel(
    private val loadPlayingDataUseCase: LoadPlayingDataUseCase,
    private val deletePlayingDataUseCase: DeletePlayingDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(loading = true)

                val kyara = loadPlayingDataUseCase.invoke()
                _uiState.value = _uiState.value.copy(kyara = kyara)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e)
            } finally {
                _uiState.value = _uiState.value.copy(loading = false)
            }
        }
    }

    fun onTap() {
        if (uiState.value.kyara == null) {
            _uiState.value = _uiState.value.copy(goToCreatePage = true)
        } else {
            _uiState.value = _uiState.value.copy(onPlaying = true)
        }
    }
    
    fun onLongTap() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(loading = true)
                deletePlayingDataUseCase.invoke()
                _uiState.value = _uiState.value.copy(kyara = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e)
            } finally {
                _uiState.value = _uiState.value.copy(loading = false)
            }
        }
    }
    fun onPlayingStop() {
        _uiState.value = uiState.value.copy(onPlaying = false)
    }
}