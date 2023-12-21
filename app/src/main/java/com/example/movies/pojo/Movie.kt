package com.example.movies.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("poster")
    val poster: Poster,
    @SerializedName("rating")
    val rating: Rating
) : Serializable
