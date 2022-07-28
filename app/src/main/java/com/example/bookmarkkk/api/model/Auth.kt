package com.example.bookmarkkk.api.model

import com.google.gson.annotations.SerializedName

data class LoginData( // 로그인
    @SerializedName("id") val user_id: String,
    @SerializedName("pw") val user_pw: String,
    @SerializedName("autoLogin") val autoLogin : Boolean
)

data class UserId(
    @SerializedName("id") val id: String
)
