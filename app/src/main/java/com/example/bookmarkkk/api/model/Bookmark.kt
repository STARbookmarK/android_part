package com.example.bookmarkkk.api.model

import com.google.gson.annotations.SerializedName

data class Bookmark( // 북마크
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("address") val address : String,
    @SerializedName("image") val image : String,
    @SerializedName("description") val description : String,
    @SerializedName("rate") val rate : Int,
    @SerializedName("hashtags") val tags : String
)

data class BookmarkForAdd( // 북마크 추가
    @SerializedName("title") val title : String,
    @SerializedName("address") val address: String,
    @SerializedName("description") val description: String,
    @SerializedName("rate") val rate : Int,
    @SerializedName("shared") val shared : Boolean,
    @SerializedName("hashtags") val hashtags : List<String>
)

data class BookmarkForModify( // 북마크 수정
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("address") val address: String,
    @SerializedName("description") val description: String,
    @SerializedName("rate") val rate: Int,
    @SerializedName("shared") val shared: Boolean,
    @SerializedName("hashtags") val hashtags: List<String>
)

data class BookmarkId( // 아이디 중복체크
    @SerializedName("id") val id: Int
)

data class HashTag(
    @SerializedName("title") val title : String,
    @SerializedName("star") val start : Int,
    @SerializedName("category") val category : String
)
