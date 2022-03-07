package com.example.domain.playing

import com.example.data.repository.KyaraRepository
import com.example.model.Kyara

class LoadPlayingDataUseCase(
    private val repository: KyaraRepository
) {
    suspend fun invoke(): Kyara?  {
        return repository.load()
    }
}