package ru.pgk.spravki.data.api.model.login

import kotlinx.serialization.Serializable

@Serializable
data class Login(
    val login: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val error: String?,
    val code: String?,
    val accessToken: String?,
    val refreshToken: String?,
    val roles: List<String>?
)