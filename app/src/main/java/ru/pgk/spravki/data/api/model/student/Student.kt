package ru.pgk.spravki.data.api.model.student

import ru.pgk.spravki.data.api.model.department.Department
import ru.pgk.spravki.data.api.model.user.Group
import ru.pgk.spravki.data.api.model.user.User

data class Student(
    val id: Int,
    val user: User,
    val department: Department,
    val group: Group
)