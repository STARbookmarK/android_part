package com.example.bookmarkkk

import retrofit2.Call
import retrofit2.http.*

interface LoginService {
    @POST("api/login")
    fun login(@Body request: LoginData): Call<Void>
}

interface AutoLoginService {
    @GET("api/login")
    fun autoLogin(
    ):Call<UserInfo>
}

interface RegisterService{
    @POST("api/register")
    fun register(@Body request: RegisterData):Call<Void>
}

interface LogoutService {
    @GET("api/logout")
    fun logout(
    ):Call<Void>
}


