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
    @SerializedName("nickname") val nickname: String,
    @SerializedName("info") val info : String,
)

data class BioOfUserInfo(
    @SerializedName("info") val info: String
)

data class Password(
    @SerializedName("pw") val pw: String,
    @SerializedName("newPw") val newPw: String
)

