package com.example.bookmarkkk

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("id") val user_id: String,
    @SerializedName("pw") val user_pw: String,
    @SerializedName("autoLogin") val autoLogin : Boolean
)

data class UserId(
    @SerializedName("id") val id: String
)

data class SignUpData(
    @SerializedName("id") val user_id: String,
    @SerializedName("password") val user_pw: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("info") val info: String
)

data class IdCheckData(
    @SerializedName("valid") val valid: Boolean
)

data class NicknameCheckData(
    @SerializedName("valid") val valid: Boolean
)

data class UserInfo(
    @SerializedName("user_id") val id : String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("info") val info : String,
    @SerializedName("bookmarkShow") val bookmarkShow: Boolean, // 북마크 출력 방식
    @SerializedName("hashtagShow") val hashtagShow: Boolean, // 즐겨찾기 해시태그
    @SerializedName("hashtagCategory") val hashtagCategory: Boolean // 즐겨찾기 해시태그 카테고리화
)

data class BioOfUserInfo(
    @SerializedName("info") val info: String
)

data class Password(
    @SerializedName("pw") val pw: String,
    @SerializedName("newPw") val newPw: String
)

data class BookmarkViewInfo(
    @SerializedName("bookmarkShow") val bookmarkShow: Boolean, // 북마크 출력 방식
    @SerializedName("hashtagShow") val hashtagShow: Boolean, // 즐겨찾기 해시태그
    @SerializedName("hashtagCategory") val hashtagCategory: Boolean // 즐겨찾기 해시태그 카테고리화
)

