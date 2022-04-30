package com.example.bookmarkkk

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("id") val user_id: String,
    @SerializedName("pw") val user_pw: String
)

