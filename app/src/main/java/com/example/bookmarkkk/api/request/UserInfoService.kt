package com.example.bookmarkkk.api.request

import com.example.bookmarkkk.api.model.BioOfUserInfo
import com.example.bookmarkkk.api.model.BookmarkViewInfo
import com.example.bookmarkkk.api.model.Password
import com.example.bookmarkkk.api.model.UserInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserInfoService {
    @GET("api/infos")
    suspend fun getUserInfo():Result<UserInfo> // 정보 확인
//    @PATCH("api/infos")
//    fun changeBio(@Body bio: BioOfUserInfo):Call<Void> // 소개글 변경

    //    @GET("api/infos")
//    suspend fun getUserInfo():Response<UserInfo> // 정보 확인
    @PATCH("api/infos")
    suspend fun changeBio(@Body bio: BioOfUserInfo): Response<Void> // 소개글 변경
    @PATCH("api/password")
    fun changePassword(@Body pwData: Password): Call<Void> // 패스워드 변경
    @PATCH("api/show")
    fun changeViewType(@Body viewInfo: BookmarkViewInfo): Call<Void> // 뷰타입 변경
}