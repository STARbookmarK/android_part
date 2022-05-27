package com.example.bookmarkkk

import android.graphics.Paint
import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.CookieJar
import okhttp3.JavaNetAuthenticator
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit
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

    val authenticationService: AuthenticationService by lazy { retrofit.create(AuthenticationService::class.java) }
    val signUpService : SignUpService by lazy { retrofit.create(SignUpService::class.java) }
    val userInfoService : UserInfoService by lazy { retrofit.create(UserInfoService::class.java) }
}

//object RegisterClient {
//
//    var gson = GsonBuilder().setLenient().create()
//
//    val retrofit = Retrofit.Builder()
//        .baseUrl("https://44142a55-dbbf-46c1-9a3b-d10e590ee659.mock.pstmn.io/") //mock server
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()
//
//    val registerService: RegisterService by lazy { retrofit.create(RegisterService::class.java) }
//}