package com.app.newmoviesapp.data.source.remote

import com.app.newmoviesapp.response.MovieListResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkSource {

    companion object {
        private const val MOVIES_URL = "/3/movie/popular"
    }

    @GET(MOVIES_URL)
    fun getMovies(@Query("page") page: Int): Flowable<MovieListResponse>
}