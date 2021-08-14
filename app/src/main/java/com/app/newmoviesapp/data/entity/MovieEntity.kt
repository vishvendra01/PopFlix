package com.app.newmoviesapp.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val moviePosterUrl: String?,
    val movieBackdropUrl: String?,
    val overview: String,
    val rating: Double,
    val releaseDate: String,
    var page: Int = 1
) : Parcelable