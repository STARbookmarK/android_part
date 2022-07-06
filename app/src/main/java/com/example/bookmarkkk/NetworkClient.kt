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

    private const val BASE_URL = "http://dev.stark.r-e.kr/"

    var builder = OkHttpClient().newBuilder()

    var okHttpClient = builder
        .cookieJar(JavaNetCookieJar(CookieManager()))
        .build()

    var gson = GsonBuilder().setLenient().create()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val authenticationService: AuthenticationService by lazy { retrofit.create(AuthenticationService::class.java) }
    val signUpService : SignUpService by lazy { retrofit.create(SignUpService::class.java) }
    val userInfoService : UserInfoService by lazy { retrofit.create(UserInfoService::class.java) }
    val bookmarkService : BookmarkService by lazy { retrofit.create(BookmarkService::class.java)}
}

// API 테스트용
object TestClient {

    var gson = GsonBuilder().setLenient().create()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://ef184ba9-b58e-4708-83f9-68f1b18e1ce9.mock.pstmn.io/") //mock server
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val bookmarkService : BookmarkService by lazy { TestClient.retrofit.create(BookmarkService::class.java) }
}