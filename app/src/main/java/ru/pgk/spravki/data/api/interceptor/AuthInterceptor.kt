package ru.pgk.spravki.data.api.interceptor

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import ru.pgk.spravki.data.api.NetworkApi
import ru.pgk.spravki.data.api.NetworkConstants.CUSTOM_HEADER
import ru.pgk.spravki.data.api.NetworkConstants.NO_AUTH
import ru.pgk.spravki.data.api.model.ErrorModel
import ru.pgk.spravki.data.api.model.login.Login
import ru.pgk.spravki.data.api.model.user.RefreshTokenBody
import ru.pgk.spravki.data.database.UserDataSource
import ru.pgk.spravki.utils.extensions.fromJson
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Provider

class AuthInterceptor @Inject constructor(
    private val userDataSource: UserDataSource,
    private val authApi: Provider<NetworkApi>
): Interceptor {

    private val mutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()

        if (NO_AUTH in req.headers.values(CUSTOM_HEADER)) {
            return chain.proceedWithToken(req, null)
        }

        val token = userDataSource.getAccessToken()

        val res = chain.proceedWithToken(req, token)

        if ((res.code != HttpURLConnection.HTTP_UNAUTHORIZED) || token == null) {
            return res
        }

        if(!res.isSuccessful && res.body != null) {
            val errorModel = Gson().fromJson<ErrorModel>(res.body!!.string())

            if(errorModel.code?.contains("errors_token") == false)
                return res
        }

        val newToken: String? = runBlocking(Dispatchers.IO) {
            mutex.withLock {
                val maybeUpdatedToken = userDataSource.getAccessToken()

                when {
                    maybeUpdatedToken == null -> null
                    maybeUpdatedToken != token -> maybeUpdatedToken
                    else -> {
                        val refreshTokenRes = authApi.get().refreshToken(
                            RefreshTokenBody(
                                token = userDataSource.getRefreshToken() ?: "",
                                userId = userDataSource.getUserId()
                            )
                        )
                        when (refreshTokenRes.code()) {
                            HttpURLConnection.HTTP_OK -> {
                                refreshTokenRes.body()!!.accessToken.also { updatedToken ->
                                    userDataSource.saveAccessToken(updatedToken)
                                }
                            }
                            else -> {

                                val signInResponse = authApi.get().login(
                                    Login(
                                        login = userDataSource.getLogin() ?: "",
                                        password = userDataSource.getPassword() ?: ""
                                    )
                                )
                                val accessToken = signInResponse.body()?.accessToken

                                userDataSource.saveAccessToken(accessToken)
                                userDataSource.saveRefreshToken(signInResponse.body()?.refreshToken)

//                                if(accessToken == null){
//                                    userDataSource.save(UserLocalDatabase(statusRegistration = false))
//                                }

                                accessToken
                            }
                        }
                    }
                }
            }
        }

        return if (newToken !== null) chain.proceedWithToken(req, newToken) else res
    }

    private fun Interceptor.Chain.proceedWithToken(req: Request, token: String?): Response =
        req.newBuilder()
            .apply {
                if (token !== null) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .removeHeader(CUSTOM_HEADER)
            .build()
            .let(::proceed)

}