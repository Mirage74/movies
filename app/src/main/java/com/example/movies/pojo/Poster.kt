package com.example.movies.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Poster(
    @SerializedName("url")
    val url: String
) : Serializable
