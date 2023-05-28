package ru.pgk.spravki.data.api.model.user

data class RefreshTokenBody(
    val token: String,
    val userId: Int
)