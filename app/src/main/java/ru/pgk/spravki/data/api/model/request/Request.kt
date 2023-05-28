package ru.pgk.spravki.data.api.model.request

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Request(
    val id: Int,
    val status: RequestStatus,
    val count: Int,
    val date: Long,
    val userId: Int,
    val departmentId: Int,
    val documentType: DocumentType
){
    fun getDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        val dt =  Date(date * 1000)
        return dateFormat.format(dt)
    }
}

data class DocumentType(
    val id: Int,
    val type: DocumentTypeEnum,
    val refresh_time: Int,
    val role: List<String>
)

enum class RequestStatus(val text: String) {
    SEND("Отправлено"),
    IN_PROGRESS("В процессе"),
    DONE("Сделано")
}

enum class DocumentTypeEnum(val text: String) {
    STUDY_DOCUMENT_BUDGET("Справка о месте обучения (бюджет)"),
    STUDY_DOCUMENT_NO_BUDGET("Справка о месте обучения (платная)")
}