package com.example.bookmarkkk.api.model

import com.google.gson.annotations.SerializedName

data class UserInfo( // 회원정보
    @SerializedName("user_id") val id : String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("info") val info : String,
    @SerializedName("bookmarkshow") val bookmarkShow: Int, // 북마크 출력 방식
    @SerializedName("hashtagshow") val hashtagShow: Int, // 즐겨찾기 해시태그
    @SerializedName("hashtagcategory") val hashtagCategory: Int // 즐겨찾기 해시태그 카테고리화
)

data class BioOfUserInfo( // 소개글
    @SerializedName("info") val info: String
)

data class Password( // 비밀번호
    @SerializedName("pw") val pw: String,
    @SerializedName("newPw") val newPw: String
)

data class BookmarkViewInfo( // 북마크 보기방식
    @SerializedName("bookmarkShow") var bookmarkShow: Int, // 북마크 출력 방식
    @SerializedName("hashtagShow") var hashtagShow: Int, // 즐겨찾기 해시태그
    @SerializedName("hashtagCategory") var hashtagCategory: Int // 즐겨찾기 해시태그 카테고리화
)
