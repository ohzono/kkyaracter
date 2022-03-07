package com.example.domain.playing

import com.example.data.repository.KyaraRepository
import com.example.model.Kyara

class SavePlayingDataUseCase(
    private val repository: KyaraRepository
) {
    suspend fun invoke(character: String, soundFilePath: String) {
        val id = hashCode().toString()
        val kyara = Kyara(id, character, soundFilePath)
        return repository.save(kyara)
    }
}