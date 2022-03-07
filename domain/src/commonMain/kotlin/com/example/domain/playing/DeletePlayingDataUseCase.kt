package com.example.domain.playing

import com.example.data.repository.KyaraRepository

class DeletePlayingDataUseCase(
    private val repository: KyaraRepository
) {
    suspend fun invoke() {
        return repository.save(null)
    }
}