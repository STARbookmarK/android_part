package com.example.bookmarkkk

import com.google.gson.annotations.SerializedName

data class Bookmark( // 북마크
    @SerializedName("tag") val tag_name : String,
    @SerializedName("url") val url : String,
    @SerializedName("info") val tag_info : String,
    @SerializedName("rate") val rate : String
)

