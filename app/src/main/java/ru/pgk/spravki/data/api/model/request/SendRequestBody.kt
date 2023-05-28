package ru.pgk.spravki.data.api.model.request

data class SendRequestBody(
    val count: Int,
    val departmentId: Int,
    val documentType: DocumentTypeEnum
)

data class SendRequestResponse(
    val error: String?
)