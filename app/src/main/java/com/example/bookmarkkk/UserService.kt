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
        @Header("token") token: String
    ):Call<UserInfo>
}


