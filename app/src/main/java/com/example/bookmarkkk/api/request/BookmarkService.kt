package com.example.bookmarkkk.api.request

import com.example.bookmarkkk.*
import com.example.bookmarkkk.api.model.Bookmark
import com.example.bookmarkkk.api.model.BookmarkForAdd
import com.example.bookmarkkk.api.model.BookmarkForModify
import com.example.bookmarkkk.api.model.BookmarkId
import com.example.bookmarkkk.api.model.HashTag
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface BookmarkService {
    //    @GET("api/bookmarks")
//    fun getAllBookmarks():Call<List<Bookmark>> // 모든 북마크 조회
    @GET("api/bookmarks")
    suspend fun getAllBookmarks(): Response<List<Bookmark>> // 모든 북마크 조회
    //    @POST("api/bookmarks")
//    fun addBookmark(@Body item: BookmarkForAdd):Call<Void>
    @POST("api/bookmarks")
    suspend fun addBookmark(@Body item: BookmarkForAdd):Result<Void>
    //    @HTTP(method = "DELETE", path = "api/bookmarks", hasBody = true)
//    fun deleteBookmark(@Body item: BookmarkId):Call<Void> // 북마크 삭제
    @HTTP(method = "DELETE", path = "api/bookmarks", hasBody = true)
    suspend fun deleteBookmark(@Body item: BookmarkId):Result<Void> // 북마크 삭제
    @GET("api/hashtags")
    fun getTags(): Call<List<HashTag>>
    //    @PATCH("api/bookmarks")
//    fun updateBookmark(@Body item: BookmarkForModify):Call<Void> // 북마크 편집
    @PATCH("api/bookmarks")
    suspend fun updateBookmark(@Body item: BookmarkForModify):Result<Void> // 북마크 편집
}