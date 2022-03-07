package com.example.data.repository

import com.example.model.Kyara

interface KyaraRepository {
    suspend fun save(kyara: Kyara?)
    suspend fun load(): Kyara?
}