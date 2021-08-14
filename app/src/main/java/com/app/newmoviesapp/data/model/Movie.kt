package com.app.newmoviesapp.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("poster_path")
    val moviePosterUrl: String,

    @SerializedName("backdrop_path")
    val backDropPath: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("vote_average")
    val userRatings: Double,

    @SerializedName("release_date")
    val releaseDate: String
){

}