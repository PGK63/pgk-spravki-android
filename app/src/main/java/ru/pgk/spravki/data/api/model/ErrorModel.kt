package ru.pgk.spravki.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorModel(
    val error: String? = null,
    val code: String? = null
)