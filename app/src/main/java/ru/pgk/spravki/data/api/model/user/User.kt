package ru.pgk.spravki.data.api.model.user

data class User(
    val email: String,
    val fatherName: String,
    val id: Int,
    val login: String,
    val name: String,
    val password: String,
    val surname: String,
    val roles: List<String>,
    val code: String?,
    val error: String?
)