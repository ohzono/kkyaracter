package com.example.infra.db

import com.example.model.Kyara
import com.russhwolf.settings.Settings
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KyaraDataBaseImpl: KyaraDataBase {
    private val _settings: Settings = Settings()
    override fun save(kyara: Kyara?) {
        if (kyara == null) {
            _settings.remove(Kyara::class.simpleName!!)
        } else {
            _settings.putString(Kyara::class.simpleName!!, Json.encodeToString(kyara))
        }
    }

    override fun load(): Kyara? {
        return if(_settings.keys.any { it == Kyara::class.simpleName }) {
            Json.decodeFromString(_settings.getString(Kyara::class.simpleName!!, ""))
        } else {
            null
        }
    }
}