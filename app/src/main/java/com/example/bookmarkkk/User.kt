package com.example.bookmarkkk

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("id") val user_id: String,
    @SerializedName("pw") val user_pw: String,
    @SerializedName("autoLogin") val autoLogin : Boolean
)

data class UserInfo(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("tokenType") val tokenType: String,
    @SerializedName("iat") val iat: Int,
    @SerializedName("exp") val exp: Int,
    @SerializedName("iss") val iss: String
)

data class RegisterData(
    @SerializedName("id") val user_id: String,
    @SerializedName("password") val user_pw: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("message") val message: String
)

data class IdCheckData(
    @SerializedName("valid") val valid: Boolean
)

data class NicknameCheckData(
    @SerializedName("valid") val valid: Boolean
)

