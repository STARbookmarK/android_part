package com.example.bookmarkkk

import com.google.gson.GsonBuilder
import okhttp3.JavaNetAuthenticator
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import javax.net.ssl.SSLSession


object NetworkClient {

    var builder = OkHttpClient().newBuilder()
    var okHttpClient = builder
        .cookieJar(JavaNetCookieJar(CookieManager()))
        .build()

    var gson = GsonBuilder().setLenient().create()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://dev.stark.r-e.kr/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val loginService: LoginService by lazy { retrofit.create(LoginService::class.java) }
    val autoLoginService : AutoLoginService by lazy { retrofit.create(AutoLoginService::class.java) }
}