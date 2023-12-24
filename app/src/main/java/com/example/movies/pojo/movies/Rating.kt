package com.example.movies.pojo.movies

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rating(
    @SerializedName("kp")
    val kp: String,
    @SerializedName("imdb")
    val imdb: String
) : Serializable
