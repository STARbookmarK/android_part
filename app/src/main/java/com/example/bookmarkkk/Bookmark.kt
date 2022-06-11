package com.example.bookmarkkk

import com.google.gson.annotations.SerializedName

data class Bookmark(
    @SerializedName("tag") val tag_name : String,
    @SerializedName("url") val url : String,
    @SerializedName("info") val tag_info : String,
    @SerializedName("rate") val rate : String
)

