package com.example.bookmarkkk.api.model

import com.google.gson.annotations.SerializedName

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

