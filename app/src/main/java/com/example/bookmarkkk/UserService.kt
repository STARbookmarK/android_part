package com.example.bookmarkkk

import android.media.MediaRouter
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AuthenticationService { // 로그인 관련
//    @POST("api/login")
//    fun login(@Body request: LoginData): Call<Void> // 로그인
//    @GET("api/login")
//    fun autoLogin():Call<UserId> // 자동로그인
    @POST("api/login")
    suspend fun login(@Body request: LoginData): Response<Void> // 로그인
    @GET("api/login")
    suspend fun autoLogin(): Result<UserId>
    @GET("api/logout")
    suspend fun logout():Response<Void> // 로그아웃
}

interface SignUpService{ // 회원가입 관련
    @POST("api/register")
    suspend fun signUp(@Body request: SignUpData):Response<Void> // 회원가입
    @GET("api/register/id/{user_id}")
    suspend fun idCheck(@Path("user_id") user_id: String):Result<IdCheckData> // id 중복확인
    @GET("api/register/name/{nickname}")
    suspend fun nicknameCheck(@Path("nickname") nickname: String):Result<NicknameCheckData> // 닉네임 중복확인
}

interface UserInfoService { // 사용자 정보 관련
    @GET("api/infos")
    suspend fun getUserInfo():Result<UserInfo> // 정보 확인
//    @PATCH("api/infos")
//    fun changeBio(@Body bio: BioOfUserInfo):Call<Void> // 소개글 변경

//    @GET("api/infos")
//    suspend fun getUserInfo():Response<UserInfo> // 정보 확인
    @PATCH("api/infos")
    suspend fun changeBio(@Body bio: BioOfUserInfo):Response<Void> // 소개글 변경
    @PATCH("api/password")
    fun changePassword(@Body pwData: Password):Call<Void> // 패스워드 변경
    @PATCH("api/show")
    fun changeViewType(@Body viewInfo: BookmarkViewInfo):Call<Void> // 뷰타입 변경
}

interface BookmarkService { // 북마크 관련
//    @GET("api/bookmarks")
//    fun getAllBookmarks():Call<List<Bookmark>> // 모든 북마크 조회
    @GET("api/bookmarks")
    suspend fun getAllBookmarks():Response<List<Bookmark>> // 모든 북마크 조회

//    @POST("api/bookmarks")
//    fun addBookmark(@Body item: BookmarkForAdd):Call<Void>
    @POST("api/bookmarks")
    suspend fun addBookmark(@Body item: BookmarkForAdd):Result<Void>



//    @HTTP(method = "DELETE", path = "api/bookmarks", hasBody = true)
//    fun deleteBookmark(@Body item: BookmarkId):Call<Void> // 북마크 삭제

    @HTTP(method = "DELETE", path = "api/bookmarks", hasBody = true)
    suspend fun deleteBookmark(@Body item: BookmarkId):Result<Void> // 북마크 삭제

    @GET("api/hashtags")
    fun getTags():Call<List<HashTag>>


//    @PATCH("api/bookmarks")
//    fun updateBookmark(@Body item: BookmarkForModify):Call<Void> // 북마크 편집

    @PATCH("api/bookmarks")
    suspend fun updateBookmark(@Body item: BookmarkForModify):Result<Void> // 북마크 편집
}


