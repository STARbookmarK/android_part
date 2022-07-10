package com.example.bookmarkkk

import android.media.MediaRouter
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AuthenticationService { // 로그인 관련
    @POST("api/login")
    fun login(@Body request: LoginData): Call<Void> // 로그인
    @GET("api/login")
    fun autoLogin():Call<UserId> // 자동로그인
    @GET("api/logout")
    fun logout():Call<Void> // 로그아웃
}

interface SignUpService{ // 회원가입 관련
    @POST("api/register")
    fun signUp(@Body request: SignUpData):Call<Void> // 회원가입
    @GET("api/register/id/{user_id}")
    fun idCheck(@Path("user_id") user_id: String):Call<IdCheckData> // id 중복확인
    @GET("api/register/name/{nickname}")
    fun nicknameCheck(@Path("nickname") nickname: String):Call<NicknameCheckData> // 닉네임 중복확인
}

interface UserInfoService { // 사용자 정보 관련
    @GET("api/infos")
    fun getUserInfo():Call<UserInfo> // 정보 확인
    @PATCH("api/infos")
    fun changeBio(@Body bio: BioOfUserInfo):Call<Void> // 소개글 변경
    @PATCH("api/password")
    fun changePassword(@Body pwData: Password):Call<Void> // 패스워드 변경
    @PATCH("api/show")
    fun changeViewType(@Body viewInfo: BookmarkViewInfo):Call<Void> // 뷰타입 변경
}

interface BookmarkService { // 북마크 관련
    @GET("api/bookmarks")
    fun getAllBookmarks():Call<List<Bookmark>> // 모든 북마크 조회
//    @POST("api/bookmarks")
//    fun addBookmark(@Body data: Bookmark):Call<Void> // 북마크 추가
//    @DELETE("api/bookmarks/{bookmarks_id}")
//    fun deleteBookmark(@Path("bookmarks_id") id: String):Call<Void> // 북마크 삭제
//    @PUT("api/bookmarks/{bookmarks_id}")
//    fun updateBookmark(@Path("bookmarks_id") id: String):Call<Void> // 북마크 편집
}


