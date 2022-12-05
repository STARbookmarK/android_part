package com.example.bookmarkkk.api.request

import com.example.bookmarkkk.api.model.IdCheckData
import com.example.bookmarkkk.api.model.NicknameCheckData
import com.example.bookmarkkk.api.model.SignUpData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SignUpService {
    @POST("api/register")
    suspend fun signUp(@Body request: SignUpData): Response<Void> // 회원가입
    @GET("api/register/id/{user_id}")
    suspend fun idCheck(@Path("user_id") user_id: String):Result<IdCheckData> // id 중복확인
    @GET("api/register/name/{nickname}")
    suspend fun nicknameCheck(@Path("nickname") nickname: String):Result<NicknameCheckData> // 닉네임 중복확인
}