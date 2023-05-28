package ru.pgk.spravki.data.database

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserDataSource @Inject constructor(
    @ApplicationContext context: Context
) {

    private val userShared = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun getRefreshToken(): String? {
        return userShared.getString("refreshToken", "")
    }

    fun getAccessToken(): String? {
        return userShared.getString("accessToken", "")
    }

    fun getUserId(): Int {
        return userShared.getInt("userId", 0)
    }

    fun getLogin(): String? {
        return userShared.getString("login", "")
    }

    fun getPassword(): String? {
        return userShared.getString("password", "")
    }

    fun saveAccessToken(token: String?) {
        userShared.edit()
            .putString("accessToken", token)
            .apply()
    }

    fun saveRefreshToken(token: String?){
        userShared.edit()
            .putString("refreshToken", token)
            .apply()
    }

    fun saveUserId(id: Int){
        userShared.edit()
            .putInt("userId", id)
            .apply()
    }

    fun saveLogin(login: String?){
        userShared.edit()
            .putString("login", login)
            .apply()
    }

    fun savePassword(password: String?){
        userShared.edit()
            .putString("password", password)
            .apply()
    }
}
  