package com.example.bookmarkkk

import com.google.gson.annotations.SerializedName

data class UserId(
    @SerializedName("id") val id: String
)

data class LoginData( // 로그인
    @SerializedName("id") val user_id: String,
    @SerializedName("pw") val user_pw: String,
    @SerializedName("autoLogin") val autoLogin : Boolean
)

data class SignUpData( // 회원가입
    @SerializedName("id") val user_id: String,
    @SerializedName("password") val user_pw: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("info") val info: String
)

data class IdCheckData( // 아이디 중복체크
    @SerializedName("valid") val valid: Boolean
)

data class NicknameCheckData( // 닉네임 중복체크
    @SerializedName("valid") val valid: Boolean
)

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
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("address") val address: String,
    @SerializedName("description") val description: String,
    @SerializedName("rate") val rate : Int,
    @SerializedName("shared") val shared : Boolean,
    @SerializedName("hashtags") val hashtags : List<String>
)

data class BookmarkId( // 아이디 중복체크
    @SerializedName("id") val id: Int
)

data class HashTag(
    @SerializedName("title") val title : String,
    @SerializedName("star") val start : Int,
    @SerializedName("category") val category : String
)

