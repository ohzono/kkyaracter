package com.example.infra.db

import com.example.model.Kyara

interface KyaraDataBase {
    fun save(kyara: Kyara?)
    fun load(): Kyara?
}