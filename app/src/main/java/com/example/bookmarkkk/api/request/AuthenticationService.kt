package com.example.bookmarkkk.api.request

import com.example.bookmarkkk.api.model.LoginData
import com.example.bookmarkkk.api.model.UserId
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationService {
    //    @POST("api/login")
//    fun login(@Body request: LoginData): Call<Void> // 로그인
//    @GET("api/login")
//    fun autoLogin():Call<UserId> // 자동로그인
    @POST("api/login")
    suspend fun login(@Body request: LoginData): Response<Void> // 로그인
    @GET("api/login")
    suspend fun autoLogin(): Result<UserId>
    @GET("api/logout")
    suspend fun logout(): Response<Void> // 로그아웃
}