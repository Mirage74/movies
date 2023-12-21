package com.example.movies.pojo.trailers

import com.google.gson.annotations.SerializedName

data class TrailerResponse(
    @SerializedName("videos")
    val trailersList: TrailersList
)
