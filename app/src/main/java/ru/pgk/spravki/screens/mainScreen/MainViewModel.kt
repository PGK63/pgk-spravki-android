package ru.pgk.spravki.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.pgk.spravki.data.api.NetworkApi
import ru.pgk.spravki.data.api.model.ErrorModel
import ru.pgk.spravki.data.api.model.department.DepartmentType
import ru.pgk.spravki.data.api.model.request.Request
import ru.pgk.spravki.data.api.model.request.SendRequestBody
import ru.pgk.spravki.data.api.model.user.User
import ru.pgk.spravki.utils.extensions.fromJson
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkApi: NetworkApi
): ViewModel() {

    suspend fun getUserInfo(): Response<User> = networkApi.getUserInfo()

    suspend fun getRequests(): List<Request> = networkApi.getMyRequest()

    suspend fun getDepartmentTypes(): List<DepartmentType> = networkApi.getDepartmentTypes()

    fun deleteRequest(
        requestId: Int,
        onSuccess: suspend () -> Unit,
        onError: (String) -> Unit,
        onFinally: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = networkApi.deleteRequest(requestId)

                if(response.isSuccessful){
                    onSuccess()
                }else {
                    val errorModel = Gson().fromJson<ErrorModel>(response.errorBody()!!.string())
                    onError(errorModel.error ?: "Произошла ошибка")
                }
            }catch (e: Exception){
                onError(e.message.toString())
            }finally {
                delay(1000L)
                onFinally()
            }
        }
    }

    fun sendRequest(
        body: SendRequestBody,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        onFinally: () -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = networkApi.sendRequest(body = body)
                if(response.isSuccessful){
                    onSuccess()
                }else {
                    val errorModel = Gson().fromJson<ErrorModel>(response.errorBody()!!.string())
                    onError(errorModel.error ?: "Произошла ошибка")
                }
            }catch (e:Exception){
                onError(e.message.toString())
            }finally {
                delay(1000L)
                onFinally()
            }
        }
    }
}