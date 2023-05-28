package ru.pgk.spravki.data.api.model.user

data class Group(
    val id: Int,
    val educationFinishDate: String,
    val educationForm: String,
    val educationStartDate: String,
    val educationYear: Int,
    val isBudget: Boolean,
    val name: String,
    val studyDepartmentId: Int
)