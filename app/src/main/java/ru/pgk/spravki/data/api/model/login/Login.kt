package ru.pgk.spravki.data.api.model.login

data class Login(
    val login: String,
    val password: String
)

data class LoginResponse(
    val error: String?,
    val code: String?,
    val accessToken: String?,
    val refreshToken: String?,
    val roles: List<String>?
)