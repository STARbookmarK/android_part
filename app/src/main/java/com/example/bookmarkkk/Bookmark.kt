package com.example.bookmarkkk

import com.google.gson.annotations.SerializedName

data class Bookmark( // 북마크
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("address") val address : String,
    @SerializedName("image") val image : String,
    @SerializedName("description") val description : String,
    @SerializedName("rate") val rate : Int,
    @SerializedName("hashtags") val tags : String
)

