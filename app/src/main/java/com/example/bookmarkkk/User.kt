package com.example.bookmarkkk

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("id") val user_id: String,
    @SerializedName("pw") val user_pw: String,
    @SerializedName("autoLogin") val autoLogin : Boolean,
    //자동로그인 autoLogin : Boolean 추가
)

data class LoginResponse(
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("accessToken") val accessToken: String
)

data class UserInfo(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("tokenType") val tokenType: String,
    @SerializedName("iat") val iat: Int,
    @SerializedName("exp") val exp: Int,
    @SerializedName("iss") val iss: String
)

