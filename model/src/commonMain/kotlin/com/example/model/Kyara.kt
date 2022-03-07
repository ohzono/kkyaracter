package com.example.model

import kotlinx.serialization.Serializable


@Serializable
data class Kyara(
    val id: String,
    val character: String,
    val soundFilePath: String
)
