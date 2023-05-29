package ru.pgk.spravki.screens.authScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import ru.pgk.spravki.data.api.NetworkApi
import ru.pgk.spravki.data.api.model.ErrorModel
import ru.pgk.spravki.data.api.model.login.Login
import ru.pgk.spravki.data.database.UserDataSource
import ru.pgk.spravki.utils.extensions.fromJson
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val networkApi: NetworkApi,
    private val userDataSource: UserDataSource
): ViewModel() {

    fun login(
        name: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (message: String) -> Unit,
        onFinally: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                if(name.isEmpty() || password.isEmpty()) {
                    onError("Заполните все поля"); return@launch
                }

                val response = networkApi.login(
                    Login(
                        login = name,
                        password = password
                    )
                )

                if(response.isSuccessful){

                    val user = networkApi.getUserInfo(accessToken = "Bearer " + response.body()?.accessToken)

                    if(user.isSuccessful){
                        userDataSource.saveAccessToken(response.body()?.accessToken)
                        userDataSource.saveRefreshToken(response.body()?.refreshToken)
                        userDataSource.savePassword(password)
                        userDataSource.saveLogin(name)
                        userDataSource.saveUserId(user.body()!!.id)
                        onSuccess()
                    }else {
                        val errorModel = Gson().fromJson<ErrorModel>(user.errorBody()!!.string())
                        onError(errorModel.error ?: "Произошла ошибка")
                    }
                }else {
                    val errorModel = Gson().fromJson<ErrorModel>(response.errorBody()!!.string())
                    onError(errorModel.error ?: "Произошла ошибка")
                }
            }catch (e:Exception){
                onError(e.message ?: "Произошла ошибка")
            }finally {
                onFinally()
            }
        }
    }
}