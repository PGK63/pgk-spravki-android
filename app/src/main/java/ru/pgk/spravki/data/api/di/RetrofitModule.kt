package ru.pgk.spravki.data.api.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.pgk.spravki.data.api.NetworkApi
import ru.pgk.spravki.data.api.interceptor.AuthInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @[Provides Singleton]
    fun providerNetworkApi(
        retrofit: Retrofit
    ): NetworkApi = retrofit.create()

    @[Provides Singleton]
    fun providerRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ):Retrofit = Retrofit.Builder()
        .baseUrl("http://62.113.105.39:20202")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @[Provides Singleton]
    fun providerOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    @[Provides Singleton]
    fun providerGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

}