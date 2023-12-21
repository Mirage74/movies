package com.example.movies.pojo.trailers

import com.google.gson.annotations.SerializedName

data class TrailersList(
    @SerializedName("trailers")
    val trailers: List<Trailer>
)
