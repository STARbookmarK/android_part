package com.example.bookmarkkk

import retrofit2.Call
import retrofit2.http.*

// retrofit interface naming -> ok
// change password -> ok
// id, nickname check -> ok

interface AuthenticationService {
    @POST("api/login")
    fun login(@Body request: LoginData): Call<Void>
    @GET("api/login")
    fun autoLogin():Call<UserId>
    @GET("api/logout")
    fun logout():Call<Void>
}

interface SignUpService{
    @POST("api/register")
    fun signUp(@Body request: SignUpData):Call<Void>
    @GET("api/register/id/{user_id}")
    fun idCheck(@Path("user_id") user_id: String):Call<IdCheckData>
    @GET("api/register/name/{nickname}")
    fun nicknameCheck(@Path("nickname") nickname: String):Call<NicknameCheckData>
}

interface UserInfoService {
    @GET("api/infos")
    fun getUserInfo():Call<UserInfo>
    @PATCH("api/infos")
    fun changeBio(@Body bio: BioOfUserInfo):Call<Void>
    @PATCH("api/password")
    fun changePassword(@Body pwData: Password):Call<Void>
}


