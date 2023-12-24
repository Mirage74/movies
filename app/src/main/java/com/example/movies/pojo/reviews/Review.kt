package com.example.movies.pojo.reviews

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("review")
    val review: String
)