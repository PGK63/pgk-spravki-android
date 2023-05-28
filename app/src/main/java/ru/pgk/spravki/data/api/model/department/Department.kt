package ru.pgk.spravki.data.api.model.department

import ru.pgk.spravki.data.api.model.request.DocumentTypeEnum

data class Department(
    val id: Int,
    val name: String,
    val shortName: String? = null
)

data class DepartmentType(
    val department: Department,
    val types: List<DocumentTypeEnum>
)