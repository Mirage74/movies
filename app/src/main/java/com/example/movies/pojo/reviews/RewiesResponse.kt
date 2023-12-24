package com.example.movies.pojo.reviews

import com.google.gson.annotations.SerializedName

data class RewiesResponse(
    @SerializedName("docs")
    val reviewList: List<Review>
)
