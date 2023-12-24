package com.example.movies.pojo.movies

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Poster(
    @SerializedName("url")
    val url: String
) : Serializable
