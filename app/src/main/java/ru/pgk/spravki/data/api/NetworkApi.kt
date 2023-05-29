package ru.pgk.spravki.data.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.readText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.pgk.spravki.data.api.NetworkConstants.CUSTOM_HEADER
import ru.pgk.spravki.data.api.NetworkConstants.NO_AUTH
import ru.pgk.spravki.data.api.model.ErrorModel
import ru.pgk.spravki.data.api.model.department.DepartmentType
import ru.pgk.spravki.data.api.model.login.Login
import ru.pgk.spravki.data.api.model.login.LoginResponse
import ru.pgk.spravki.data.api.model.request.Request
import ru.pgk.spravki.data.api.model.request.SendRequestBody
import ru.pgk.spravki.data.api.model.request.SendRequestResponse
import ru.pgk.spravki.data.api.model.student.Student
import ru.pgk.spravki.data.api.model.user.RefreshTokenBody
import ru.pgk.spravki.data.api.model.user.User

interface NetworkApi {

    @Headers("$CUSTOM_HEADER: $NO_AUTH")
    @POST("/users/login")
    suspend fun login(
        @Body login: Login,
        @Header("Token") token: String = "6f065151-3bc8-434b-8271-d415feeb5f8f"
    ): Response<LoginResponse>

    @Headers("$CUSTOM_HEADER: $NO_AUTH")
    @POST("/users/refresh-token")
    suspend fun refreshToken(
        @Body body: RefreshTokenBody,
        @Header("Token") token: String = "6f065151-3bc8-434b-8271-d415feeb5f8f"
    ): Response<LoginResponse>

    @GET("/users/info")
    suspend fun getUserInfo(
        @Header("Token") token: String = "6f065151-3bc8-434b-8271-d415feeb5f8f",
        @Header("Authorization") accessToken: String? = null
    ): Response<User>

    @GET("/students/info")
    suspend fun getStudentInfo(
        @Header("Token") token: String = "6f065151-3bc8-434b-8271-d415feeb5f8f"
    ): Response<Student>

    @GET("/requests/my-requests")
    suspend fun getMyRequest(
        @Header("Token") token: String = "6f065151-3bc8-434b-8271-d415feeb5f8f"
    ): List<Request>

    @Headers("$CUSTOM_HEADER: $NO_AUTH")
    @GET("/departments/get-all-types")
    suspend fun getDepartmentTypes(
        @Header("Token") token: String = "6f065151-3bc8-434b-8271-d415feeb5f8f"
    ): List<DepartmentType>

    @POST("/requests/send")
    suspend fun sendRequest(
        @Header("Token") token: String = "6f065151-3bc8-434b-8271-d415feeb5f8f",
        @Body body: SendRequestBody
    ): Response<SendRequestResponse?>
}

private val json = Json { ignoreUnknownKeys = true }

suspend fun login(login: Login): Pair<LoginResponse?, ErrorModel?>{
    return try {
        val client = HttpClient(Android){
            install(JsonFeature){
                serializer = KotlinxSerializer()
            }
        }

        val response = client.post<LoginResponse>("http://62.113.105.39:10101/users/login"){
            contentType(ContentType.Application.Json)
            header("Token", "6f065151-3bc8-434b-8271-d415feeb5f8f")
            body = login
        }
        Pair(response, null)
    }catch (ex: ClientRequestException){
        val res = ex.response.readText(Charsets.UTF_8)
        val response = json.decodeFromString(ErrorModel.serializer(), res)
        Pair(null, response)
    }
}