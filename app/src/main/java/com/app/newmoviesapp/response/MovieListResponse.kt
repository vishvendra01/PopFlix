package com.app.newmoviesapp.response

import com.app.newmoviesapp.data.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    val page: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("results")
    val results: List<Movie>
)
