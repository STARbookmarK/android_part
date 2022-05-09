package com.example.bookmarkkk

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("id") val user_id: String,
    @SerializedName("pw") val user_pw: String,
    @SerializedName("autoLogin") val autoLogin : Boolean
    //자동로그인 autoLogin : Boolean 추가
)

data class TokenData(
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("accessToken") val accessToken: String
)

