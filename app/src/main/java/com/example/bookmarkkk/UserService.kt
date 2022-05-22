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

interface IdCheckService{
    @GET("api/register/id/{user_id}")
    fun idCheck(@Path("user_id") user_id: String):Call<IdCheckData>
}

interface NicknameCheckService{
    @GET("api/register/name/{nickname}")
    fun nicknameCheck(@Path("nickname") nickname: String):Call<NicknameCheckData>
}

interface LogoutService {
    @GET("api/logout")
    fun logout(
    ):Call<Void>
}


