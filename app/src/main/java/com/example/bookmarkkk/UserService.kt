package com.example.bookmarkkk

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {
    @POST("api/login")
    @FormUrlEncoded
    fun login(
        @Field("id") user_id: String,
        @Field("pw") user_pw: String,
        @Field("autoLogin") autoLogin : Boolean
    ): Call<LoginData>
}

interface TokenService {
    @GET("api/login")
    fun getTokens():Call<LoginData>
}

