package com.example.data.repository

import com.example.infra.db.KyaraDataBase
import com.example.model.Kyara

class KyaraRepositoryImpl(
    private val dataBase: KyaraDataBase
) : KyaraRepository {

    override suspend fun save(kyara: Kyara?) {
        dataBase.save(kyara)
    }

    override suspend fun load(): Kyara? {
        return dataBase.load()
    }
}